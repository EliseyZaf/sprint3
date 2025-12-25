package samsungcampus.sprint3.elikur.core;

public final class GameSetting {
    public enum ScreenSizes {
        MOBILE(720, 1640),
        PC(360, 820);

        public int[] size;
        ScreenSizes(int width, int height) {
            size = new int[] {width, height};
        }
    }

    public static final ScreenSizes useSizes = ScreenSizes.MOBILE;

    // WORLD SETTINGS
    public static final float STEP_TIME = 1f / 60;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 6;
    public static final float SCALE = 0.05f;

    // OBJECTS SETTINGS
    public static float TRASH_VELOCITY = 16;
    public static float BOX_VELOCITY = 12;
    public static final long STARTING_TRASH_APPEARANCE_COOL_DOWN = 2000;
    public static final float BULLET_VELOCITY = 200;
    public static final long SHOOTING_COOL_DOWN = 800;
    public static final long LASER_DAMAGE = 23;

    // cBITs
    public static final short TRASH_BIT = 1;
    public static final short SHIP_BIT = 2;
    public static final short BULLET_BIT = 4;
    public static final short BOX_BIT = 8;
}
