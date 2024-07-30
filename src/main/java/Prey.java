public class Prey extends Animal {

    public Prey(Terrain terrain, int id) {
        super(terrain, id);
        terrain.setPrey(this);
    }

    @Override
    public void update() {

    }

    @Override
    public Vector2D beginMove() {
        double w = 3; // Weighting of the own direction
        double pWolf = -3; // Weighting away from wolves
        double delta_v = 0.3;  // Scaling of step size

        double r1 = rand.nextDouble();

        // Repulsion from nearest wolf
        Wolf bestWolf = terrain.getWolfs().getFirst();
        for (Wolf wolf : terrain.getWolfs()) {
            if (wolf.getPosition().distance(position) < bestWolf.getPosition().distance(position)) {
                bestWolf = wolf;
            }
        }
        Vector2D repulsionFromWolf = bestWolf.getPosition().sub(position).normalize().scale(pWolf * r1 / position.distance(bestWolf.getPosition()));


        // New direction
        Vector2D newDirection = direction.scale(w);
        newDirection = newDirection.add(repulsionFromWolf);
        return newDirection;
    }


    @Override
    public void run() {
        while (!isInterrupted()) {
            move();
        }
    }
}
