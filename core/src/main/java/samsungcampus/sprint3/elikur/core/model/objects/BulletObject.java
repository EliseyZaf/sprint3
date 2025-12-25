package samsungcampus.sprint3.elikur.core.model.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import samsungcampus.sprint3.elikur.core.GameResources;
import samsungcampus.sprint3.elikur.core.GameSetting;
import samsungcampus.sprint3.elikur.core.model.GameObject;

public class BulletObject extends GameObject {
    public boolean wasHit;
    public BulletObject(int x, int y, World world) {
        super(GameResources.BULLET_IMG_PATH, x, y, 15, 45, world, GameSetting.BULLET_BIT, 0);

        wasHit = false;
        body.setLinearVelocity(new Vector2(0, GameSetting.BULLET_VELOCITY));
        body.setBullet(true);
    }

    public boolean hasToBeDestroyed() {
        return wasHit || (getY() - height / 2 > GameSetting.useSizes.size[1]);
    }

    @Override
    public void hit() {
        wasHit = true;
    }
}
