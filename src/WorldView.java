import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

public final class WorldView
{
    private PApplet screen;
    private WorldModel world;
    private int tileWidth;
    private int tileHeight;
    private Viewport viewport;

    public WorldView(
            int numRows,
            int numCols,
            PApplet screen,
            WorldModel world,
            int tileWidth,
            int tileHeight)
    {
        this.screen = screen;
        this.world = world;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.viewport = new Viewport(numRows, numCols);
    }

    public void shiftView(int colDelta, int rowDelta) {
        int newCol = clamp(viewport.col + colDelta, 0,
                           world.getNumCols() - viewport.numCols);
        int newRow = clamp(viewport.row + rowDelta, 0,
                           world.getNumRows() - viewport.numRows);

        viewport.shift(newCol, newRow);
    }
    public int getViewPortRow(){return viewport.row;}
    public int getViewPortCol(){return viewport.col;}

    private int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }

    private void drawBackground() {
        for (int row = 0; row < viewport.numRows; row++) {
            for (int col = 0; col < viewport.numCols; col++) {
                Point worldPoint = Viewport.viewportToWorld(viewport, col, row);
                Optional<PImage> image =
                        Background.getBackgroundImage(world, worldPoint);
                if (image.isPresent()) {
                    screen.image(image.get(), col * tileWidth,
                                      row * tileHeight);
                }
            }
        }
    }

    private void drawEntities() {
        for (Entity entity : world.getEntities()) {
            Point pos = entity.getPosition();

            if (viewport.contains(pos)) {
                Point viewPoint = Viewport.worldToViewport(viewport, pos.x, pos.y);
                screen.image(entity.getCurrentImage(),
                                  viewPoint.x * tileWidth,
                                  viewPoint.y * tileHeight);
            }
        }
    }

    public void drawViewport() {
        drawBackground();
        drawEntities();
    }
}
