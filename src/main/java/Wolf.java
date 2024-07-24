public class Wolf extends Animal{

    private double pPrey;

    private double fitness;
    private Prey prey;
    public Wolf(Prey prey) {
        super();
        this.prey = prey;
        this.fitness = getPosition().distance(prey.getPosition());
    }

    @Override
    public void move() {
        pI = 0.5 ; // Gewichtung hin zum eigenen Bestwert
        pG = 1.6 ; // Gewichtung hin zum Globalen Maximum
        pPrey = 3.0; //Gewichtung zur Position der Beute
        delta_v = 0.2;  // Skalierung der Schrittweite

        setV(v.add(a));
        setPosition(position.add(v));
    }

    public boolean preyCaught() {
        return this.getPosition().equals(prey.getPosition());
    }
}
