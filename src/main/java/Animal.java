import java.util.List;
import java.util.Random;

public class Animal extends Thread{
    //constants
    protected final double CHI = 0.729843788;
    protected final double c = 2.05;

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

    protected Vector2D e1 = new Vector2D(rand.nextDouble(), rand.nextDouble());
    protected Vector2D e2 = new Vector2D(rand.nextDouble(), rand.nextDouble());
    private Vector2D selfMaxPosition;

    private final Terrain terrain;


    public Animal(Terrain terrain) {
        this.terrain = terrain;
        this.position = new Vector2D(1000, 1000);
        this.v = new Vector2D(1000, 1000);
        this.selfMaxPosition = position;

        this.startingX = position.getX();
        this.startingY = position.getY();

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

    public Vector2D getA() {
        return a;
    }

    public void setA(Vector2D a) {
        this.a = a;
    }

    public Vector2D getV() {
        return v;
    }

    public void setV(Vector2D v) {
        this.v = v;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public double getStartingX() {
        return startingX;
    }

    public double getStartingY() {
        return startingY;
    }

    public double getpI() {
        return pI;
    }

    public void setpI(double pI) {
        this.pI = pI;
    }

    public double getpG() {
        return pG;
    }

    public void setpG(double pG) {
        this.pG = pG;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getDelta_v() {
        return delta_v;
    }

    public void setDelta_v(double delta_v) {
        this.delta_v = delta_v;
    }

    public Vector2D getE1() {
        return e1;
    }

    public void setE1(Vector2D e1) {
        this.e1 = e1;
    }

    public Vector2D getE2() {
        return e2;
    }

    public void setE2(Vector2D e2) {
        this.e2 = e2;
    }

    public Vector2D getSelfMaxPosition() {
        return selfMaxPosition;
    }

    public void setSelfMaxPosition(Vector2D selfMaxPosition) {
        this.selfMaxPosition = selfMaxPosition;
    }
}
