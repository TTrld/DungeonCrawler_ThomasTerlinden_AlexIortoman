import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite {
    private boolean isWalking=true;
    private double speed = 2;
    private final int spriteSheetNumberOfColumn=10;
    private int timeBetweenFrame=200;
    private Direction direction=Direction.NORTH;
    private int maxHealth = 100;
    private int currentHealth = 100;

    public DynamicSprite(double x, double y, double width, double height, Image image) {
        super(x, y, width, height, image);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = Math.max(0, Math.min(currentHealth, maxHealth)); // Ensure health stays between 0 and maxHealth
    }

    public void takeDamage(int damage) {
        setCurrentHealth(currentHealth - damage);
    }

    public void heal(int amount) {
        setCurrentHealth(currentHealth + amount);
    }
    private boolean isMovingPossible(ArrayList<Sprite> environment) {
        Rectangle2D.Double hitBox =new Rectangle2D.Double();
        switch(direction) {
            case EAST:hitBox.setRect(super.getHitBox().getX()+speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST:hitBox.setRect(super.getHitBox().getX()-speed,super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH:hitBox.setRect(super.getHitBox().getX(),super.getHitBox().getY()-speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH:hitBox.setRect(super.getHitBox().getX(),super.getHitBox().getY()+speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;

        }
        for (Sprite s : environment){
            if ((s instanceof SolidSprite) && (s!=this)){
                if (((SolidSprite) s).intersect(hitBox)){
                    return false;
                }
            }
        }
        return true;
    }

    public void moveIfPossible(ArrayList<? extends Sprite> environment) {
        if (isMovingPossible((ArrayList<Sprite>) environment)) {
            move();
        }
    }


    public void move() {
        switch (direction) {
            case NORTH -> y -= speed;
            case SOUTH -> y += speed;
            case EAST -> x += speed;
            case WEST -> x -= speed;
        }
    }

    @Override
    public void draw(Graphics g) {
        int index= (int) (System.currentTimeMillis()/timeBetweenFrame%spriteSheetNumberOfColumn);

        g.drawImage(image,(int) x, (int) y, (int) (x+width),(int) (y+height),
                (int) (index*this.width), (int) (direction.getFrameLineNumber()*height),
                (int) ((index+1)*this.width), (int)((direction.getFrameLineNumber()+1)*this.height),null);

        drawHealthBar(g);
    }

    private void drawHealthBar(Graphics g) {
        int barWidth = 50;
        int barHeight = 5;
        int barX = (int) x + (int) (width / 2) - (barWidth / 2);
        int barY = (int) y - 10;


        // Draw current health (filled part of the bar)
        int healthWidth = (int) ((double) currentHealth / maxHealth * barWidth);
        g.setColor(Color.BLUE);
        g.fillRect(barX, barY, healthWidth, barHeight);

        // Draw the border of the health bar
        g.setColor(Color.BLACK);
        g.drawRect(barX, barY, barWidth, barHeight);
    }


    public void setDirection(Direction direction) {
        this.direction = direction;
    }


    public double getX() {
        return x;
    }

}

