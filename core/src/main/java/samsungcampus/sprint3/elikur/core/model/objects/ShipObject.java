package samsungcampus.sprint3.elikur.core.model.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;

import samsungcampus.sprint3.elikur.core.GameResources;
import samsungcampus.sprint3.elikur.core.GameSetting;
import samsungcampus.sprint3.elikur.core.effects.Effect;
import samsungcampus.sprint3.elikur.core.model.GameObject;

public class ShipObject extends GameObject {
    public long lastTimeShoot, shootDaley;
    public int livesLeft;
    private ArrayList<Effect> effects;
    public HashMap<String, Integer> countEffect = new HashMap();
    public int damage = 1;

    // EffectsChangers
    public float shootingCooldown = GameSetting.SHOOTING_COOL_DOWN;
    private Texture laserTexture;
    public boolean laserOn = false;
    public ShipObject(int x, int y, World world) {
        super(GameResources.SHIP_IMG_PATH, x, y, 150, 150, world, GameSetting.SHIP_BIT, 0.2f);
        effects = new ArrayList<>();
        countEffect = new HashMap<>();

        countEffect.put("TimeDelay", 0);
        countEffect.put("Laser", 0);

        laserTexture = new Texture(GameResources.LASER_IMG_PATH);

        body.setLinearDamping(10);
        livesLeft = 3;
    }

    @Override
    public void draw(SpriteBatch batch) {
        putInFrame();
        if (laserOn) batch.draw(laserTexture,
            getX() - laserTexture.getWidth() / 2,
            getY() + height / 2 - 10);

        super.draw(batch);
    }

    public void update(float delta) {
        for (int i = 0; i < effects.size(); i++) {
            if (effects.get(i).update(delta)) {
                effects.remove(i);
            }
        }
    }

    private void putInFrame() {
        if (getY() > (GameSetting.useSizes.size[1] / 2f - height / 2f)) {
            setY(GameSetting.useSizes.size[1] / 2 - height / 2);
        }
        if (getY() <= (height / 2f)) {
            setY(height / 2);
        }
        if (getX() < (-width / 2f)) {
            setX(GameSetting.useSizes.size[0]);
        }
        if (getX() > (GameSetting.useSizes.size[0] + width / 2f)) {
            setX(0);
        }
    }

    public void move(Vector3 start, Vector3 finish) {
        if (start != null && finish != null) {
            body.setTransform(
                (getX() * GameSetting.SCALE) + ((finish.x - start.x) * GameSetting.SCALE),
                (getY() * GameSetting.SCALE) + ((finish.y - start.y) * GameSetting.SCALE),
                0);
        }
    }

    public boolean needToShoot() {
        if (TimeUtils.millis() - lastTimeShoot >= shootingCooldown && !laserOn) {
            lastTimeShoot = TimeUtils.millis();
            return true;
        }
        return false;
    }

    public void addEffect(Effect effect) {
        effects.add(effect);
    }

    @Override
    public void hit() {
        livesLeft -= 1;
    }

    public boolean isAlive() {
        return livesLeft > 0;
    }

    public int getLivesLeft() {
        return livesLeft;
    }
}
