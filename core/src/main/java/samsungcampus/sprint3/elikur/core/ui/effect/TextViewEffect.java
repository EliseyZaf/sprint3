package samsungcampus.sprint3.elikur.core.ui.effect;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import samsungcampus.sprint3.elikur.Starter;
import samsungcampus.sprint3.elikur.core.ui.TextView;

public class TextViewEffect extends TextView {
    private Vector2 force;
    private static float speed = 2, gravity = 16f;
    private float liveTime = 0.3f, elapsedTime = 0;
    public boolean isLive = true;

    public TextViewEffect(float x, float y, String text) {
        super(Starter.effectNumbers, x, y, text);
        force = new Vector2(1, 1.5f);
    }

    public void update(float delta) {
        elapsedTime += delta;

        if (elapsedTime < liveTime) {
            x += force.x * speed;
            y += force.y * speed;

            force.y -= gravity * delta;
        } else isLive = false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
}
