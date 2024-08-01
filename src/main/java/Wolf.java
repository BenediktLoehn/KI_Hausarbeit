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
        if (position.distance(preyPosition) < terrain.getGlobalMaxPosition().distance(preyPosition))
            terrain.setGlobalMaxPosition(position);
    }

    public boolean preyCaught() {
        return position.distance(prey.getPosition()) < radius + prey.getRadius();
    }



    public Vector2D beginMove() {
        w = 1; //Geiwchtung der eigenen Richtung
        pI = 0.0; // Gewichtung hin zum eigenen Bestwert
        pG = 0.3; // Gewichtung hin zum Globalen Maximum
        pPrey = 1.25; //Gewichtung zur Position der Beute
        delta_v = 0.01;  // Skalierung der Schrittweite


        //random values for weight of own max, global max and prey position
        double r1 = rand.nextDouble(), r2 = rand.nextDouble(), r3 = rand.nextDouble();

        //Attraction for own max (always the current position)
        Vector2D attractionLocalMax = getSelfMaxPosition().sub(position).scale(pI * r1).scale(delta_v);

        //Attraction for global max
        Vector2D attractionGlobalMax = terrain.getGlobalMaxPosition().sub(position).scale(pG * r2).scale(delta_v);

        Vector2D attractionPrey = prey.getPosition().sub(position).scale(pPrey * r3).normalize();

        //New direction
        Vector2D newDirection = direction.scale(w);
        newDirection = newDirection.add(attractionLocalMax);
        newDirection = newDirection.add(attractionGlobalMax);
        newDirection = newDirection.add(attractionPrey);
        return newDirection;
    }
}

