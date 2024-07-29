import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Terrain {

    private final double length;
    private final double width;
    private Vector2D globalMaxPosition;

    private List<Wolf> wolfs = new ArrayList<>();
    private Prey prey;

    public List<Obstacle> obstacles = new ArrayList<>();

    public Terrain(double length, double width) {
        this.length = length;
        this.width = width;

        //initialize random obstacles
        for(int i = 0; i < 20; i++) {
            Obstacle obstacle = new Obstacle(this);
            obstacles.add(obstacle);
        }
        Vector2D x1 = new Vector2D(0.0, 0.0);
        Vector2D y1 = new Vector2D(width, 0.0);
        Obstacle xAxisTop = new Obstacle(x1, y1);
        obstacles.add(xAxisTop);
        x1 = new Vector2D(0.0, 0.0);
        y1 = new Vector2D(0.0, length);
        Obstacle yAxisLeft = new Obstacle(x1, y1);
        obstacles.add(yAxisLeft);
        x1 = new Vector2D(width, 0.0);
        y1 = new Vector2D(width, length);
        Obstacle yAxisRight = new Obstacle(x1, y1);
        obstacles.add(yAxisRight);
        x1 = new Vector2D(0.0, length);
        y1 = new Vector2D(width, length);
        Obstacle xAxisBottom = new Obstacle(x1, y1);
        obstacles.add(xAxisBottom);
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public Vector2D getGlobalMaxPosition() {
        return globalMaxPosition;
    }

    public void setGlobalMaxPosition(Vector2D globalMaxPosition) {
        this.globalMaxPosition = globalMaxPosition;
    }

    public List<Wolf> getWolfs() {
        return wolfs;
    }

    public void setWolfs(List<Wolf> wolfs) {
        this.wolfs = wolfs;
    }

    public Prey getPrey() {
        return prey;
    }

    public void setPrey(Prey prey) {
        this.prey = prey;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }
}
