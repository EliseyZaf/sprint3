package samsungcampus.sprint3.elikur.core.model.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import javax.swing.SpringLayout;

import samsungcampus.sprint3.elikur.core.GameResources;
import samsungcampus.sprint3.elikur.core.GameSetting;
import samsungcampus.sprint3.elikur.core.effects.Effect;
import samsungcampus.sprint3.elikur.core.model.Box;
import samsungcampus.sprint3.elikur.screen.GameScreen;

public class TimeDelayBox extends Box {
    public TimeDelayBox(int x, int y, World world) {
        super(GameResources.BOX_DELAY, x, y, 64, 64, world, GameSetting.BOX_BIT);

        body.setLinearVelocity(new Vector2(0, -GameSetting.BOX_VELOCITY));
    }

    @Override
    public void hit() {
        if (GameScreen.player.countEffect.get("TimeDelay") < 2) {
            super.hit();
            GameScreen.player.addEffect(new Effect(6) {
                @Override
                public void startEffect() {
                    GameScreen.player.countEffect.put("TimeDelay", GameScreen.player.countEffect.get("TimeDelay") + 1);
                    GameScreen.player.shootingCooldown /= 3;
                }

                @Override
                public void endEffect() {
                    GameScreen.player.countEffect.put("TimeDelay", GameScreen.player.countEffect.get("TimeDelay") - 1);
                    GameScreen.player.shootingCooldown *= 3;
                }
            });
        }
    }
}
