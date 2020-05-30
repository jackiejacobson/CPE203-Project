import processing.core.PImage;

import java.util.List;

public abstract class MoveToEntity extends AnimatedEntity {

    //private int imageIndex;
    public MoveToEntity(String id,
                        Point position,
                        List<PImage> images,
                        int actionPeriod, int animationPeriod)
    {
        super(id, position,images,actionPeriod,animationPeriod);
        //this.imageIndex = imageIndex;
    }

    abstract boolean moveTo(WorldModel world,
                          Entity target,
                          EventScheduler scheduler);

    abstract Point nextPositionEntity(WorldModel world, Point destPos);

    public void scheduleActions(
            //Entity entity,
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                ActionFactory.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
        scheduler.scheduleEvent(this,
                ActionFactory.createAnimationAction(this, 0),
                this.getAnimationPeriod());
    }
}
