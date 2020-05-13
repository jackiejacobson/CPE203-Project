import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Blacksmith implements Entity {

    private final String ORE_ID_PREFIX = "ore -- ";
    private final int ORE_CORRUPT_MIN = 20000;
    private final int ORE_CORRUPT_MAX = 30000;

    private final String QUAKE_KEY = "quake";
    private final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    private final Random rand = new Random();
    private final String BLOB_KEY = "blob"; //changed code
    private final String BLOB_ID_SUFFIX = " -- blob";
    private final int BLOB_PERIOD_SCALE = 4;
    private final int BLOB_ANIMATION_MIN = 50;
    private final int BLOB_ANIMATION_MAX = 150;

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private int actionPeriod;
    private int animationPeriod;

    public Blacksmith(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }
    public Point getPosition(){
        return position;
    }

    @Override
    public void executeActivity(Entity entity, WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

    }

    @Override
    public int getAnimationPeriod() {
        return 0;
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

    @Override
    public void scheduleActions(Entity entity, EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

    }


    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }
}
