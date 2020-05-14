public class Activity implements Action {

    private ActiveEntity entity;
    private WorldModel world;
    private ImageStore imageStore;

    public Activity(
            ActiveEntity entity,
            WorldModel world,
            ImageStore imageStore)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public Entity entity() {return entity;}
    public WorldModel world() {return world;}
    public ImageStore imageStore() { return imageStore;}

    public void executeAction(
            EventScheduler scheduler)
    {
        entity.executeActivity(world, imageStore, scheduler);
    }

}
