public class Activity implements Action {

    private Entity entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public Activity(
            Entity entity,
            WorldModel world,
            ImageStore imageStore,
            int repeatCount)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    public Entity entity() {return entity;}
    public WorldModel world() {return world;}
    public ImageStore imageStore() { return imageStore;}
    public int repeatCount() {return repeatCount;}

    public void executeAction(
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
