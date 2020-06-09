import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Robot extends MoveToEntity{
    private final Random rand = new Random();

    public Robot(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
    }

    protected boolean moveToHelper(WorldModel world,
                                   Entity target,
                                   EventScheduler scheduler)
    {
        if (this.getPosition().adjacent(target.getPosition())) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else{
            return false;
        }
    }

    public Point nextPositionEntity(
            WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - getPosition().x);
        Point newPos = new Point(getPosition().x + horiz, getPosition().y);

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 || (occupant.isPresent() && !(occupant.get().getClass()
                == Ore.class)))
        {
            int vert = Integer.signum(destPos.y - getPosition().y);
            newPos = new Point(getPosition().x, getPosition().y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 || (occupant.isPresent() && !(occupant.get().getClass()
                    == Ore.class)))
            {
                newPos = getPosition();
            }
        }

        return newPos;
    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> BSTarget =
                world.findNearest( this.getPosition(), Blacksmith.class);
        long nextPeriod = this.getActionPeriod();

        if (BSTarget.isPresent()) {
            Point tgtPos = BSTarget.get().getPosition();

            if (moveTo(world, BSTarget.get(), scheduler)) {
                String QUAKE_KEY = "explosion";
                Explosion explosion = EntityFactory.createExplosion(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(explosion);
                nextPeriod += this.getActionPeriod();
                explosion.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this,
                ActionFactory.createActivityAction(this, world, imageStore),
                nextPeriod);
    }
}
