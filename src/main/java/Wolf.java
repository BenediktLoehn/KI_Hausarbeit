public class Wolf extends Animal {

    private double pPrey;

    private final Prey prey;

    public Wolf(Terrain terrain, int id) {
        super(terrain, id);
        this.prey = terrain.getPrey();
        terrain.getWolfs().add(this);
    }

    @Override
    public void move() {

        w = 1; //Geiwchtung der eigenen Richtung
        pI = 0.25; // Gewichtung hin zum eigenen Bestwert
        pG = 0.25; // Gewichtung hin zum Globalen Maximum
        pPrey = 1.25; //Gewichtung zur Position der Beute
        delta_v = 0.05;  // Skalierung der Schrittweite


        //random values for weight of own max, global max and prey position
        double r1 = rand.nextDouble(), r2 = rand.nextDouble(), r3 = rand.nextDouble();

        //Attraction for own max (always the current position)
        Vector2D attractionLocalMax = getSelfMaxPosition().sub(position).scale(pI * r1);

        //Attraction for global max
        Vector2D attractionGlobalMax = terrain.getGlobalMaxPosition().sub(position).scale(pG * r2);

        Vector2D attractionPrey = prey.getPosition().sub(position).scale(pPrey * r3);

        //New direction
        direction = direction.scale(w);
        direction = direction.add(attractionLocalMax);
        direction = direction.add(attractionGlobalMax);
        direction = direction.add(attractionPrey);
        direction = direction.scale(delta_v);

        // Calculate repulsive force from obstacles
        Vector2D repulsiveForce = new Vector2D(0.0, 0.0);
        for (Obstacle obstacle : terrain.getObstacles()) {
            Vector2D closestPoint = position.closestPointOnLineSegment(obstacle.getX(), obstacle.getY());
            double distanceToObstacle = position.distance(closestPoint);

            if (distanceToObstacle < radius + obstacle.getCollisionRadius()) {
                Vector2D startToObstacle = obstacle.getX().sub(position);
                Vector2D endToObstacle = obstacle.getY().sub(position);
                Vector2D closestEndPoint = startToObstacle.magnitude() < endToObstacle.magnitude() ? obstacle.getX() : obstacle.getY();

                Vector2D awayFromObstacle = closestEndPoint.sub(position).scale(-1).scale(100 / (distanceToObstacle * distanceToObstacle));
                repulsiveForce = repulsiveForce.add(awayFromObstacle);
            }
        }

        direction = direction.add(repulsiveForce);
        // Introduce small random perturbation
        Vector2D perturbation = new Vector2D(rand.nextDouble(-0.1, 0.1), rand.nextDouble(-0.1, 0.1));
        direction = direction.add(perturbation);

        System.err.printf("Calculated direction of Wolf %d: " + direction + "\n", id);

        //New Position
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
        if(!collisionDetected) {
            position = newPosition;
        } else {
            System.err.printf("Collision detected for Wolf %d, position not updated\n", id);
        }

        System.err.printf("new position of Wolf %d: " + position + "\n", id);

        if(position.getX() > terrain.getWidth() || position.getY() > terrain.getLength()) {
            position.setX(rand.nextDouble(terrain.getWidth()));
            position.setY(rand.nextDouble(terrain.getLength()));
        } else if (position.getX() <0 || position.getY() < 0) {
            position.setX(rand.nextDouble(terrain.getWidth()));
            position.setY(rand.nextDouble(terrain.getLength()));
        }
        update();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update() {
        Vector2D preyPosition = prey.getPosition();
        if(position.distance(preyPosition) < terrain.getGlobalMaxPosition().distance(preyPosition)) terrain.setGlobalMaxPosition(position);
    }

    public boolean preyCaught() {
        return position.equals(prey.getPosition());
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            move();
        }
    }
}
