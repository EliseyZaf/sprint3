package samsungcampus.sprint3.elikur.core.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Box extends GameObject {
    protected static final int paddingHorizontal = 30;
    protected boolean isPickup = false;
    public Box(String texturePath, int x, int y, int width, int height, World world, short cBit) {
        super(texturePath, x, y, width, height, world, cBit, 0);
    }

    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }

    @Override
    public void hit() {
        super.hit();
        isPickup = true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (!isPickup) super.draw(batch);
    }

    public boolean isAlive() {
        return !isPickup;
    }
}
