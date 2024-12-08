package com.example.demo.characters;

public class EnemyProjectile extends Projectile {

    // Move these constants to a central configuration class if possible
    private static final String IMAGE_NAME = "enemyfire1.png";
    private static final int IMAGE_HEIGHT = 80;
    private int horizontalVelocity = -10 ;

    public EnemyProjectile(double initialXPos, double initialYPos) {
        this(initialXPos, initialYPos, -10); // Default velocity
    }

    public EnemyProjectile(double initialXPos, double initialYPos, int velocity) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
        this.horizontalVelocity = velocity;
    }

    @Override
    public void updatePosition() {
        moveHorizontally(horizontalVelocity);
    }

    @Override
    public void updateActor() {
        updatePosition();
        checkBounds(); // Optional: Remove projectiles off-screen
    }

    /**
     * Check if the projectile is out of screen bounds and should be removed.
     */
    private void checkBounds() {
        if (getLayoutX() + getTranslateX() < 0) {
            destroy(); // Assuming a destroy method exists in `Projectile` or its superclass
        }
    }
}
