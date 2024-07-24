public class Prey extends Animal{

    private double pWolfGlobalMax;
    public Prey() {
        super();
    }

    @Override
    public void move() {
        pWolfGlobalMax = 3.0; //Gewichtung zur Position des "besten" Wolfs
        delta_v = 0.25;  // Skalierung der Schrittweite
    }
}
