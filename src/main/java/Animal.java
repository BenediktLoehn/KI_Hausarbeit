import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Random;

@Getter
@Setter
public class Animal extends Thread{
    //constants
    private final double CHI = 0.729843788;
    private final double c = 2.05;

    private final Random rand = new Random();

    protected Vector2D a;
    protected Vector2D v;
    protected Vector2D position;

    private final double startingX;
    private final double startingY;

    protected double pI; //personal attraction
    protected double pG; //neighbourhood attraction
    protected double delta_v; //to scale down steps
    protected double radius = 3.0; //for collision checks with obstacles

    private Vector2D e1 = new Vector2D(rand.nextDouble(), rand.nextDouble());
    private Vector2D e2 = new Vector2D(rand.nextDouble(), rand.nextDouble());
    private Vector2D selfMaxPosition;


    public Animal() {
        position = new Vector2D(1000, 1000);
        v = new Vector2D(1000, 1000);
        selfMaxPosition = position;

        this.startingX = rand.nextDouble(1000);
        this.startingY = rand.nextDouble(1000);

        //calculate acceleration with the following formula: a = CHI * [c*e1 * (pG -x) + c*e2 * (pI - x)] - (1-x) * v1
        Vector2D term1 = position.scale(-1).add(new Vector2D(pG, pG)).elementWiseMul(e1).scale(c)
                .add(position.scale(-1).add(new Vector2D(pI, pI)).elementWiseMul(e2).scale(c));
        this.a = term1.scale(CHI).sub(v.scale(1 - CHI));
    }

    //check collisions with obstacles
    protected boolean isColliding(Vector2D newPosition, List<Vector2D> obstacles, double obstacleRadius) {
        for(Vector2D obstacle : obstacles) {
            if(newPosition.distance(obstacle) < this.radius + obstacleRadius) {
                return true;
            }
        }
        return false;
    }

    public void move() {

    }
    public void run() {
        while(!isInterrupted()) {
            move();
        }
    }
}
