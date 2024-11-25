import java.util.ArrayList;

public class PhysicEngine implements Engine {
    private ArrayList<DynamicSprite> movingSpriteList = new ArrayList<>();
    private ArrayList<SolidSprite> environment;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Projectile> projectiles = new ArrayList<>();

    public void addToMovingSpriteList(DynamicSprite sprite) {
        movingSpriteList.add(sprite);
    }

    public void setEnvironment(ArrayList<SolidSprite> environment) {
        this.environment = environment;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void setProjectiles(ArrayList<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    @Override
    public void update() {
        for (DynamicSprite sprite : movingSpriteList) {
            sprite.moveIfPossible(environment);
        }

        // Check collisions between projectiles and enemies
        ArrayList<Projectile> toRemove = new ArrayList<>();
        for (Projectile projectile : projectiles) {
            for (Enemy enemy : enemies) {
                if (enemy.getHitBox().intersects(projectile.getHitBox())) {
                    enemy.takeDamage(25); // Damage the enemy
                    toRemove.add(projectile); // Destroy the projectile
                }
            }
        }
        projectiles.removeAll(toRemove);

        // Remove dead enemies
        enemies.removeIf(enemy -> enemy.getCurrentHealth() <= 0);
    }
}
