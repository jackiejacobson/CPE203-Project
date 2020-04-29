public final class Action
{
    private ActionKind kind;
    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public Action(
            ActionKind kind,
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            int repeatCount)
    {
        this.kind = kind;
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler scheduler) {
        switch (kind) {
            case ACTIVITY:
                executeActivityAction(scheduler);
                break;

            case ANIMATION:
                executeAnimationAction(scheduler);
                break;
        }
    }

    private void executeAnimationAction(
            EventScheduler scheduler)
    {
        entity.nextImage();

        if (repeatCount != 1) {
            scheduler.scheduleEvent(entity,
                          Functions.createAnimationAction(entity,
                                                Math.max(repeatCount - 1,
                                                         0)),
                          entity.getAnimationPeriod());
        }
    }

    private void executeActivityAction(
            EventScheduler scheduler)
    {
        switch (entity.getKind()) {
            case MINER_FULL:
                entity.executeMinerFullActivity(entity, world,
                                         imageStore, scheduler);
                break;

            case MINER_NOT_FULL:
                entity.executeMinerNotFullActivity(entity, world,
                                            imageStore, scheduler);
                break;

            case ORE:
                entity.executeOreActivity(entity, world,
                                   imageStore, scheduler);
                break;

            case ORE_BLOB:
                entity.executeOreBlobActivity(entity, world,
                                       imageStore, scheduler);
                break;

            case QUAKE:
                entity.executeQuakeActivity(entity, world,
                                     imageStore, scheduler);
                break;

            case VEIN:
                entity.executeVeinActivity(entity, world,
                                    imageStore, scheduler);
                break;

            default:
                throw new UnsupportedOperationException(String.format(
                        "executeActivityAction not supported for %s",
                        entity.getKind()));
        }
    }
}
