package samsungcampus.sprint3.elikur.core.model.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import samsungcampus.sprint3.elikur.core.GameResources;
import samsungcampus.sprint3.elikur.core.GameSetting;
import samsungcampus.sprint3.elikur.core.effects.Effect;
import samsungcampus.sprint3.elikur.core.model.Box;
import samsungcampus.sprint3.elikur.screen.GameScreen;

public class AddPowerBox extends Box {
    public AddPowerBox(int x, int y, World world) {
        super(GameResources.BOX_POWER_UP, x, y, 64, 64, world, GameSetting.BOX_BIT);

        body.setLinearVelocity(new Vector2(0, -GameSetting.BOX_VELOCITY));
    }

    @Override
    public void hit() {
        super.hit();
        GameScreen.player.damage++;
    }
}
