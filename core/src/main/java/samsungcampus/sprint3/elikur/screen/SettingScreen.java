package samsungcampus.sprint3.elikur.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import samsungcampus.sprint3.elikur.Starter;
import samsungcampus.sprint3.elikur.core.GameResources;
import samsungcampus.sprint3.elikur.core.managers.MemoryManager;
import samsungcampus.sprint3.elikur.core.ui.ButtonView;
import samsungcampus.sprint3.elikur.core.ui.ImageView;
import samsungcampus.sprint3.elikur.core.ui.MovingBackgroundView;
import samsungcampus.sprint3.elikur.core.ui.TextView;

public class SettingScreen extends ScreenAdapter {
    Starter starter;

    MovingBackgroundView backgroundView;
    TextView titleTextView;
    ImageView blackoutImageView;
    ButtonView returnButton;
    TextView musicSettingView, soundSettingView, clearSettingView, renderSettingView;

    public SettingScreen(Starter starter) {
        this.starter = starter;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleTextView = new TextView(starter.largeWhiteFont, 256, 1100, "Settings");
        blackoutImageView = new ImageView(85, 440, 550, 360, GameResources.BLACKOUT_MIDDLE_IMG_PATH);
        musicSettingView = new TextView(starter.commonWhiteFont, 173, 717, "Music: " + "ON");
        soundSettingView = new TextView(starter.commonWhiteFont, 173, 658, "Sound: " + "ON");
        clearSettingView = new TextView(starter.commonWhiteFont, 173, 599, "Clear records");
        renderSettingView = new TextView(starter.commonWhiteFont, 173, 540, "Enable Debug Render Line");
        returnButton = new ButtonView(
            280, 330,
            160, 70,
            starter.commonBlackFont,
            GameResources.BUTTON_BACK,
            "Return"
        );
    }

    @Override
    public void show() {
        super.show();

        if (GameScreen.drawRender) {
            renderSettingView.setText("EDRL (now draw)");
        } else {
            renderSettingView.setText("Enable Debug Render Line");
        }
    }

    @Override
    public void render(float delta) {
        handleInput();

        starter.camera.update();
        starter.batch.setProjectionMatrix(starter.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        starter.batch.begin();

        backgroundView.draw(starter.batch);
        titleTextView.draw(starter.batch);
        blackoutImageView.draw(starter.batch);
        returnButton.draw(starter.batch);
        musicSettingView.draw(starter.batch);
        soundSettingView.draw(starter.batch);
        clearSettingView.draw(starter.batch);
        renderSettingView.draw(starter.batch);

        starter.batch.end();
    }

    private String translateStateToText(boolean state) {
        return state ? "ON" : "OFF";
    }

    void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touch = starter.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (returnButton.isHit(touch.x, touch.y)) {
                starter.setMenuScreen();
            }
            if (clearSettingView.isHit(touch.x, touch.y)) {
                clearSettingView.setText("Clear records (cleared)");
                MemoryManager.saveTableOfRecords(new ArrayList<>());
            }
            if (musicSettingView.isHit(touch.x, touch.y)) {
                MemoryManager.saveMusicSettings(!MemoryManager.loadIsMusicOn());

                musicSettingView.setText("Music: " + translateStateToText(MemoryManager.loadIsMusicOn()));
                starter.audioManager.updateMusicFlag();
            }
            if (soundSettingView.isHit(touch.x, touch.y)) {
                MemoryManager.saveSoundSettings(!MemoryManager.loadIsSoundOn());

                soundSettingView.setText("Sound: " + translateStateToText(MemoryManager.loadIsSoundOn()));
                starter.audioManager.updateSoundFlag();
            }
            if (renderSettingView.isHit(touch.x, touch.y)) {
                if (!GameScreen.drawRender) {
                    renderSettingView.setText("EDRL (now draw)");
                    GameScreen.drawRender = !GameScreen.drawRender;
                } else {
                    renderSettingView.setText("Enable Debug Render Line");
                    GameScreen.drawRender = !GameScreen.drawRender;
                }
            }
        }
    }
}
