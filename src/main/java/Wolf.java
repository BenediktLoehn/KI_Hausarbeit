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
        w = 1.0; //Gewichtung der eigenen Richtung
        pG = 0.5; // Gewichtung hin zum Globalen Maximum
        pPrey = 1.5; //Gewichtung zur Position der Beute
        delta_v = 0.01;  // Skalierung der Schrittweite


        //random values for weight of global max and prey position
        double r1 = rand.nextDouble(), r2 = rand.nextDouble();

        //Attraction for global max
        Vector2D attractionGlobalMax = terrain.getGlobalMaxPosition().sub(position).scale(pG * r1).scale(delta_v);

        Vector2D attractionPrey = prey.getPosition().sub(position).scale(pPrey * r2).normalize();

        //New direction
        Vector2D newDirection = direction.scale(w);
        newDirection = newDirection.add(attractionGlobalMax);
        newDirection = newDirection.add(attractionPrey);
        return newDirection;
    }
}

