package samsungcampus.sprint3.elikur.core.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public abstract class View implements Disposable {
    protected float x;
    protected float y;

    public float width;
    public float height;

    public View(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public View(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isHit(float tx, float ty) {
        return tx >= x && tx <= x + width && ty >= y && ty <= y + height;
    }

    public void draw(SpriteBatch batch) {
    }

    public void setPosition(Vector2 position) {
        this.x = position.x;
        this.y = position.y;
    }

    public void setSize(Vector2 position) {
        this.width = position.x;
        this.height = position.y;
    }

    @Override
    public void dispose() {
    }

}
