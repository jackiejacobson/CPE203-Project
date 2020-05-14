public interface MoveToEntity extends AnimatedEntity {
    public boolean moveTo(WorldModel world,
                          Entity target,
                          EventScheduler scheduler);
    public Point nextPositionEntity(WorldModel world, Point destPos);
}
