import processing.core.PImage;

import java.util.List;


public class Explosion extends Quake {


    public Explosion(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, position, images, actionPeriod, animationPeriod);
    }


}
