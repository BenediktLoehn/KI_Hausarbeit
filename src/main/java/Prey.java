import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class Prey {
    //constants
    private final double CHI = 0.729843788;
    private final double C = 2.05;

    private final Random rand = new Random();

    private double a;
    private double v;
    private Vector2D x;

    private Vector2D e1 = new Vector2D(rand.nextDouble(), rand.nextDouble());
    private Vector2D e2 = new Vector2D(rand.nextDouble(), rand.nextDouble());
}
