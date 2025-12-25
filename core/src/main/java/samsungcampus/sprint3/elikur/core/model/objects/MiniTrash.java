package samsungcampus.sprint3.elikur.core.model.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import samsungcampus.sprint3.elikur.core.GameResources;
import samsungcampus.sprint3.elikur.core.GameSetting;
import samsungcampus.sprint3.elikur.core.model.Trash;
import samsungcampus.sprint3.elikur.screen.GameScreen;

public class MiniTrash extends Trash {
    public MiniTrash(World world) {
        super(GameResources.TRASH_IMG_PATH,
            140 / 2 + paddingHorizontal + (new Random()).nextInt((GameSetting.useSizes.size[0] - 2 * paddingHorizontal - 140)),
            GameSetting.useSizes.size[1] + 100 / 2,
            112, 73,
            world, GameSetting.TRASH_BIT,
            0.2f
        );
        body.setLinearVelocity(new Vector2(0, -GameSetting.TRASH_VELOCITY * GameScreen.getMultiplayerSpeed()));

        livesLeft = 1;
        livesLeft *= GameScreen.getMultiplayerHealth();
    }
}
