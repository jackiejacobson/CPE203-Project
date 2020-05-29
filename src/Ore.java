import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Ore implements ActiveEntity{


    private final Random rand = new Random();

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int actionPeriod;

    public Ore(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod)

    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.actionPeriod = actionPeriod;
    }
    public Point getPosition(){
        return position;
    }
    public Point setPosition(Point point){
        this.position = new Point(point.x, point.y);
        return position;
    }
    public List<PImage> getImages(){
        return images;
    }
    public int getImageIndex(){
        return imageIndex;
    }



    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
                scheduler.scheduleEvent(this,
                        ActionFactory.createActivityAction(this, world, imageStore),
                        this.actionPeriod);

    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Point pos = this.position;

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        //changed code
        String BLOB_KEY = "blob";
        String BLOB_ID_SUFFIX = " -- blob";
        int BLOB_PERIOD_SCALE = 4;
        int BLOB_ANIMATION_MIN = 50;
        int BLOB_ANIMATION_MAX = 150;
        OreBlob blob = EntityFactory.createOreBlob(this.id + BLOB_ID_SUFFIX, pos,
                this.actionPeriod / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN + rand.nextInt(
                        BLOB_ANIMATION_MAX
                                - BLOB_ANIMATION_MIN),
                imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActions(scheduler, world, imageStore);
    }


    public PImage getCurrentImage( ) {
        return getImages().get(getImageIndex());
    }

    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }
}
