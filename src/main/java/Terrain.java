import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class Terrain {
    private final Random rand = new Random();

    private final double length;
    private final double width;
    private final double obstacleRadius = 5.0;
    private Vector2D globalMaxPosition;

    public Prey prey;

    public List<Wolf> wolfs;

    public List<Vector2D> obstacles = new ArrayList<>();

    public Terrain(double length, double width, Prey prey, List<Wolf> wolfs) {
        this.length = length;
        this.width = width;
        this.prey = prey;
        this.wolfs = wolfs;

        //initialize random obstacles
        for(int i = 0; i < 3; i++) {
            double randomX = rand.nextDouble(this.getWidth());
            double randomY = rand.nextDouble(this.getLength());
            Vector2D obstacle = new Vector2D(randomX, randomY);
            obstacle.add(obstacle);
        }
    }
}
