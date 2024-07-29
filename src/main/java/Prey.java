public class Prey extends Animal{

    public Prey(Terrain terrain, int id) {
        super(terrain, id);
        terrain.setPrey(this);
    }

    @Override
    public void move() {
        double w = 3; // Weighting of the own direction
        double pWolf = -3; // Weighting away from wolves
        double delta_v = 0.3;  // Scaling of step size

        double r1 = rand.nextDouble();

        // Repulsion from nearest wolf
        Wolf bestWolf = terrain.getWolfs().getFirst();
        for (Wolf wolf : terrain.getWolfs()) {
            if(wolf.getPosition().distance(position) < bestWolf.getPosition().distance(position)) {
                bestWolf = wolf;
            }
        }
            Vector2D repulsionFromWolf = bestWolf.getPosition().sub(position).scale(pWolf * r1 / position.distance(bestWolf.getPosition()));


        // New direction
        direction = direction.scale(w);
        direction = direction.add(repulsionFromWolf);
        direction = direction.scale(delta_v);

        // Introduce small random perturbation
        Vector2D perturbation = new Vector2D(rand.nextDouble(-0.1, 0.1), rand.nextDouble(-0.1, 0.1));
        direction = direction.add(perturbation);

        System.err.printf("Calculated direction of Prey %d: " + direction + "\n", id);

        // New Position
        Vector2D newPosition = position.add(direction);

        // Check immediate collision with obstacles
        boolean collisionDetected = false;
        for (Obstacle obstacle : terrain.getObstacles()) {
            Vector2D closestPoint = newPosition.closestPointOnLineSegment(obstacle.getX(), obstacle.getY());
            if (newPosition.distance(closestPoint) < radius + obstacle.getCollisionRadius()) {
                collisionDetected = true;
                break;
            }
        }
        if (!collisionDetected) {
            position = newPosition;
        } else {
            System.err.printf("Collision detected for Prey %d, position not updated\n", id);
        }

        System.err.printf("new position of Prey %d: " + position + "\n", id);

        // Check boundary conditions
        if (position.getX() > terrain.getWidth() || position.getY() > terrain.getLength()) {
            position.setX(rand.nextDouble(terrain.getWidth()));
            position.setY(rand.nextDouble(terrain.getLength()));
        } else if (position.getX() < 0 || position.getY() < 0) {
            position.setX(rand.nextDouble(terrain.getWidth()));
            position.setY(rand.nextDouble(terrain.getLength()));
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        while(!isInterrupted()) {
            move();
        }
    }
}
