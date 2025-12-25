package samsungcampus.sprint3.elikur.core.model.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import samsungcampus.sprint3.elikur.core.GameResources;
import samsungcampus.sprint3.elikur.core.GameSetting;
import samsungcampus.sprint3.elikur.core.effects.Effect;
import samsungcampus.sprint3.elikur.core.model.Box;
import samsungcampus.sprint3.elikur.screen.GameScreen;

public class LaserBox extends Box {
    public LaserBox(int x, int y, World world) {
        super(GameResources.BOX_LASER, x, y, 64, 64, world, GameSetting.BOX_BIT);

        body.setLinearVelocity(new Vector2(0, -GameSetting.BOX_VELOCITY));
    }

    @Override
    public void hit() {
        if (GameScreen.player.countEffect.get("Laser") < 1) {
            super.hit();
            GameScreen.player.addEffect(new Effect(2f) {
                @Override
                public void startEffect() {
                    GameScreen.player.countEffect.put("Laser", GameScreen.player.countEffect.get("Laser") + 1);
                    GameScreen.player.laserOn = true;
                    if (GameScreen.starter.audioManager.isSoundOn) GameScreen.starter.audioManager.laserSound.play();
                }

                @Override
                public void endEffect() {
                    GameScreen.player.countEffect.put("Laser", GameScreen.player.countEffect.get("Laser") - 1);
                    GameScreen.player.laserOn = false;
                    if (GameScreen.starter.audioManager.isSoundOn) GameScreen.starter.audioManager.laserSound.stop();
                }
            });
        }
    }
}
