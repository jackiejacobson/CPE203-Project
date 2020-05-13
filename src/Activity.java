public class Activity implements Action {

    private ActiveEntity entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public Activity(
            ActiveEntity entity,
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
        entity.executeActivity(world, imageStore, scheduler);
    }

}
