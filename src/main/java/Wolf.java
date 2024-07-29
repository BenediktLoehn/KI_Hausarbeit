public class Wolf extends Animal {

    private double pPrey;

    private final Prey prey;

    public Wolf(Terrain terrain, int id) {
        super(terrain, id);
        this.prey = terrain.getPrey();
        terrain.getWolfs().add(this);
    }

    public void update() {
        Vector2D preyPosition = prey.getPosition();
        if(position.distance(preyPosition) < terrain.getGlobalMaxPosition().distance(preyPosition)) terrain.setGlobalMaxPosition(position);
    }
    public boolean preyCaught() {
        return position.equals(prey.getPosition());
    }

    @Override
    public void move() {

        w = 1; //Geiwchtung der eigenen Richtung
        pI = 0.25; // Gewichtung hin zum eigenen Bestwert
        pG = 0.25; // Gewichtung hin zum Globalen Maximum
        pPrey = 1.25; //Gewichtung zur Position der Beute
        delta_v = 0.01;  // Skalierung der Schrittweite


        //random values for weight of own max, global max and prey position
        double r1 = rand.nextDouble(), r2 = rand.nextDouble(), r3 = rand.nextDouble();

        //Attraction for own max (always the current position)
        Vector2D attractionLocalMax = getSelfMaxPosition().sub(position).scale(pI * r1);

        //Attraction for global max
        Vector2D attractionGlobalMax = terrain.getGlobalMaxPosition().sub(position).scale(pG * r2);

        Vector2D attractionPrey = prey.getPosition().sub(position).scale(pPrey * r3);

        //New direction
        Vector2D newDirection;
        newDirection = direction.scale(w);
        newDirection = newDirection.add(attractionLocalMax);
        newDirection = newDirection.add(attractionGlobalMax);
        newDirection = newDirection.add(attractionPrey);
        newDirection = newDirection.scale(delta_v);

        // Introduce small random perturbation
        Vector2D perturbation = new Vector2D(rand.nextDouble(-1, 1), rand.nextDouble(-1, 1));
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

        // Handle collision
        if (collisionDetected) {
            direction = findAlternativeDirection();
            newPosition = position.add(direction);
            position = newPosition;
        } else {
            direction = newDirection;
            position = newPosition;
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
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Vector2D findAlternativeDirection() {
        double angleIncrement = Math.PI / 8; // Increment angle by 22.5 degrees
        for (int i = 1; i <= 8; i++) { // Check 8 alternative directions
            double angle = angleIncrement * i;
            Vector2D alternativeDirection = new Vector2D(
                    direction.getX() * Math.cos(angle) - direction.getY() * Math.sin(angle),
                    direction.getX() * Math.sin(angle) + direction.getY() * Math.cos(angle)
            ).normalize().scale(delta_v);

            Vector2D newPosition = position.add(alternativeDirection);
            boolean collisionDetected = false;
            for (Obstacle obstacle : terrain.getObstacles()) {
                Vector2D closestPoint = newPosition.closestPointOnLineSegment(obstacle.getX(), obstacle.getY());
                if (newPosition.distance(closestPoint) < radius + obstacle.getCollisionRadius()) {
                    collisionDetected = true;
                    break;
                }
            }

            if (!collisionDetected) {
                return alternativeDirection;
            }
        }
        return direction.scale(-1); // If no alternative direction is found, reverse direction
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            move();
        }
    }
}
