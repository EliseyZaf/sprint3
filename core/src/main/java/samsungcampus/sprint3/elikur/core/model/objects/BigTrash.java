package samsungcampus.sprint3.elikur.core.model.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import samsungcampus.sprint3.elikur.core.GameResources;
import samsungcampus.sprint3.elikur.core.GameSetting;
import samsungcampus.sprint3.elikur.core.model.Trash;
import samsungcampus.sprint3.elikur.screen.GameScreen;

public class BigTrash extends Trash {
    public BigTrash(World world) {
        super(GameResources.TRASH1_IMG_PATH,
            140 / 2 + paddingHorizontal + (new Random()).nextInt((GameSetting.useSizes.size[0] - 2 * paddingHorizontal - 140)),
            GameSetting.useSizes.size[1] + 100 / 2,
            226, 123,
            world, GameSetting.TRASH_BIT,
            new Vector2(0.2f, 0.35f)
        );
        body.setLinearVelocity(new Vector2(0, (int) -(GameSetting.TRASH_VELOCITY * 0.75) * GameScreen.getMultiplayerSpeed()));

        livesLeft = 4;
        livesLeft *= GameScreen.getMultiplayerHealth();
    }
}
