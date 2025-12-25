package samsungcampus.sprint3.elikur.core.spaces;

import static samsungcampus.sprint3.elikur.core.GameSetting.POSITION_ITERATIONS;
import static samsungcampus.sprint3.elikur.core.GameSetting.STEP_TIME;
import static samsungcampus.sprint3.elikur.core.GameSetting.VELOCITY_ITERATIONS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class GameWorld {
    private World world;
    private Vector2 vector2;
    private boolean doSleep;

    float accumulator = 0;

    public GameWorld(Vector2 vector2, boolean doSleep) {
        world = new World(vector2, doSleep);

        this.vector2 = vector2;
        this.doSleep = doSleep;
    }

    public void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += delta;

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    public World getWorld() {
        return world;
    }

    public void dispose() {
        world.clearForces();
        world.dispose();

        world = new World(vector2, doSleep);
        accumulator = 0;
    }

}
