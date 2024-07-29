import java.util.List;
import java.util.Random;

public class Animal extends Thread{
    protected final Random rand = new Random();
    protected int id;

    private final double startingX;
    private final double startingY;
    protected final Terrain terrain;
    protected Vector2D a;
    protected Vector2D v;
    protected Vector2D position;
    protected Vector2D direction;




    protected double  w; //weight of own direction
    protected double pI; //personal attraction
    protected double pG; //neighbourhood attraction
    protected double delta_v; //to scale down steps
    protected double radius = 5.0; //for collision checks with obstacles

    private Vector2D selfMaxPosition;




    public Animal(Terrain terrain, int id) {
        this.terrain = terrain;
        this.id = id;
        this.startingX = rand.nextDouble(terrain.getLength());
        this.startingY = rand.nextDouble(terrain.getWidth());

        position = new Vector2D(startingX, startingY);

        direction = new Vector2D(rand.nextDouble(-1, 1), rand.nextDouble(-1, 1));
        this.selfMaxPosition = position;


    }

    //check collisions with obstacles
    /*protected boolean isColliding(Vector2D newPosition, List<Obstacle> obstacles, double obstacleRadius) {
        for(Obstacle obstacle : obstacles) {
            if(newPosition.distance(obstacle) < this.radius + obstacleRadius) {
                return true;
            }
        }
        return false;
    }*/

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

    public Vector2D getSelfMaxPosition() {
        return selfMaxPosition;
    }

    public void setSelfMaxPosition(Vector2D selfMaxPosition) {
        this.selfMaxPosition = selfMaxPosition;
    }

    public Vector2D getDirection() {
        return direction;
    }

    public void setDirection(Vector2D direction) {
        this.direction = direction;
    }

    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }
}
