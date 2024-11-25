import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {
    private ArrayList<Displayable> renderList;
    private int xOffset = 0;
    private DynamicSprite hero;// Reference to the hero for camera tracking
    private GameEngine gameEngine;



    public RenderEngine(JFrame displayZoneFrame, DynamicSprite hero) {
        renderList = new ArrayList<>();
        this.hero = hero;
    }

    public RenderEngine(JFrame displayZoneFrame, DynamicSprite hero, GameEngine gameEngine) {
        renderList = new ArrayList<>();
        this.hero = hero;
        this.gameEngine = gameEngine;
    }

    public void setRenderList(ArrayList<Displayable> renderList) {
        this.renderList = renderList;
    }

    public void addToRenderList(Displayable displayable) {
        renderList.add(displayable);
    }

    // Add a list of displayables to renderList
    public void addToRenderList(ArrayList<? extends Displayable> displayables) {
        renderList.addAll(displayables);
    }

    @Override
    public void update() {
        int screenWidth = getWidth();
        xOffset = (int) ((hero.getX()+100) - screenWidth / 2);
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Check if renderList is null
        if (renderList != null) {
            for (Displayable displayable : renderList) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.translate(-xOffset, 0); // Shift by xOffset
                displayable.draw(g2d);
                g2d.dispose();
            }
        } else {
            System.err.println("Warning: renderList is null in RenderEngine");
        }

        // Check if gameEngine or projectiles are null
        if (gameEngine != null && gameEngine.getProjectiles() != null) {
            for (Projectile projectile : gameEngine.getProjectiles()) {
                projectile.draw(g);
            }
        } else {
            System.err.println("Warning: gameEngine or projectiles are null");
        }
    }

    public int getXOffset() {
        return xOffset;
    }

}
