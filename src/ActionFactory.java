public class ActionFactory {

    public static Action createAnimationAction(Entity entity, int repeatCount) {
        return new Animation((AnimatedEntity) entity, repeatCount);
    }

    public static Action createActivityAction(
            Entity entity, WorldModel world, ImageStore imageStore)
    {
        return new Activity((ActiveEntity) entity, world, imageStore);
    }
}
