package samsungcampus.sprint3.elikur.core.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import samsungcampus.sprint3.elikur.core.GameSetting;
import samsungcampus.sprint3.elikur.core.model.objects.AddPowerBox;
import samsungcampus.sprint3.elikur.core.model.objects.LaserBox;
import samsungcampus.sprint3.elikur.core.model.objects.TimeDelayBox;
import samsungcampus.sprint3.elikur.screen.GameScreen;

public abstract class Trash extends GameObject {
    private World world;
    protected static final int paddingHorizontal = 30;
    protected float livesLeft;
    public boolean drawWithRed = false;
    private int cadrWithRed = 0;
    private static float timeToBox = 0.6f, elapsedTime = 0;
    private static boolean canSpawnBox = true;
    public Trash(String texturePath, int x, int y, int width, int height, World world, short cBit, Vector2 scaleHitBox) {
        super(texturePath, x, y, width, height, world, cBit, scaleHitBox);
        this.world = world;
    }

    public Trash(String texturePath, int x, int y, int width, int height, World world, short cBit, float scaleHitBox) {
        super(texturePath, x, y, width, height, world, cBit, scaleHitBox);
        this.world = world;
    }

    public static void updateCooldown(float delta) {
        elapsedTime += delta;
        if (elapsedTime >= timeToBox) {
            canSpawnBox = true;
        }
    }

    public Box getLoot() {
        if (canSpawnBox) {
            float chance = (float) Math.random();

            canSpawnBox = false;
            elapsedTime = 0;

            if (chance <= 0.23) {
                return new TimeDelayBox(getX(), getY(), world);
            } else if (chance <= 0.3) {
                return new LaserBox(getX(), getY(), world);
            } else if (chance <= 0.33) {
                return new AddPowerBox(getX(), getY(), world);
            }
        }
        return null;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!drawWithRed) super.draw(batch);
        else {
            batch.setColor(1, 0, 0, 0.8f);
            batch.draw(texture, getX() - (width / 2f), getY() - (height / 2f), width, height);
            batch.setColor(1, 1, 1, 1);

            if (cadrWithRed == 0) drawWithRed = false;
            else cadrWithRed--;
        }
    }

    @Override
    public void hit() {
        super.hit();
        livesLeft -= GameScreen.player.damage;

        drawWithRed = true;
        cadrWithRed = 5;
    }

    public void laserDamage(float delta) {
        livesLeft -= GameSetting.LASER_DAMAGE * delta * GameScreen.player.damage;
    }

    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }

    public boolean isAlive() {
        return livesLeft <= 0;
    }

    public void die() {
        livesLeft = 0;
    }
}
