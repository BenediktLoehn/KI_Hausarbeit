import java.util.Random;

public abstract class Animal extends Thread{
    protected final Random rand = new Random();
    protected int id;

    private final double startingX;
    private final double startingY;
    protected final Terrain terrain;
    protected Vector2D position;
    protected Vector2D direction;




    protected double  w; //weight of own direction
    protected double pI; //personal attraction
    protected double pG; //neighbourhood attraction
    protected double delta_v; //to scale down steps
    protected double radius = 3.0; //for collision checks with obstacles

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

    protected Vector2D handleObstacleCollision(Vector2D newDirection) {
        Obstacle collidingObstacle = null;
        double minimalDistance = Double.MAX_VALUE;
        for (Obstacle obstacle : terrain.getObstacles()) {
            Vector2D closestPoint = position.closestPointOnLineSegment(obstacle.getX(), obstacle.getY());
            double currentDistance = position.distance(closestPoint);
            if (currentDistance < radius + obstacle.getCollisionRadius()) {
                if (collidingObstacle == null || currentDistance < minimalDistance) {
                    minimalDistance = currentDistance;
                    collidingObstacle = obstacle;
                }
            }
        }

        if (collidingObstacle != null) {
            // Calculate sliding direction along the obstacle
            Vector2D obstacleDirection = collidingObstacle.calculateDirection();
            double dotProduct = newDirection.dot(obstacleDirection);
            newDirection = obstacleDirection.scale(dotProduct).normalize().scale(newDirection.magnitude());
        }
        return newDirection;
    }

    private Vector2D handleBoundaryCollision(Vector2D newDirection) {
        double newX = position.getX() + newDirection.getX();
        double newY = position.getY() + newDirection.getY();

        if (newX < 0 || newX > terrain.getWidth()) {
            newDirection = new Vector2D(-newDirection.getX(), newDirection.getY());
        }
        if (newY < 0 || newY > terrain.getLength()) {
            newDirection = new Vector2D(newDirection.getX(), -newDirection.getY());
        }
        return newDirection;
    }
    protected void move() {

        Vector2D newDirection = beginMove();

        // Introduce small random perturbation
        Vector2D perturbation = new Vector2D(rand.nextDouble(-1, 1), rand.nextDouble(-1, 1));
        newDirection = newDirection.add(perturbation).normalize(); // Changed from 'direction' to 'newDirection' and normalized

        System.err.printf("Calculated direction of %s (%d): %s \n", this.getClass().getName(), id, newDirection.toString());

        // Handle collision with obstacles and boundaries
        newDirection = handleObstacleCollision(newDirection);
        newDirection = handleBoundaryCollision(newDirection);

        Vector2D newPosition = position.add(newDirection);
        if (newPosition.getX() >= 0 && newPosition.getX() <= terrain.getWidth() &&
                newPosition.getY() >= 0 && newPosition.getY() <= terrain.getLength()) {
            setPosition(newPosition); // Set new position only if within terrain bounds
        }
        setDirection(newDirection.normalize());
        System.err.printf("new position of %s (%d): %s \n", this.getClass().getName(),  id, position.toString());

        update();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public abstract void update();

    public abstract Vector2D beginMove();

    public void run() {
        while(!isInterrupted()) {
            move();
        }
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public double getRadius() {
        return radius;
    }

    public Vector2D getSelfMaxPosition() {
        return selfMaxPosition;
    }

    public void setDirection(Vector2D direction) {
        this.direction = direction;
    }

}
