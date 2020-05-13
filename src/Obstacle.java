import processing.core.PImage;

import java.util.List;


public class Obstacle implements Entity{

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    public Obstacle(
            String id,
            Point position,
            List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
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
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

    }


    public PImage getCurrentImage( ) {
        return getImages().get(getImageIndex());
    }

    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }
}
