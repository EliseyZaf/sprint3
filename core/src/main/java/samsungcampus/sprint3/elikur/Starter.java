package samsungcampus.sprint3.elikur;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;

import samsungcampus.sprint3.elikur.core.GameResources;
import samsungcampus.sprint3.elikur.core.GameSetting;
import samsungcampus.sprint3.elikur.core.helpers.FontBuilder;
import samsungcampus.sprint3.elikur.core.managers.AudioManager;
import samsungcampus.sprint3.elikur.core.spaces.GameWorld;
import samsungcampus.sprint3.elikur.screen.GameScreen;
import samsungcampus.sprint3.elikur.screen.MenuScreen;
import samsungcampus.sprint3.elikur.screen.SettingScreen;

public class Starter extends Game {
    public AudioManager audioManager;

    public SpriteBatch batch;
    public ShapeRenderer renderer;
    public OrthographicCamera camera;
    public GameWorld world;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private SettingScreen settingScreen;

    public Vector3 touchStart, touchFinish;

    public static BitmapFont commonWhiteFont, commonBlackFont, largeWhiteFont, effectNumbers;

    @Override
    public void create() {
        Box2D.init();
        world = new GameWorld(new Vector2(0, 0), true);

        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameSetting.useSizes.size[0], GameSetting.useSizes.size[1]);

        commonWhiteFont = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
        commonBlackFont = FontBuilder.generate(24, Color.BLACK, GameResources.FONT_PATH);
        largeWhiteFont = FontBuilder.generate(56, Color.WHITE, GameResources.FONT_PATH);
        effectNumbers = FontBuilder.generate(45, Color.ORANGE, GameResources.FONT_PATH);

        audioManager = new AudioManager();

        menuScreen    = new MenuScreen(this);
        gameScreen    = new GameScreen(this);
        settingScreen = new SettingScreen(this);

        setMenuScreen();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void setGameScreen() {
        setScreen(gameScreen);
    }

    public void setMenuScreen() {
        setScreen(menuScreen);
    }

    public void setSettingScreen() {
        setScreen(settingScreen);
    }
}
