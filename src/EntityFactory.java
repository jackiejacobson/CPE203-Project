import processing.core.PImage;

import java.util.List;

public class EntityFactory {

    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static int QUAKE_ANIMATION_PERIOD = 100;

    static Entity createBlacksmith(
            String id, Point position, List<PImage> images)
    {
        return new Blacksmith(id, position, images, 0, 0, 0,
                          0);
    }

    public static Entity createMinerFull(
            String id,
            int resourceLimit,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new MinerFull( id, position, images,
                          resourceLimit, resourceLimit, actionPeriod,
                          animationPeriod);
    }

    public static Entity createMinerNotFull(
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

    static Entity createObstacle(
            String id, Point position, List<PImage> images)
    {
        return new Obstacle(id, position, images);
    }

    public static Entity createOre(
            String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Ore(id, position, images, 0, 0,
                          actionPeriod, 0);
    }

    public static Entity createOreBlob(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new OreBlob( id, position, images, 0, 0,
                          actionPeriod, animationPeriod);
    }

    public static Entity createQuake(
            Point position, List<PImage> images)
    {
        return new Quake(QUAKE_ID, position, images, 0, 0,
                          QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }

    static Entity createVein(
            String id, Point position, int actionPeriod, List<PImage> images)
    {
        return new Vein(id, position, images, 0, 0,
                          actionPeriod, 0);
    }
}
