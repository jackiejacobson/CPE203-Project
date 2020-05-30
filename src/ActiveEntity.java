import processing.core.PImage;

import java.util.List;

public abstract class ActiveEntity extends Entity {

   // private String id;
  //  private Point position;
   // private List<PImage> images;
  //  private int imageIndex;
    private int actionPeriod;

    public ActiveEntity(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod)

    {
        super(id, position, images);
        this.actionPeriod = actionPeriod;
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                ActionFactory.createActivityAction(this, world, imageStore),
                this.actionPeriod);
    }



    public int getActionPeriod(){return actionPeriod;}



    public abstract void executeActivity(WorldModel world,
                                ImageStore imageStore,
                                EventScheduler scheduler);

}
