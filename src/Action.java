public interface Action
{
    public Entity entity();
    public WorldModel world();
    public ImageStore imageStore();
    public int repeatCount();
    public void executeAction(EventScheduler scheduler);

}
