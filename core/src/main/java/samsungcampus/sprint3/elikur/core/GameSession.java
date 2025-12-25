package samsungcampus.sprint3.elikur.core;

import com.badlogic.gdx.utils.TimeUtils;

import samsungcampus.sprint3.elikur.Starter;
import samsungcampus.sprint3.elikur.core.model.Trash;
import samsungcampus.sprint3.elikur.core.model.objects.BigTrash;
import samsungcampus.sprint3.elikur.core.model.objects.HugeTrash;
import samsungcampus.sprint3.elikur.core.model.objects.MiniTrash;
import samsungcampus.sprint3.elikur.core.model.objects.XLTrash;
import samsungcampus.sprint3.elikur.screen.GameScreen;

public class GameSession {
    private long startSessionTime;
    private long pauseTime;
    private long nextTrashSpawnTime;
    private long maxSpawnInSec = 1000 / 8;

    private float[] chanses = new float[]{0.7f, 0.9f, 0.97f, 1f};

    public GameSession() {
        startSessionTime = TimeUtils.millis();
    }

    public void startGame() {
        startSessionTime = TimeUtils.millis();
        nextTrashSpawnTime = startSessionTime + (long) (GameSetting.STARTING_TRASH_APPEARANCE_COOL_DOWN
            * getTrashPeriodCoolDown());
    }

    public Trash shouldSpawnTrash(Starter starter) {
        if (nextTrashSpawnTime <= TimeUtils.millis()) {
            nextTrashSpawnTime = TimeUtils.millis() + Math.max(maxSpawnInSec, (long) (GameSetting.STARTING_TRASH_APPEARANCE_COOL_DOWN
                * getTrashPeriodCoolDown()));

            return spawnTrash(starter);
        }
        return null;
    }

    private float getTrashPeriodCoolDown() {
        return (float) Math.exp(-0.01 * (TimeUtils.millis() - startSessionTime) / 1000);
    }

    public void pause() {
        pauseTime = TimeUtils.millis();
    }

    public void resumeGame() {
        startSessionTime = TimeUtils.millis() - (pauseTime - startSessionTime);
        nextTrashSpawnTime = startSessionTime + (long) (GameSetting.STARTING_TRASH_APPEARANCE_COOL_DOWN
            * getTrashPeriodCoolDown());
    }

    private Trash spawnTrash(Starter starter) {
        float chance = (float) Math.random();

        if (chance <= chanses[0])      return new MiniTrash(starter.world.getWorld());
        else if (chance <= chanses[1]) return new BigTrash(starter.world.getWorld());
        else if (chance <= chanses[2]) return new HugeTrash(starter.world.getWorld());
        else if (chance <= chanses[3]) return new XLTrash (starter.world.getWorld());

        return null;
    }
}
