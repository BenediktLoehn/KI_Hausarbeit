import java.util.Vector;

public class Wolf extends Animal {

    private double pPrey;

    private double fitness;
    private Prey prey;

    public Wolf(Terrain terrain) {
        super(terrain);
        this.prey = terrain.getPrey();
        terrain.getWolfs().add(this);
        this.fitness = getPosition().distance(prey.getPosition());
    }

    @Override
    public void move() {
        pI = 0.1; // Gewichtung hin zum eigenen Bestwert
        pG = 0.3; // Gewichtung hin zum Globalen Maximum
        pPrey = 0.7; //Gewichtung zur Position der Beute
        delta_v = 0.05;  // Skalierung der Schrittweite


        //scale down acceleration for smaller steps
        Vector2D acc = calculateAcceleration();


        System.err.println("Calculated acceleration: " + acc);
        acc.scale(delta_v);
        System.err.println("Scaled acceleration " + acc);
        setA(acc);

        setV(v.add(a));
        // Scale the velocity before updating the position
        Vector2D scaledVelocity = v.scale(delta_v);
        // Update the position by adding the scaled velocity
        setPosition(position.add(scaledVelocity));
        // Debug output
        System.err.println("New Position: " + getPosition());
        System.err.println("New Velocity: " + v);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //calculate acceleration with the following formula: a = CHI * [c*e1 * (pG -x) + c*e2 * (pI - x)] - (1-x) * v1
    public Vector2D calculateAcceleration() {
        double positionMagnitude = position.magnitude();
        //Calculate term1 : c * e1 * (pG - magnitude(position))
        Vector2D term1 = e1.scale(c * (pG - positionMagnitude));

        // Calculate term2: c * e2 * (pI - magnitude(position))
        Vector2D term2 = e2.scale(c * (pI - positionMagnitude));

        // Sum term1 and term2, then scale by CHI
        Vector2D combinedTerm = term1.add(term2).scale(CHI);
        // Subtract the velocity term scaled by (1 - CHI)
        Vector2D velocityTerm = v.scale(1 - positionMagnitude);
        // Calculate the final acceleration
        Vector2D acceleration = combinedTerm.sub(velocityTerm);
        return acceleration;
    }

    public boolean preyCaught() {
        return this.getPosition().equals(prey.getPosition());
    }
}
