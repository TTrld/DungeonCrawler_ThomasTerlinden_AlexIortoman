import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

public class GameEngine implements Engine, KeyListener, MouseListener {
    private DynamicSprite hero; // The hero controlled by the player
    private ArrayList<Sprite> environment; // The game environment (obstacles, etc.)
    private ArrayList<Projectile> projectiles = new ArrayList<>(); // List of active projectiles
    private ArrayList<Enemy> enemies = new ArrayList<>(); // List of enemies in the game
    private int baseSpeed = 2;
    private int boostSpeed = 5;
    private Image projectileImage; // The image for projectiles
    private RenderEngine renderEngine;




    public GameEngine(DynamicSprite hero, ArrayList<Sprite> environment, RenderEngine renderEngine) {
        this.hero = hero;
        this.environment = environment;
        this.renderEngine = renderEngine;


        // Load the projectile image
        try {
            projectileImage = ImageIO.read(new File("./img/Potion_purple_64_004.png"));
            if (projectileImage == null) {
                System.err.println("Error: Projectile image is null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Could not load projectile image.");
        }
    }

    @Override
    public void update() {
        // Update the hero's movement
        hero.moveIfPossible(environment);

        // Update projectiles
        ArrayList<Projectile> toRemove = new ArrayList<>();
        for (Projectile projectile : projectiles) {
            projectile.move();
            if (projectile.isOutOfBounds(400, 600)) { // Screen bounds
                toRemove.add(projectile);
            }
        }
        projectiles.removeAll(toRemove);

        // Check collisions between projectiles and enemies
        toRemove.clear(); // Reuse the list for projectiles to remove
        for (Projectile projectile : projectiles) {
            for (Enemy enemy : enemies) {
                if (enemy.getHitBox().intersects(projectile.getHitBox())) {
                    enemy.takeDamage(25); // Inflict damage
                    toRemove.add(projectile); // Mark the projectile for removal
                }
            }
        }
        projectiles.removeAll(toRemove);

        // Remove dead enemies
        enemies.removeIf(enemy -> enemy.getCurrentHealth() <= 0);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> hero.setDirection(Direction.NORTH);
            case KeyEvent.VK_S -> hero.setDirection(Direction.SOUTH);
            case KeyEvent.VK_A -> hero.setDirection(Direction.WEST);
            case KeyEvent.VK_D -> hero.setDirection(Direction.EAST);
            case KeyEvent.VK_CONTROL -> hero.setSpeed(boostSpeed); // Boost speed
            case KeyEvent.VK_H -> hero.takeDamage(10); // Inflict self-damage for testing
            case KeyEvent.VK_SPACE -> launchProjectile(); // Launch projectile with spacebar

        }
    }

    private void launchProjectile() {
        projectiles.add(new Projectile(
                hero.getX()/4 + hero.getWidth() / 2,
                hero.getY() + hero.getHeight() / 2,
                20, 20, // Dimensions of the projectile
                projectileImage,
                hero.getX() + hero.getWidth() * 2,
                hero.getY()
        ));
    }


    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            hero.setSpeed(baseSpeed); // Reset speed
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX() + renderEngine.getXOffset(); // Adjust X for camera
        int mouseY = e.getY(); // Adjust Y if needed for the camera

        // Log positions for debugging
        System.out.println("Hero Position: (" + hero.getX() + ", " + hero.getY() + ")");
        System.out.println("Mouse Click Position (Adjusted): (" + mouseX + ", " + mouseY + ")");

        // Create and launch the projectile
        projectiles.add(new Projectile(
                hero.getWidth() / 2 + 75, // Start from the hero's center X
                hero.getY() + hero.getHeight() / 2, // Start from the hero's center Y
                20, 20, // Projectile dimensions
                projectileImage, // Projectile image
                mouseX, // Target X position (adjusted for camera)
                mouseY  // Target Y position (adjusted for camera)
        ));
    }




    @Override
    public void mousePressed(MouseEvent e) {
        // Not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Not used
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public DynamicSprite getHero() {
        return hero;
    }
}
