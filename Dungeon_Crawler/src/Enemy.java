import java.awt.*;
import java.awt.image.ImageObserver;

public class Enemy extends DynamicSprite {
    private int frameIndexX = 0;  // Frame index for animation
    private int frameIndexY = 0;  // Row index for direction (0 for idle facing one direction)
    private int numberOfFrames = 4;  // Number of frames in the idle animation
    private long lastFrameTime;  // Time when the last frame was updated
    private int animationSpeed = 500;// Time between frames in milliseconds
    private int maxHealth = 100;  // Maximum health for the enemy
    private int currentHealth = 100;
    private boolean attacking = false;
    private boolean dead = false;

    private boolean movingRight = true;
    private double totalDistanceMoved = 0;
    private final double movementRange = 200;


    public Enemy(double x, double y, double width, double height, Image image) {
        super(x, y, width, height, image);
        this.lastFrameTime = System.currentTimeMillis();
    }

    @Override
    public void draw(Graphics g) {
        updateAnimation();  // Update the animation frame index
        updateIdleMovement();   // Update the place of the frame


        if (movingRight) {
            // Moving to the right, use the second row frames (row index 1)
            g.drawImage(image,
                    (int) (x + (width +15)), (int) y, (int) x, (int) (y + (height+15)),  // Flip horizontally
                    (int) (frameIndexX * (width +15)), (int) (1 * (height+15)),
                    (int) ((frameIndexX + 1) * (width +15)), (int) (2 * (height+15)),
                    (ImageObserver) null);
        } else {
            // Moving to the left, use the same frames but flip them horizontally
            g.drawImage(image,
                    (int) x, (int) y, (int) (x + (width +15)), (int) (y + (height+15)),
                    (int) (frameIndexX * (width +15)), (int) (1 * (height+15)),  // Second row for right walking
                    (int) ((frameIndexX + 1) * (width +15)), (int) (2 * (height+15)),
                    (ImageObserver) null);
        }
        drawHealthBar(g);
    }

    private void updateAnimation() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= animationSpeed) {
            frameIndexX = (frameIndexX + 1) % numberOfFrames;  // Loop through the frames
            lastFrameTime = currentTime;
        }
    }

    private void updateIdleMovement() {
        double movementSpeed = 0.5;  // Adjust the movement speed if needed

        if (movingRight) {
            x += movementSpeed;
            totalDistanceMoved += movementSpeed;

            if (totalDistanceMoved >= movementRange) {
                movingRight = false;
                totalDistanceMoved = 0;  // Reset distance moved
            }
        } else {
            x -= movementSpeed;
            totalDistanceMoved += movementSpeed;

            if (totalDistanceMoved >= movementRange) {
                movingRight = true;
                totalDistanceMoved = 0;  // Reset distance moved
            }
        }
    }

    private void drawHealthBar(Graphics g) {
        int barWidth = 50;  // Width of the health bar
        int barHeight = 5;  // Height of the health bar
        int barX = (int) (x + (width / 2) - (barWidth / 2));  // Center the bar horizontally above the enemy
        int barY = (int) y - 10;  // Position the bar slightly above the enemy



        int healthWidth = (int) ((double) currentHealth / maxHealth * barWidth);
        g.setColor(Color.RED);
        g.fillRect(barX, barY, healthWidth, barHeight);

        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, barWidth, barHeight);
    }

    public void takeDamage(int damage) {
        currentHealth = Math.max(0, currentHealth - damage);
    }

    public void heal(int amount) {
        currentHealth = Math.min(maxHealth, currentHealth + amount);
    }

    public int getCurrentHealth() {
        return currentHealth;
    }
}
