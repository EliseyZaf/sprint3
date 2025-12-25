package samsungcampus.sprint3.elikur.core.model;

import static samsungcampus.sprint3.elikur.core.GameSetting.SCALE;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import samsungcampus.sprint3.elikur.core.GameSetting;

public abstract class GameObject {
    protected Body body;
    protected Texture texture;
    protected int width, height;

    private short cBit;

    public GameObject(String texturePath, int x, int y, int width, int height, World world, short cBit, Vector2 scaleHitBox) {
        this.width = width;
        this.height = height;
        this.cBit = cBit;

        texture = new Texture(texturePath);
        body = createBody(x, y, world, scaleHitBox);
    }

    public GameObject(String texturePath, int x, int y, int width, int height, World world, short cBit, float scaleHitBox) {
        this.width = width;
        this.height = height;
        this.cBit = cBit;

        texture = new Texture(texturePath);
        body = createBody(x, y, world, new Vector2(scaleHitBox, scaleHitBox));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX() - (width / 2f), getY() - (height / 2f), width, height);
    }

    private Body createBody(float x, float y, World world, Vector2 scaleHitBox) {
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        Body body = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        float halfWidth = (width * SCALE) / 2f;
        float halfHeight = (height * SCALE) / 2f;

        shape.setAsBox(
            halfWidth - halfWidth * scaleHitBox.x,
            halfHeight - halfHeight * scaleHitBox.y,
            new Vector2(0, 0), 0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape; // устанавливаем коллайдер
        fixtureDef.density = 0.1f; // устанавливаем плотность тела
        fixtureDef.friction = 1f; // устанвливаем коэффициент трения
        fixtureDef.filter.categoryBits = cBit; // Установка id обьекта для коллизий

        if (cBit == GameSetting.TRASH_BIT) {
            fixtureDef.isSensor = true;
            fixtureDef.filter.maskBits = (short) (GameSetting.SHIP_BIT | GameSetting.BULLET_BIT);
        }
        if (cBit == GameSetting.BOX_BIT) {
            fixtureDef.isSensor = true;
            fixtureDef.filter.maskBits = GameSetting.SHIP_BIT;
        }

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        shape.dispose();

        body.setTransform(x * SCALE, y * SCALE, 0);
        return body;
    }

    public void dispose() {
        texture.dispose();
    }

    public int getX() {
        return (int) (body.getPosition().x / SCALE);
    }

    public int getY() {
        return (int) (body.getPosition().y / SCALE);
    }

    public void setX(int x) {
        body.setTransform(x * SCALE, body.getPosition().y, 0);
    }

    public void setY(int y) {
        body.setTransform(body.getPosition().x, y * SCALE, 0);
    }

    public Body getBody() {
        return body;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void hit() {
    }
}
