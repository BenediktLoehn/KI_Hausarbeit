import java.util.Random;

public abstract class Animal extends Thread{
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

    public Vector2D reflect(Obstacle obstacle, Vector2D newDirection) {
        double angle = newDirection.calculateAngleToOtherVector(obstacle.calculateDirection());
        return newDirection.rotateVector(angle);
    }

    protected Vector2D handleCollision(Vector2D newDirection) {
        // Check immediate collision with obstacles
        Obstacle collidingObstacle = null;
        double minimalDistance = Double.MAX_VALUE;
        for (Obstacle obstacle : terrain.getObstacles()) {
            Vector2D closestPoint = position.closestPointOnLineSegment(obstacle.getX(), obstacle.getY());
            double currentDistance = position.distance(closestPoint);
            //TODO: drüber nachdenken
            if (currentDistance < radius + obstacle.getCollisionRadius()) {
                if(collidingObstacle == null || position.distance(closestPoint) < minimalDistance) {
                    minimalDistance = currentDistance;
                    collidingObstacle = obstacle;
                }
            }
        }

        // Handle collision
        if (collidingObstacle != null) {
            return reflect(collidingObstacle, newDirection);
        } else {
            return newDirection;
        }
    }
    protected void move() {

        Vector2D newDirection = beginMove();

        // Introduce small random perturbation
        Vector2D perturbation = new Vector2D(rand.nextDouble(-1, 1), rand.nextDouble(-1, 1));
        direction = direction.add(perturbation);

        System.err.printf("Calculated direction of %s (%d): %s \n", this.getClass().getName(), id, direction.toString());

        //New Position

        newDirection = handleCollision(newDirection);
        Vector2D theoreticalNewDirection = handleCollision(newDirection);
        Vector2D newPosition = position.add(newDirection);
        //new collision after handling the first one?
        if(newDirection.equals(theoreticalNewDirection) && newPosition.getX() < 1000 && newPosition.getY() < 1000) {
            setPosition(newPosition);
        }
        setDirection(newDirection);
        System.err.printf("new position of %s (%d): %s \n", this.getClass().getName(),  id, position.toString());

        update();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void update();

    public abstract Vector2D beginMove();

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
