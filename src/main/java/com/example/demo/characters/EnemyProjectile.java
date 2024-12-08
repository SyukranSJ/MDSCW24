package com.example.demo.characters;

/**
 * Represents a projectile fired by an enemy plane in the game.
 * <p>
 * This class handles the movement and lifecycle of enemy projectiles, including their horizontal
 * movement across the screen. It also contains logic to remove projectiles once they exit the screen bounds.
 * </p>
 */
public class EnemyProjectile extends Projectile {

    // Move these constants to a central configuration class if possible
    private static final String IMAGE_NAME = "enemyfire1.png";
    private static final int IMAGE_HEIGHT = 80;
    private int horizontalVelocity = -5;

    /**
     * Constructs an `EnemyProjectile` with a specified position and default horizontal velocity.
     *
     * @param initialXPos The initial X position of the projectile.
     * @param initialYPos The initial Y position of the projectile.
     */
    public EnemyProjectile(double initialXPos, double initialYPos) {
        this(initialXPos, initialYPos, -10); // Default horizontal velocity
    }

    /**
     * Constructs an `EnemyProjectile` with a specified position and customizable velocity.
     *
     * @param initialXPos The initial X position of the projectile.
     * @param initialYPos The initial Y position of the projectile.
     * @param velocity    The horizontal speed at which the projectile moves across the screen.
     */
    public EnemyProjectile(double initialXPos, double initialYPos, int velocity) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
        this.horizontalVelocity = velocity;
    }

    /**
     * Updates the projectile's position by moving it horizontally across the screen.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(horizontalVelocity);
    }

    /**
     * Updates the state of the projectile each game frame, including its movement and bounds checking.
     */
    @Override
    public void updateActor() {
        updatePosition();
        checkBounds(); // Remove projectile if it exits the screen bounds
    }

    /**
     * Checks if the projectile has moved out of the visible bounds of the screen and removes it.
     * <p>
     * If the projectile is no longer visible (i.e., off-screen), it calls the `destroy()` method,
     * which cleans up the object as per the superclass implementation.
     * </p>
     */
    private void checkBounds() {
        if (getLayoutX() + getTranslateX() < 0) {
            destroy(); // Assuming a `destroy()` method exists in `Projectile` or its superclass
        }
    }
}
