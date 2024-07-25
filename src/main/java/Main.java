import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static void createAndShowGUI() {
        List<Wolf> wolfs = new ArrayList<>();
        Terrain terrain = new Terrain(1000.0, 1000.0);
        Prey prey = new Prey(terrain);


        TerrainPanel terrainPanel = new TerrainPanel(terrain, prey, wolfs);

        for (int i = 0; i < 1; i++) {
            wolfs.add(new Wolf(terrain));
        }

        JFrame frame = new JFrame("Wolfs and Prey on Terrain");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(terrainPanel);
        frame.setSize((int) terrain.getWidth(), (int) terrain.getLength()); // Größe des Frames entsprechend dem Terrain
        frame.setVisible(true);

        prey.start();
        for (Wolf wolf : wolfs) {
            wolf.start();
        }


        Vector2D oldPosition = terrain.getGlobalMaxPosition();
        Vector2D currentPosition;
        while (!prey.isInterrupted()) {
            terrainPanel.repaint();
            for(Wolf wolf : wolfs) {
                if(wolf.preyCaught()) prey.interrupt();
            }
            terrainPanel.repaint();
            currentPosition = terrain.getGlobalMaxPosition();
            if (oldPosition != currentPosition) {
                oldPosition = currentPosition;
            }
            try {
                Thread.sleep(1000); // Wartezeit zwischen den Aktualisierungen
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        wolfs.forEach(Wolf::interrupt);

        System.err.printf("Prey was caught");
    }


    public static void main(String[] args) {
        createAndShowGUI();
    }
}
