package com.example.demo.characters;

/**
 * Represents the user-controlled fighter plane in the game.
 * <p>
 * This class extends the `FighterPlane` class and includes specific attributes and behaviors
 * for the user plane, such as movement constraints, projectile firing, and tracking the number of kills.
 * </p>
 */
public class UserPlane extends FighterPlane {

    // Constants representing the visual and movement properties of the plane.
    private static final String IMAGE_NAME = "userplane.png";
    private static final double Y_UPPER_BOUND = 70;
    private static final double Y_LOWER_BOUND = 650.0;
    private static final double INITIAL_X_POSITION = 5.0;
    private static final double INITIAL_Y_POSITION = 300.0;
    private static final int IMAGE_HEIGHT = 75;
    private static final int VERTICAL_VELOCITY = 8;
    private static final int PROJECTILE_X_POSITION = 70;
    private static final int PROJECTILE_Y_POSITION_OFFSET = 25;

    private int velocityMultiplier = 0;  // Controls vertical movement speed (0: stopped, 1: moving down, -1: moving up).
    private int numberOfKills;  // Tracks the number of enemy planes destroyed by the user.

    /**
     * Constructs the `UserPlane` with the specified initial health.
     *
     * @param initialHealth The health value of the user-controlled plane.
     */
    public UserPlane(int initialHealth) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
    }

    /**
     * Updates the position of the user plane based on the current `velocityMultiplier`.
     * <p>
     * Moves the plane vertically while ensuring it remains within the defined upper and lower bounds on the screen.
     * If the plane exceeds these bounds, its position is reset to prevent it from leaving the screen.
     * </p>
     */
    @Override
    public void updatePosition() {
        if (velocityMultiplier != 0) {
            double initialTranslateY = getTranslateY();
            moveVertically(VERTICAL_VELOCITY * velocityMultiplier);

            double newPosition = getLayoutY() + getTranslateY();
            if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
                setTranslateY(initialTranslateY);
            }
        }
    }

    /**
     * Updates the actor each frame by calling the `updatePosition` method.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Fires a projectile from the user plane.
     *
     * @return An instance of `ActiveActorDestructible` representing the projectile.
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
    }

    /**
     * Moves the user plane upward by setting the `velocityMultiplier` to -1.
     */
    public void moveUp() {
        velocityMultiplier = -1;
    }

    /**
     * Moves the user plane downward by setting the `velocityMultiplier` to 1.
     */
    public void moveDown() {
        velocityMultiplier = 1;
    }

    /**
     * Stops vertical movement by setting the `velocityMultiplier` to 0.
     */
    public void stop() {
        velocityMultiplier = 0;
    }

    /**
     * Returns the number of enemy planes destroyed by the user.
     *
     * @return The current number of kills.
     */
    public int getNumberOfKills() {
        return numberOfKills;
    }

    /**
     * Increments the user's kill count by 1.
     */
    public void incrementKillCount() {
        numberOfKills++;
    }
}
