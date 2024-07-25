import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class TerrainPanel extends JPanel {
    private Terrain terrain;
    private Prey prey;
    private List<Wolf> wolfs;


    BufferedImage terrainBackground;

    public TerrainPanel(Terrain terrain, Prey prey, List<Wolf> wolfs) {
        this.terrain = terrain;
        this.prey = prey;
        this.wolfs = wolfs;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // paint terrain with obstacles

        if (terrainBackground != null) {
            g.drawImage(terrainBackground, 0, 0, this);
        } else {
            int terrainWidth = (int) terrain.getWidth();
            int terrainHeight = (int) terrain.getLength();
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(0, 0, terrainWidth, terrainHeight);
        }

        for(Obstacle obstacle : terrain.getObstacles()) {
            g2d.setColor(Color.LIGHT_GRAY);
            int x1 = (int) obstacle.getX().getX();
            int y1 = (int) obstacle.getX().getY();
            int x2 = (int) obstacle.getY().getX();
            int y2 = (int) obstacle.getY().getY();

            g.drawLine(x1, y1, x2, y2);
        }

        // paint wolfs
        g2d.setColor(Color.RED);
        for (Wolf wolf : wolfs) {
            Vector2D wolfPosition = wolf.getPosition();
            int x = (int) wolfPosition.getX();
            int y = (int) wolfPosition.getY();
            g2d.fillOval(x, y, 10, 10);
        }

        //paint prey
        g2d.setColor(Color.BLUE);
        Vector2D preyPosition = prey.getPosition();
        int x = (int) preyPosition.getX();
        int y = (int) preyPosition.getY();
        g2d.fillOval(x, y, 10, 10);
    }
}
