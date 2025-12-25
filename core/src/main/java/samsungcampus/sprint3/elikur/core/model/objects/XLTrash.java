package samsungcampus.sprint3.elikur.core.model.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import samsungcampus.sprint3.elikur.core.GameResources;
import samsungcampus.sprint3.elikur.core.GameSetting;
import samsungcampus.sprint3.elikur.core.model.Trash;
import samsungcampus.sprint3.elikur.screen.GameScreen;

public class XLTrash extends Trash {
    public XLTrash(World world) {
        super(GameResources.TRASH3_IMG_PATH,
            140 / 2 + paddingHorizontal + (new Random()).nextInt((GameSetting.useSizes.size[0] - 2 * paddingHorizontal - 140)),
            GameSetting.useSizes.size[1] + 100 / 2,
            522, 453,
            world, GameSetting.TRASH_BIT,
            new Vector2(0.1f, 0.1f));
        body.setLinearVelocity(new Vector2(0, (int) -(GameSetting.TRASH_VELOCITY * 0.3) * GameScreen.getMultiplayerSpeed()));

        livesLeft = 20;
        livesLeft *= GameScreen.getMultiplayerHealth();
    }
}
