package com.example.demo.characters;

/**
 * Represents a projectile fired by the user-controlled plane.
 * <p>
 * This class extends the `Projectile` superclass and encapsulates the behavior of the user's projectile,
 * including its movement and visual properties on the screen.
 * </p>
 */
public class UserProjectile extends Projectile {

    // Constants representing the visual and movement properties of the projectile.
    private static final String IMAGE_NAME = "userfire1.png";  // The image representing the projectile.
    private static final int IMAGE_HEIGHT = 10;               // The height of the projectile image.
    private static final int HORIZONTAL_VELOCITY = 15;        // Speed at which the projectile moves horizontally.

    /**
     * Constructs a `UserProjectile` with the specified initial X and Y positions.
     *
     * @param initialXPos The X-coordinate where the projectile originates.
     * @param initialYPos The Y-coordinate where the projectile originates.
     */
    public UserProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    /**
     * Updates the position of the projectile by moving it horizontally to the right.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
    }

    /**
     * Updates the actor each game frame by moving the projectile horizontally.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }
}
