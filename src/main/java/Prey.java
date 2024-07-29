public class Prey extends Animal{

    private double pWolfGlobalMax;
    public Prey(Terrain terrain, int id) {
        super(terrain, id);
        terrain.setPrey(this);
    }

    @Override
    public void move() {
        pWolfGlobalMax = 3.0; //Gewichtung zur Position des "besten" Wolfs
        delta_v = 0.25;  // Skalierung der Schrittweite
    }


    @Override
    public void run() {
        while(!isInterrupted()) {
            move();
        }
    }
}
