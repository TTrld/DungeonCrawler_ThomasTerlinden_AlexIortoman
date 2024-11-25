import java.awt.*;
import java.awt.geom.Point2D;

public class Projectile extends DynamicSprite {
    private double targetX, targetY; // Target position
    private double speed = 5; // Speed of the projectile
    private double directionX, directionY; // Direction of movement
    private double angle; // Angle for spinning
    private double rotationSpeed = 10; // Speed of rotation

    public Projectile(double x, double y, double width, double height, Image image, double targetX, double targetY) {
        super(x, y, width, height, image);
        this.targetX = targetX;
        this.targetY = targetY;

        // Calculate direction
        double deltaX = targetX - x;
        double deltaY = targetY - y;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        this.directionX = deltaX / distance;
        this.directionY = deltaY / distance;
    }

    @Override
    public void move() {
        x += directionX * speed;
        y += directionY * speed;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.rotate(Math.toRadians(angle), x + width / 2, y + height / 2); // Rotate around the center
        g2d.drawImage(image, (int) x, (int) y, (int) width, (int) height, null);
        g2d.dispose();

        angle += rotationSpeed; // Update rotation
    }

    public boolean isOutOfBounds(int screenWidth, int screenHeight) {
        return x < 0 || x > screenWidth || y < 0 || y > screenHeight;
    }
}
