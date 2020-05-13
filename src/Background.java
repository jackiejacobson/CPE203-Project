import java.util.List;
import java.util.Optional;

import processing.core.PImage;

public final class Background
{
    private String id;
    private List<PImage> images;
    private int imageIndex;

    public Background(String id, List<PImage> images) {
        this.id = id;
        this.images = images;
    }

    public PImage getCurrentImage() {
            return getImages().get(
                    getImageIndex());
    }

    public String getId(){
        return id;
    }
    public List<PImage> getImages(){
        return images;
    }
    public int getImageIndex(){
        return imageIndex;
    }

    public static Optional<PImage> getBackgroundImage(
            WorldModel world, Point pos)
    {
        if (world.withinBounds(pos)) {
            return Optional.of(getBackgroundCell(world, pos).getCurrentImage());
        }
        else {
            return Optional.empty();
        }
    }

    private static Background getBackgroundCell(WorldModel world, Point pos) {
        return world.getBackground()[pos.y][pos.x];
    }

    public void setBackgroundCell(
            WorldModel world, Point pos, Background background)
    {
        world.getBackground()[pos.y][pos.x] = background;
    }
}
