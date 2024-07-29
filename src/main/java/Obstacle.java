import java.util.Random;

public class Obstacle {

    private final Random rand = new Random();

    private Vector2D x;
    private Vector2D y;

    private double collisionRadius = 5.0;

    private Vector2D position;


    public Obstacle(Vector2D x, Vector2D y) {
        this.x = x;
        this.y = y;
    }

    public Obstacle(Terrain terrain) {
        double x1 = rand.nextDouble() * terrain.getWidth();
        double y1 = rand.nextDouble() * terrain.getLength();

        this.x = new Vector2D(x1, y1);

        double angle = rand.nextDouble() * 2 * Math.PI; // Random angle in radians
        double length = rand.nextDouble() * 200; // Random length up to 20

        double x2 = x1 + length * Math.cos(angle);
        double y2 = y1 + length * Math.sin(angle);

        this.y = new Vector2D(x2, y2);
    }

    public Vector2D getX() {
        return x;
    }

    public void setX(Vector2D x) {
        this.x = x;
    }

    public Vector2D getY() {
        return y;
    }

    public void setY1(Vector2D y) {
        this.y = y;
    }

    public double getCollisionRadius() {
        return collisionRadius;
    }

    public void setCollisionRadius(double radius) {
        this.collisionRadius = radius;
    }
}
