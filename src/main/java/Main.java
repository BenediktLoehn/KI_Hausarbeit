import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static void createAndShowGUI() {
        List<Wolf> wolfs = new ArrayList<>();
        Prey prey = new Prey();
        Terrain terrain = new Terrain(1000.0, 1000.0, prey, wolfs);

        TerrainPanel terrainPanel = new TerrainPanel(terrain, prey, wolfs);

        for (int i = 0; i < 20; i++) {
            wolfs.add(new Wolf(prey));
        }

        JFrame frame = new JFrame("Wolfs and Prey on Terrain");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(terrainPanel);
        frame.setSize((int) terrain.getWidth(), (int) terrain.getLength()); // Größe des Frames entsprechend dem Terrain
        frame.setVisible(true);

        for (Wolf wolf : wolfs) {
            wolf.start();
        }
        prey.start();

        Vector2D oldPosition = terrain.getGlobalMaxPosition();
        Vector2D currentPosition;
        while (!prey.isInterrupted()) {
            for(Wolf wolf : wolfs) {
                if(wolf.preyCaught()) break;
            }
            terrainPanel.repaint();
            currentPosition = terrain.getGlobalMaxPosition();
            if (oldPosition != currentPosition) {
                oldPosition = currentPosition;
            }
            try {
                Thread.sleep(100); // Wartezeit zwischen den Aktualisierungen
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
