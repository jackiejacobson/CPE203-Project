import processing.core.PImage;

import java.util.List;


public class Quake extends AnimatedEntity {


    public Quake(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }
    /*
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
           // Entity entity,
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
                scheduler.scheduleEvent(this,
                        ActionFactory.createActivityAction(this, world, imageStore),
                        this.actionPeriod);
        int QUAKE_ANIMATION_REPEAT_COUNT = 10;
        scheduler.scheduleEvent(this, ActionFactory.createAnimationAction(this,
                QUAKE_ANIMATION_REPEAT_COUNT),
                        this.getAnimationPeriod());
    }
    */
    public void executeActivity(
            //Entity entity,
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }


    /*

    public int getAnimationPeriod() {
                return animationPeriod;

    }

    public PImage getCurrentImage( ) {
        return getImages().get(getImageIndex());
    }

    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }

     */
}
