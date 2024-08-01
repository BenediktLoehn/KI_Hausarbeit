import java.util.Random;

public class Obstacle {

    private final Random rand = new Random();

    private final Vector2D x;
    private final Vector2D y;

    private final double collisionRadius = 3.0;


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

    public Vector2D getY() {
        return y;
    }

    public double getCollisionRadius() {
        return collisionRadius;
    }

    public Vector2D calculateDirection() {
        return new Vector2D(y.getX() - x.getX(), y.getY() - x.getY());
    }
}
