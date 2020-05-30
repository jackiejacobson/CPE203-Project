import processing.core.PImage;

import java.util.List;
import java.util.Optional;

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

    public boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (moveToHelper(world, target, scheduler)){
            return true;
        }
        else {
            Point nextPos = this.nextPositionEntity(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }
    protected abstract boolean moveToHelper(WorldModel world, Entity target, EventScheduler scheduler);

    abstract Point nextPositionEntity(WorldModel world, Point destPos);


}
