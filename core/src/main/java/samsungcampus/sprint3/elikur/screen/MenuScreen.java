package samsungcampus.sprint3.elikur.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import samsungcampus.sprint3.elikur.Starter;
import samsungcampus.sprint3.elikur.core.GameResources;
import samsungcampus.sprint3.elikur.core.GameSetting;
import samsungcampus.sprint3.elikur.core.helpers.FontBuilder;
import samsungcampus.sprint3.elikur.core.ui.ButtonView;
import samsungcampus.sprint3.elikur.core.ui.MovingBackgroundView;
import samsungcampus.sprint3.elikur.core.ui.TextView;

public class MenuScreen extends ScreenAdapter {
    Starter starter;
    MovingBackgroundView backgroundView;
    TextView titleView;
    ButtonView startButtonView, settingsButtonView, exitButtonView;

    public MenuScreen(Starter starter) {
        this.starter = starter;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleView = new TextView(starter.largeWhiteFont, 180, 960, "Space Cleaner");

        startButtonView = new ButtonView(140, 646, 440, 70, starter.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "start");
        settingsButtonView = new ButtonView(140, 551, 440, 70, starter.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "settings");
        exitButtonView = new ButtonView(140, 456, 440, 70, starter.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "exit");

        titleView.setPosition(new Vector2(GameSetting.useSizes.size[0] / 2 - titleView.width / 2, GameSetting.useSizes.size[1] - GameSetting.useSizes.size[1] / 3));
    }

    @Override
    public void render(float delta) {
        handleInput();

        starter.camera.update();
        starter.batch.setProjectionMatrix(starter.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        starter.batch.begin();

        backgroundView.draw(starter.batch);
        titleView.draw(starter.batch);
        exitButtonView.draw(starter.batch);
        settingsButtonView.draw(starter.batch);
        startButtonView.draw(starter.batch);

        starter.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 vector = starter.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (startButtonView.isHit(vector.x, vector.y)) {
                starter.setGameScreen();
            }
            if (exitButtonView.isHit(vector.x, vector.y)) {
                Gdx.app.exit();
            }
            if (settingsButtonView.isHit(vector.x, vector.y)) {
                starter.setSettingScreen();
            }
        }
    }
}
