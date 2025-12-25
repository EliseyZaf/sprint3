package samsungcampus.sprint3.elikur.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

import samsungcampus.sprint3.elikur.Starter;
import samsungcampus.sprint3.elikur.core.GameResources;
import samsungcampus.sprint3.elikur.core.GameSession;
import samsungcampus.sprint3.elikur.core.GameSetting;
import samsungcampus.sprint3.elikur.core.effects.DamageNumberEffect;
import samsungcampus.sprint3.elikur.core.helpers.GameStats;
import samsungcampus.sprint3.elikur.core.managers.ContactManager;
import samsungcampus.sprint3.elikur.core.managers.MemoryManager;
import samsungcampus.sprint3.elikur.core.model.Box;
import samsungcampus.sprint3.elikur.core.model.objects.BulletObject;
import samsungcampus.sprint3.elikur.core.model.objects.ShipObject;
import samsungcampus.sprint3.elikur.core.model.Trash;
import samsungcampus.sprint3.elikur.core.ui.ButtonView;
import samsungcampus.sprint3.elikur.core.ui.ImageView;
import samsungcampus.sprint3.elikur.core.ui.LiveView;
import samsungcampus.sprint3.elikur.core.ui.MovingBackgroundView;
import samsungcampus.sprint3.elikur.core.ui.RecordsListView;
import samsungcampus.sprint3.elikur.core.ui.TextView;

public class GameScreen extends ScreenAdapter {
    private Box2DDebugRenderer debugRenderer;
    public static boolean drawRender = false;

    public static Starter starter;
    public static ShipObject player;
    private GameSession session;
    private ContactManager contactManager;
    private ArrayList<Trash> trashObjects;
    private ArrayList<Box> boxObjects;
    private ArrayList<BulletObject> bulletArray;
    private static DamageNumberEffect damageNumberEffect;

    private final Vector2 v0 = new Vector2(0, (float) GameSetting.useSizes.size[1] / 2);
    private final Vector2 v1 = new Vector2(GameSetting.useSizes.size[0], (float) GameSetting.useSizes.size[1] / 2);
    public static float score = 0, worldSpeed = 1, multiple = 1, distance = 0;

    private MovingBackgroundView backgroundView;
    private ImageView topBlackoutView, backBlackoutView;
    private LiveView liveView;
    private TextView scoreTextView;
    private ButtonView pauseButton, homeButton, continueButton;

    private GameStats state;

    private TextView recordsTextView;
    private RecordsListView recordsListView;
    private ButtonView homeButton2;

    // Cash
    private Vector2 center;
    public GameScreen(Starter starter) {
        this.starter = starter;

        debugRenderer = new Box2DDebugRenderer();
        damageNumberEffect = new DamageNumberEffect(starter);
    }

    @Override
    public void show() {
        trashObjects = new ArrayList<>();
        bulletArray  = new ArrayList<>();
        boxObjects   = new ArrayList<>();

        center = new Vector2();

        contactManager = new ContactManager(starter.world.getWorld());
        session        = new GameSession();

        backgroundView  = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        topBlackoutView = new ImageView(0, 0, GameResources.BLACKOUT_TOP_IMG_PATH);
        liveView        = new LiveView(0, 0);
        scoreTextView   = new TextView(starter.commonWhiteFont, 0, 0);
        pauseButton     = new ButtonView(605, 1200, 46, 54, GameResources.PAUSE_IMG_PATH);

        topBlackoutView.setPosition(new Vector2(0, GameSetting.useSizes.size[1] - topBlackoutView.height));
        liveView       .setPosition(new Vector2(305, GameSetting.useSizes.size[1] - topBlackoutView.height / 2 - liveView.height / 2));
        scoreTextView  .setPosition(new Vector2(50, GameSetting.useSizes.size[1] - topBlackoutView.height / 2 - scoreTextView.height / 2));
        pauseButton    .setPosition(new Vector2(605, GameSetting.useSizes.size[1] - topBlackoutView.height / 2 - pauseButton.height / 2));

        backBlackoutView = new ImageView(0, 0, GameResources.BLACKOUT_BACK_IMG_PATH);
        homeButton       = new ButtonView(0, 0, 160, 70, starter.commonBlackFont, GameResources.BUTTON_BACK, "Home");
        continueButton   = new ButtonView(0, 0, 160, 70, starter.commonBlackFont, GameResources.BUTTON_BACK, "Continue");

        backBlackoutView.setPosition(new Vector2(GameSetting.useSizes.size[0], GameSetting.useSizes.size[1]));
        homeButton.setPosition(
            new Vector2(GameSetting.useSizes.size[0] / 2 - 30 - homeButton.width,
                GameSetting.useSizes.size[1] / 2
            ));

        continueButton.setPosition(
            new Vector2(GameSetting.useSizes.size[0] / 2 + 30,
                GameSetting.useSizes.size[1] / 2
            ));

        state = GameStats.PLAYING;

        player = new ShipObject(
            GameSetting.useSizes.size[0] / 2, 150,
            starter.world.getWorld()
        );

        recordsListView = new RecordsListView(starter.commonWhiteFont, 690);
        recordsTextView = new TextView(starter.largeWhiteFont, 206, 842, "Last records");
        homeButton2 = new ButtonView(
            280, 365,
            160, 70,
            starter.commonBlackFont,
            GameResources.BUTTON_BACK,
            "Home"
        );

        session.startGame();

        state = GameStats.PLAYING;
    }

    @Override
    @SuppressWarnings("NewApi")
    public void render(float delta) {
        if (delta > 1) {
            ArrayList<Integer> recordsTable = MemoryManager.loadRecordsTable();
            if (recordsTable == null) {
                recordsTable = new ArrayList<>();
            }
            int foundIdx = 0;
            for (; foundIdx < recordsTable.size(); foundIdx++) {
                if (recordsTable.get(foundIdx) < score) break;
            }
            recordsTable.add(foundIdx, (int) score);
            MemoryManager.saveTableOfRecords(recordsTable);
            state = GameStats.ENDED;
            recordsListView.setRecords(MemoryManager.loadRecordsTable());

            score = 0;
            distance = 0;
            worldSpeed = 1;
            multiple = 1;

            starter.setMenuScreen();
        }

        handleInput();

        damageNumberEffect.update(delta);

        if (state == GameStats.PLAYING) {
            starter.world.stepWorld();

            player.update(delta);

            distance += worldSpeed * delta * 20;
            score += worldSpeed * delta * 10 * multiple;
            worldSpeed += 0.01f * delta;

            Trash trash = session.shouldSpawnTrash(starter);
            if (trash != null) trashObjects.add(trash);

            Trash.updateCooldown(delta);

            if (player.needToShoot()) {
                BulletObject laserBullet = new BulletObject(
                    player.getX(), player.getY() + player.getHeight() / 2 + 50,
                    starter.world.getWorld()
                );
                bulletArray.add(laserBullet);

                if (starter.audioManager.isSoundOn) starter.audioManager.shootSound.play();
            }

            if (player.laserOn) {
                center.x = player.getX() + player.getWidth() / 2;
                for (Trash t: trashObjects)  {
                    if (center.x + 10 >= t.getX() && center.x + 10 <= t.getX() + t.getWidth()
                        || center.x - 10 <= t.getX() + t.getWidth() && center.x - 10 >= t.getX()) {
                        t.laserDamage(delta);
                        t.drawWithRed = true;
                    }
                }
            }

            updateTrash();
            updateBullets();
            updateBox();

            backgroundView.move();
            liveView.setLeftLives(player.getLivesLeft());
            scoreTextView.setText("Score: " + (int) score + "  ×" + "%.1f".formatted(multiple));

            if (!player.isAlive()) {
                ArrayList<Integer> recordsTable = MemoryManager.loadRecordsTable();
                if (recordsTable == null) {
                    recordsTable = new ArrayList<>();
                }
                int foundIdx = 0;
                for (; foundIdx < recordsTable.size(); foundIdx++) {
                    if (recordsTable.get(foundIdx) < score) break;
                }
                recordsTable.add(foundIdx, (int) score);
                MemoryManager.saveTableOfRecords(recordsTable);
                state = GameStats.ENDED;
                recordsListView.setRecords(MemoryManager.loadRecordsTable());

                distance = 0;
                score = 0;
                worldSpeed = 1;
                multiple = 1;
            }
        }

        draw();

        if (drawRender) renderDebug();
    }

    private void renderDebug() {
        Matrix4 debugMatrix = new Matrix4();

        debugMatrix.setToOrtho2D(0, 0,
            GameSetting.useSizes.size[0],
            GameSetting.useSizes.size[1]
        );

        debugMatrix.scale(1 / GameSetting.SCALE, 1 / GameSetting.SCALE, 1);

        debugRenderer.render(starter.world.getWorld(), debugMatrix);
    }

    private void updateTrash() {
        for (int i = 0; i < trashObjects.size(); i++) {

            boolean hasToBeDestroyed = trashObjects.get(i).isAlive() || !trashObjects.get(i).isInFrame();

            if (trashObjects.get(i).isAlive()) {
                if (starter.audioManager.isSoundOn) starter.audioManager.explosionSound.play(0.2f);
            }

            if (hasToBeDestroyed) {
                starter.world.getWorld().destroyBody(trashObjects.get(i).getBody());
                Box box = trashObjects.remove(i--).getLoot();

                multiple += 0.05f;

                if (box != null) boxObjects.add(box);
            }
        }
    }

    private void updateBullets() {
        for (int i = 0; i < bulletArray.size(); i++) {
            if (bulletArray.get(i).hasToBeDestroyed()) {
                starter.world.getWorld().destroyBody(bulletArray.get(i).getBody());
                bulletArray.remove(i--);
            }
        }
    }

    private void updateBox() {
        for (int i = 0; i < boxObjects.size(); i++) {
            if (!boxObjects.get(i).isAlive()) {
                starter.world.getWorld().destroyBody(boxObjects.get(i).getBody());
                boxObjects.remove(i--);
            }
        }
    }

    private void handleInput() {
        Vector3 touch = starter.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

        switch (state) {
            case PLAYING:
                if (Gdx.input.justTouched()) {
                    starter.touchStart = touch;

                    if (pauseButton.isHit(starter.touchStart.x, starter.touchStart.y)) {
                        pauseGame();
                    }
                }

                if (Gdx.input.isTouched()) {
                    starter.touchFinish = touch;

                    player.move(starter.touchStart, starter.touchFinish);

                    starter.touchStart = touch;
                }
                break;

            case PAUSED:
                if (homeButton.isHit(touch.x, touch.y)) {
                    distance = 0;
                    score = 0;
                    worldSpeed = 1;
                    multiple = 1;

                    state = GameStats.ENDED;
                    starter.setMenuScreen();
                }
                if (continueButton.isHit(touch.x, touch.y)) {
                    resumeGame();
                }
                break;

            case ENDED:
                if (homeButton2.isHit(touch.x, touch.y)) {
                    starter.setMenuScreen();
                }
                break;
        }
    }

    private void draw() {
        starter.camera.update();
        starter.batch.setProjectionMatrix(starter.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        starter.batch.begin();

        backgroundView.draw(starter.batch);

        player.draw(starter.batch);
        for (Trash trash : trashObjects) trash.draw(starter.batch);
        for (Box box : boxObjects) box.draw(starter.batch);
        for (BulletObject bullet : bulletArray) bullet.draw(starter.batch);
        damageNumberEffect.draw(starter.batch);

        topBlackoutView.draw(starter.batch);
        scoreTextView.draw(starter.batch);
        liveView.draw(starter.batch);
        pauseButton.draw(starter.batch);

        starter.batch.end();

        // Line
        starter.renderer.begin();
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        starter.renderer.setColor(1, 0, 0,
            Math.min(1, 1 - (((float) GameSetting.useSizes.size[1] / 2 - (player.getY() + player.getHeight())) / 300)));
        Gdx.gl.glLineWidth(Math.max(0, 4 - (((float) GameSetting.useSizes.size[1] / 2 - (player.getY() + player.getHeight())) / 20)));
        starter.renderer.line(v0, v1);
        starter.renderer.end();

        starter.batch.begin();

        if (state == GameStats.PAUSED) {
            backBlackoutView.draw(starter.batch);
            homeButton.draw(starter.batch);
            continueButton.draw(starter.batch);
        } else if (state == GameStats.ENDED) {
            backBlackoutView.draw(starter.batch);
            recordsTextView.draw(starter.batch);
            recordsListView.draw(starter.batch);
            homeButton2.draw(starter.batch);
        }

        starter.batch.end();
    }

    public void pauseGame() {
        state = GameStats.PAUSED;
        session.pause();
    }

    public void resumeGame() {
        state = GameStats.PLAYING;
        session.resumeGame();
    }

    @Override
    public void hide() {
        for (int i = 0; i < trashObjects.size(); i++) {
            starter.world.getWorld().destroyBody(trashObjects.get(i).getBody());
            trashObjects.remove(i--);
        }

        for (int i = 0; i < bulletArray.size(); i++) {
            starter.world.getWorld().destroyBody(bulletArray.get(i).getBody());
            bulletArray.remove(i--);
        }

        starter.world.dispose();

        starter.touchStart = null;
        starter.touchFinish = null;
    }

    @Override
    public void dispose() {
        super.dispose();

        player.dispose();

        backgroundView.dispose();
        topBlackoutView.dispose();
        backBlackoutView.dispose();
        liveView.dispose();
        scoreTextView.dispose();
        pauseButton.dispose();
        homeButton.dispose();
        continueButton.dispose();

        starter.world.dispose();
    }

    public static float getMultiplayerSpeed() {
        return Math.max(1, worldSpeed / 5);
    }

    public static int getMultiplayerHealth() {
        return (int) (1 + (distance / 1000));
    }

    public static void addDamageNum(Vector2 pos, String damage) {
        damageNumberEffect.addNewEffect(pos, damage);
    }
}
