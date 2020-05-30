import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Ore extends ActiveEntity {


    private final Random rand = new Random();

    public Ore(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod) {
        super(id, position, images, actionPeriod);
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler) {
        Point pos = getPosition();

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        //changed code
        String BLOB_KEY = "blob";
        String BLOB_ID_SUFFIX = " -- blob";
        int BLOB_PERIOD_SCALE = 4;
        int BLOB_ANIMATION_MIN = 50;
        int BLOB_ANIMATION_MAX = 150;
        OreBlob blob = EntityFactory.createOreBlob(getId() + BLOB_ID_SUFFIX, pos,
                getActionPeriod() / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN + rand.nextInt(
                        BLOB_ANIMATION_MAX
                                - BLOB_ANIMATION_MIN),
                imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }
}
