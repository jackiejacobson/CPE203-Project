import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public interface Entity
{
    public Point getPosition();
    public void executeActivity(Entity entity,
                                WorldModel world,
                                ImageStore imageStore,
                                EventScheduler scheduler);
    public int getAnimationPeriod();
    public void nextImage();
    public List<PImage> getImages();
    public int getImageIndex();

    public void scheduleActions(Entity entity, EventScheduler scheduler, WorldModel world, ImageStore imageStore);

    public Point setPosition(Point pos);

    public PImage getCurrentImage();
}
