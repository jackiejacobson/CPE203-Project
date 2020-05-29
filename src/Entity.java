import java.util.List;
import processing.core.PImage;

public interface Entity
{
    public Point setPosition(Point pos);
    public Point getPosition();

    public List<PImage> getImages();
    public int getImageIndex();
    public PImage getCurrentImage();
}
