import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

public final class WorldView
{
    public PApplet screen;
    public WorldModel world;
    public int tileWidth;
    public int tileHeight;
    public Viewport viewport;

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

    private static Point worldToViewport(Viewport viewport, int col, int row) {
        return new Point(col - viewport.col, row - viewport.row);
    }

    private static Point viewportToWorld(Viewport viewport, int col, int row) {
        return new Point(col + viewport.col, row + viewport.row);
    }

    public void shiftView(int colDelta, int rowDelta) {
        int newCol = clamp(viewport.col + colDelta, 0,
                           world.getNumCols() - viewport.numCols);
        int newRow = clamp(viewport.row + rowDelta, 0,
                           world.getNumRows() - viewport.numRows);

        viewport.shift(newCol, newRow);
    }

    private int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }

    private void drawBackground() {
        for (int row = 0; row < viewport.numRows; row++) {
            for (int col = 0; col < viewport.numCols; col++) {
                Point worldPoint = WorldView.viewportToWorld(viewport, col, row);
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
            Point pos = entity.position;

            if (viewport.contains(pos)) {
                Point viewPoint = WorldView.worldToViewport(viewport, pos.x, pos.y);
                screen.image(ImageStore.getCurrentImage(entity),
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
