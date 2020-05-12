public final class Viewport
{
    public int row;
    public int col;
    public int numRows;
    public int numCols;

    public Viewport(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public static Point worldToViewport(Viewport viewport, int col, int row) {
        return new Point(col - viewport.col, row - viewport.row);
    }

    public static Point viewportToWorld(Viewport viewport, int col, int row) {
        return new Point(col + viewport.col, row + viewport.row);
    }

    public boolean contains(Point p) {
        return p.y >= row && p.y < row + numRows
                && p.x >= col && p.x < col + numCols;
    }

    public void shift(int col, int row) {
        this.col = col;
        this.row = row;
    }
}
