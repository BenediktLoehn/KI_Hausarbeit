import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static void createAndShowGUI() {
        Terrain terrain = new Terrain(1000.0, 1000.0);
        List<Wolf> wolfs = new ArrayList<>();
        terrain.setWolfs(wolfs);
        Prey prey = new Prey(terrain, 1);
        terrain.setPrey(prey);
        TerrainPanel terrainPanel = new TerrainPanel(terrain, prey, wolfs);

        for (int i = 0; i < 10; i++) {
            Wolf wolf = new Wolf(terrain, i);
            if(terrain.getGlobalMaxPosition() == null || wolf.getPosition().distance(prey.getPosition()) < terrain.getGlobalMaxPosition().distance(prey.getPosition())) {
                terrain.setGlobalMaxPosition(wolf.getPosition());
            }
        }

        JFrame frame = new JFrame("Wolfs and Prey on Terrain");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(terrainPanel);
        frame.setSize((int) terrain.getWidth(), (int) terrain.getLength()); // Größe des Frames entsprechend dem Terrain
        frame.setVisible(true);

        long startTime = System.currentTimeMillis();

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
            currentPosition = terrain.getGlobalMaxPosition();
            if (oldPosition.equals(currentPosition)) {
                oldPosition = currentPosition;
            }
            try {
                Thread.sleep(20); // Wartezeit zwischen den Aktualisierungen
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        wolfs.forEach(Wolf::interrupt);

        long endTime = System.currentTimeMillis();
        float duration = endTime - startTime;
        System.err.printf("Prey was caught in %.2f seconds", duration/1000);
    }

    public static void main(String[] args) {
        createAndShowGUI();
    }
}
