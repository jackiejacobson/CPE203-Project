import java.util.List;
import processing.core.PImage;

public abstract class Entity
{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    public Entity(
            String id,
            Point position,
            List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }

    public Point setPosition(Point point)
        {this.position = new Point(point.x, point.y);
        return position;}

    public Point getPosition() {return position;}
    public String getId() {return id;}
    public List<PImage> getImages(){return images;}
    public int getImageIndex(){return imageIndex;}
    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }
    public PImage getCurrentImage(){return getImages().get(getImageIndex()); }
}
