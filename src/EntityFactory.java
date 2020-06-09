import processing.core.PImage;

import java.util.List;

public class EntityFactory {

    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;

    private static final String ROBOT_ID = "robot";
    private static final int ROBOT_ACTION_PERIOD = 1000;
    private static final int ROBOT_ANIMATION_PERIOD = 1100;

    private static final String ASTRONAUT_ID = "astronaut";
    private static final int ASTRONAUT_ACTION_PERIOD = 1000;
    private static final int ASTRONAUT_ANIMATION_PERIOD = 1100;

    private static final String EXPLOSION_ID = "explosion";
    private static final int EXPLOSION_ANIMATION_PERIOD = 50;
    private static final int EXPLOSION_ACTION_PERIOD = 50;


    public static Blacksmith createBlacksmith(
            String id, Point position, List<PImage> images)
    {
        return new Blacksmith(id, position, images);
    }

    public static MinerFull createMinerFull(
            String id,
            int resourceLimit,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new MinerFull( id, position, images,
                          resourceLimit, actionPeriod,
                          animationPeriod);
    }

    public static MinerNotFull createMinerNotFull(
            String id,
            int resourceLimit,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new MinerNotFull(id, position, images,
                          resourceLimit, 0, actionPeriod, animationPeriod);
    }

    public static Obstacle createObstacle(
            String id, Point position, List<PImage> images)
    {
        return new Obstacle(id, position, images);
    }

    public static Ore createOre(
            String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Ore(id, position, images,
                          actionPeriod);
    }

    public static OreBlob createOreBlob(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new OreBlob( id, position, images,
                          actionPeriod, animationPeriod);
    }

    public static Quake createQuake(
            Point position, List<PImage> images)
    {
        int QUAKE_ANIMATION_PERIOD = 100;
        return new Quake(QUAKE_ID, position, images,
                          QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }

    public static Vein createVein(
            String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Vein(id, position, images,
                          actionPeriod);
    }

    public static Robot createRobot(
            Point position,
            List<PImage> images)
    {
        return new Robot(ROBOT_ID, position, images, ROBOT_ACTION_PERIOD, ROBOT_ANIMATION_PERIOD);
    }

    public static Astronaut createAstronaut(
            Point position,
            List<PImage> images)
    {
        return new Astronaut(ASTRONAUT_ID, position, images, ASTRONAUT_ACTION_PERIOD, ASTRONAUT_ANIMATION_PERIOD);
    }
    public static Explosion createExplosion(
            Point position, List<PImage> images)
    {

        return new Explosion(EXPLOSION_ID, position, images,
                EXPLOSION_ACTION_PERIOD, EXPLOSION_ANIMATION_PERIOD);
    }
}
