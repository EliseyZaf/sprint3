package samsungcampus.sprint3.elikur.core.effects;

public abstract class Effect {
    public float elapsedTime = 0, finalTime;

    public Effect(float finalTime) {
        this.finalTime = finalTime;
        startEffect();
    }

    public boolean update(float delta) {
        elapsedTime += delta;

        if (elapsedTime >= finalTime) {
            endEffect();
            return true;
        }

        return false;
    }

    public abstract void startEffect();
    public abstract void endEffect();
}
