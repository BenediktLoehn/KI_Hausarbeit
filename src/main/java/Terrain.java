import java.util.ArrayList;
import java.util.List;

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
        for(int i = 0; i < 10; i++) {
            Obstacle obstacle = new Obstacle(this);
            obstacles.add(obstacle);
        }
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
}
