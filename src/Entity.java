import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public final class Entity
{
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

    public EntityKind kind;
    public String id;
    public Point position;
    public List<PImage> images;
    public int imageIndex;
    public int resourceLimit;
    public int resourceCount;
    public int actionPeriod;
    public int animationPeriod;

    public Entity(
            EntityKind kind,
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int resourceCount,
            int actionPeriod,
            int animationPeriod)
    {
        this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
    }

    private boolean moveToNotFull(
            Entity miner,
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        //if (Point.adjacent(miner.position, target.position)) {
        if (miner.position.adjacent(target.position)) {
            miner.resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else {
            Point nextPos = miner.nextPositionMiner(world, target.position);

            if (!miner.position.equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(miner, nextPos);
            }
            return false;
        }
    }

    private boolean moveToFull(
            Entity miner,
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        //if (Point.adjacent(miner.position, target.position))
        if (miner.position.adjacent(target.position)) {
            return true;
        }
        else {
            Point nextPos = miner.nextPositionMiner(world, target.position);

            if (!miner.position.equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity( miner, nextPos);
            }
            return false;
        }
    }

    private boolean moveToOreBlob(
            Entity blob,
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (blob.position.adjacent(target.position)) {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents( target);
            return true;
        }
        else {
            Point nextPos = blob.nextPositionOreBlob(world, target.position);

            if (!blob.position.equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(blob, nextPos);
            }
            return false;
        }
    }

    public Point nextPositionMiner(
            WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - position.x);
        Point newPos = new Point(position.x + horiz, position.y);

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.y - position.y);
            newPos = new Point(position.x, position.y + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = position;
            }
        }

        return newPos;
    }

    public Point nextPositionOreBlob(
            WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - position.x);
        Point newPos = new Point(position.x + horiz, position.y);

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 || (occupant.isPresent() && !(occupant.get().kind
                == EntityKind.ORE)))
        {
            int vert = Integer.signum(destPos.y - position.y);
            newPos = new Point(position.x, position.y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 || (occupant.isPresent() && !(occupant.get().kind
                    == EntityKind.ORE)))
            {
                newPos = position;
            }
        }

        return newPos;
    }

    public boolean transformNotFull(
            Entity entity,
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (entity.resourceCount >= entity.resourceLimit) {
            Entity miner = Functions.createMinerFull(entity.id, entity.resourceLimit,
                                           entity.position, entity.actionPeriod,
                                           entity.animationPeriod,
                                           entity.images);

            world.removeEntity(entity);
            scheduler.unscheduleAllEvents( entity);

            world.addEntity(miner);
            entity.scheduleActions(miner, scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public void transformFull(
            Entity entity,
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        Entity miner = Functions.createMinerNotFull(entity.id, entity.resourceLimit,
                                          entity.position, entity.actionPeriod,
                                          entity.animationPeriod,
                                          entity.images);

        world.removeEntity(entity);
        scheduler.unscheduleAllEvents(entity);

        world.addEntity(miner);
        entity.scheduleActions(miner, scheduler, world, imageStore);
    }

    public void scheduleActions(
            Entity entity,
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        switch (entity.kind) {
            case MINER_FULL:
                scheduler.scheduleEvent(entity,
                              Functions.createActivityAction(entity, world, imageStore),
                              entity.actionPeriod);
                scheduler.scheduleEvent(entity,
                              Functions.createAnimationAction(entity, 0),
                              entity.getAnimationPeriod());
                break;

            case MINER_NOT_FULL:
                scheduler.scheduleEvent(entity,
                              Functions.createActivityAction(entity, world, imageStore),
                              entity.actionPeriod);
                scheduler.scheduleEvent(entity,
                              Functions.createAnimationAction(entity, 0),
                              entity.getAnimationPeriod());
                break;

            case ORE:
                scheduler.scheduleEvent(entity,
                              Functions.createActivityAction(entity, world, imageStore),
                              entity.actionPeriod);
                break;

            case ORE_BLOB:
                scheduler.scheduleEvent(entity,
                              Functions.createActivityAction(entity, world, imageStore),
                              entity.actionPeriod);
                scheduler.scheduleEvent(entity,
                              Functions.createAnimationAction(entity, 0),
                              entity.getAnimationPeriod());
                break;

            case QUAKE:
                scheduler.scheduleEvent(entity,
                              Functions.createActivityAction(entity, world, imageStore),
                              entity.actionPeriod);
                scheduler.scheduleEvent(entity, Functions.createAnimationAction(entity,
                                                                       QUAKE_ANIMATION_REPEAT_COUNT),
                              entity.getAnimationPeriod());
                break;

            case VEIN:
                scheduler.scheduleEvent(entity,
                              Functions.createActivityAction(entity, world, imageStore),
                              entity.actionPeriod);
                break;

            default:
        }
    }

    public void executeQuakeActivity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(entity);
        world.removeEntity(entity);
    }

    public void executeVeinActivity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(entity.position);

        if (openPt.isPresent()) {
            Entity ore = Functions.createOre(ORE_ID_PREFIX + entity.id, openPt.get(),
                                   ORE_CORRUPT_MIN + rand.nextInt(
                                           ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                                   imageStore.getImageList(Functions.ORE_KEY));
            world.addEntity(ore);
            scheduleActions(ore, scheduler, world, imageStore);
        }

                    scheduler.scheduleEvent(entity,
                      Functions.createActivityAction(entity, world, imageStore),
                      entity.actionPeriod);
    }

    public void executeOreBlobActivity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> blobTarget =
                world.findNearest( entity.position, EntityKind.VEIN);
        long nextPeriod = entity.actionPeriod;

        if (blobTarget.isPresent()) {
            Point tgtPos = blobTarget.get().position;

            if (moveToOreBlob(entity, world, blobTarget.get(), scheduler)) {
                Entity quake = Functions.createQuake(tgtPos,
                                           imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += entity.actionPeriod;
                scheduleActions(quake, scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(entity,
                      Functions.createActivityAction(entity, world, imageStore),
                      nextPeriod);
    }

    public void executeOreActivity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Point pos = entity.position;

        world.removeEntity(entity);
        scheduler.unscheduleAllEvents(entity);

        Entity blob = Functions.createOreBlob(entity.id + BLOB_ID_SUFFIX, pos,
                                    entity.actionPeriod / BLOB_PERIOD_SCALE,
                                    BLOB_ANIMATION_MIN + rand.nextInt(
                                            BLOB_ANIMATION_MAX
                                                    - BLOB_ANIMATION_MIN),
                                    imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        scheduleActions(blob, scheduler, world, imageStore);
    }

    public void executeMinerNotFullActivity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> notFullTarget =
                world.findNearest(entity.position, EntityKind.ORE);

        if (!notFullTarget.isPresent() || !moveToNotFull(entity, world,
                                                         notFullTarget.get(),
                                                         scheduler)
                || !transformNotFull(entity, world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(entity,
                          Functions.createActivityAction(entity, world, imageStore),
                          entity.actionPeriod);
        }
    }

    public void executeMinerFullActivity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> fullTarget =
                world.findNearest(entity.position, EntityKind.BLACKSMITH);

        if (fullTarget.isPresent() && moveToFull(entity, world,
                                                 fullTarget.get(), scheduler))
        {
            transformFull(entity, world, scheduler, imageStore);
        }
        else {
            scheduler.scheduleEvent(entity,
                          Functions.createActivityAction(entity, world, imageStore),
                          entity.actionPeriod);
        }
    }


    public int getAnimationPeriod() {
        switch (kind) {
            case MINER_FULL:
            case MINER_NOT_FULL:
            case ORE_BLOB:
            case QUAKE:
                return animationPeriod;
            default:
                throw new UnsupportedOperationException(
                        String.format("getAnimationPeriod not supported for %s",
                                      kind));
        }
    }

    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }
}
