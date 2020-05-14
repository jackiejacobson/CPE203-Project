public interface Action
{
    public Entity entity();
    public WorldModel world();
    public ImageStore imageStore();
    public void executeAction(EventScheduler scheduler);

}
