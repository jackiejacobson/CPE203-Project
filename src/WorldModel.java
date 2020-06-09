import java.util.*;

public final class WorldModel
{
    private static final int ORE_REACH = 1;
    private final int numRows;
    private final int numCols;
    private Background background[][];
    private Entity occupancy[][];
    private Set<Entity> entities;

    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();

        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.background[row], defaultBackground);
        }
    }
    public int getNumRows(){
        return numRows;
    }
    public int getNumCols(){
        return numCols;
    }
    public Set<Entity> getEntities(){
        return entities;
    }

    public Background[][] getBackground(){
        return background;
    }

    private Optional<Entity> nearestEntity(
            List<Entity> entities, Point pos)
    {
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        else {
            Entity nearest = entities.get(0);
            int nearestDistance = nearest.getPosition().distanceSquared(pos);

            for (Entity other : entities) {
                int otherDistance = other.getPosition().distanceSquared(pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    public Optional<Entity> findNearest(
             Point pos, Class kind)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Entity entity : entities) {
            if (kind.isInstance(entity))
            {
                ofType.add(entity);
            }
        }

        return nearestEntity(ofType, pos);
    }

    public Optional<Point> findOpenAround( Point pos) {
        for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++) {
            for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++) {
                Point newPt = new Point(pos.x + dx, pos.y + dy);
                if (withinBounds(newPt) && !isOccupied(newPt)) {
                    return Optional.of(newPt);
                }
            }
        }

        return Optional.empty();
    }

    public void tryAddEntity( Entity entity) {
        if (isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }

        addEntity(entity);
    }

    public boolean isOccupied(Point pos) {
        return withinBounds(pos) && getOccupancyCell(pos) != null;
    }

    public Optional<Entity> getOccupant(Point pos) {
        if (isOccupied(pos)) {
            return Optional.of(getOccupancyCell(pos));
        }
        else {
            return Optional.empty();
        }
    }

    public void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (withinBounds(pos) && !pos.equals(oldPos)) {
            setOccupancyCell(oldPos, null);
            removeEntityAt(pos);
            setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    public void removeEntity(Entity entity) {
        removeEntityAt(entity.getPosition());
    }

    private void removeEntityAt( Point pos) {
        if (withinBounds(pos) && getOccupancyCell(pos) != null) {
            Entity entity = getOccupancyCell( pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPosition(new Point(-1, -1)) ;
            entities.remove(entity);
            setOccupancyCell(pos, null);
        }
    }

    private Entity getOccupancyCell(Point pos) {
        return occupancy[pos.y][pos.x];
    }

    public boolean withinBounds(Point pos) {
        return pos.y >= 0 && pos.y < numRows && pos.x >= 0
                && pos.x < numCols;
    }

    /*
           Assumes that there is no entity currently occupying the
           intended destination cell.
        */
    public void addEntity(Entity entity) {
        if (withinBounds(entity.getPosition())) {
            setOccupancyCell(entity.getPosition(), entity);
            entities.add(entity);
        }
    }

    private void setOccupancyCell(
            Point pos, Entity entity)
    {
        occupancy[pos.y][pos.x] = entity;
    }

    public void setBackground(
            Point pos, Background background)
    {
        if (withinBounds( pos)) {
            background.setBackgroundCell(this, pos, background);
        }
    }

    //under construction

    public void createWCE(Point click, ImageStore imageStore) {
        int radius = 1;
        int startX = click.x - radius;
        int startY = click.y - radius;
        int endX = click.x + radius;
        int endY = click.y + radius;
        for (int row = startX; row <= endX; row++) {
            for (int col = startY; col <= endY; col++) {
                Point p = new Point(row, col);
                if (withinBounds(p)) {

                    setBackground(p, new Background("space", imageStore.getImages().get("space")));
                }
            }
        }
        setBackground(click, new Background("planet_background", imageStore.getImages().get("planet_background")));

    }

    public boolean withinRadius(Point click, Point entityPos, int radius) {
        int startX = click.x - radius;
        int startY = click.y - radius;
        int endX = click.x + radius;
        int endY = click.y + radius;
        return click.y >= 0 && entityPos.y >= 0 && startY < entityPos.y && entityPos.y < endY &&
                click.x >= 0 && entityPos.x >= 0 &&startX < entityPos.x && entityPos.x < endX;
    }

    public void transformEntity(Point click, EventScheduler scheduler, ImageStore imageStore) {
        int radius = 4;
        int startX = click.x - radius;
        int startY = click.y - radius;
        int endX = click.x + radius;
        int endY = click.y + radius;
        Optional<Entity> oreBlobPositions = findNearest(click, OreBlob.class);
        for (int row = startX; row <= endX; row++) {
            for (int col = startY; col <= endY; col++) {
                Point p = new Point(row, col);
                if (withinBounds(p) &&
                        oreBlobPositions.isPresent() &&
                        withinRadius(click, oreBlobPositions.get().getPosition(), radius)) {
                    ((OreBlob)oreBlobPositions.get()).transformRobot(this, scheduler, imageStore);
                    oreBlobPositions = findNearest(click, OreBlob.class);
                }
            }
        }
    }
}
