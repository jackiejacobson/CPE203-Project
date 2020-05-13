import processing.core.PImage;

import java.util.List;


public class Obstacle implements Entity{

    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;

    public Obstacle(
            String id,
            Point position,
            List<PImage> images)
    {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }


    public Point getPosition(){
        return position;
    }

    public Point setPosition(Point point){
        this.position = new Point(point.x, point.y);
        return position;
    }
    public List<PImage> getImages(){
        return images;
    }
    public int getImageIndex(){
        return imageIndex;
    }



    public PImage getCurrentImage( ) {
        return getImages().get(getImageIndex());
    }

    public void nextImage() {
        imageIndex = (imageIndex + 1) % images.size();
    }
}
