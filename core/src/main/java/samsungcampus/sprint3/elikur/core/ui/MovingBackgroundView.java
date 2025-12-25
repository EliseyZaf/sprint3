package samsungcampus.sprint3.elikur.core.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import samsungcampus.sprint3.elikur.core.GameSetting;
import samsungcampus.sprint3.elikur.screen.GameScreen;

public class MovingBackgroundView extends View {

    protected Texture texture;

    int texture1Y;
    int texture2Y;
    int speed = 2;

    public MovingBackgroundView(String pathToTexture) {
        super(0, 0);
        texture1Y = 0;
        texture2Y = GameSetting.useSizes.size[1];
        texture = new Texture(pathToTexture);
    }

    public void move() {
        texture1Y -= (int) (speed * GameScreen.getMultiplayerSpeed());
        texture2Y -= (int) (speed * GameScreen.getMultiplayerSpeed());
        if (texture1Y <= -GameSetting.useSizes.size[1]) {
            texture1Y = GameSetting.useSizes.size[1];
        }
        if (texture2Y <= -GameSetting.useSizes.size[1]) {
            texture2Y = GameSetting.useSizes.size[1];
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, 0, texture1Y, GameSetting.useSizes.size[0], GameSetting.useSizes.size[1]);
        batch.draw(texture, 0, texture2Y, GameSetting.useSizes.size[0], GameSetting.useSizes.size[1]);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
