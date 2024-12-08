package com.example.demo.characters;

/**
 * Represents a projectile launched by the boss character in the game.
 * <p>
 * This class extends the `Projectile` superclass and defines a boss-specific projectile with a unique image,
 * position, and movement speed. The projectile moves horizontally across the screen at a constant speed,
 * adding to the boss's offensive capabilities.
 * </p>
 */
public class BossProjectile extends Projectile {
    
    private static final String IMAGE_NAME = "fireball1.png";
    private static final int IMAGE_HEIGHT = 70;
    private static final int HORIZONTAL_VELOCITY = -15;
    private static final int INITIAL_X_POSITION = 950;

    /**
     * Constructs a `BossProjectile` with a specified initial Y-coordinate.
     *
     * @param initialYPos The Y-position on the screen where the projectile should be spawned.
     *                   This value determines the vertical alignment of the projectile at launch time.
     */
    public BossProjectile(double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
    }

    /**
     * Updates the position of the projectile by moving it horizontally.
     *
     * <p>
     * This method overrides the parent class's `updatePosition()` method to move the projectile
     * across the screen horizontally using the predefined `HORIZONTAL_VELOCITY`. The movement
     * ensures the projectile advances in the correct direction at a constant speed.
     * </p>
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);  // Move horizontally using HORIZONTAL_VELOCITY
    }

    /**
     * Updates the projectile's state by invoking the `updatePosition()` method.
     *
     * <p>
     * This method is required by the `ActiveActorDestructible` interface. It ensures that the projectile's
     * movement and updates are consistently applied during each game frame, maintaining proper alignment and speed.
     * </p>
     */
    @Override
    public void updateActor() {
        updatePosition();  // Call updatePosition to handle movement
    }
}
