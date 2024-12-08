package com.example.demo.characters;

/**
 * Represents an enemy plane in the game, inheriting from the `FighterPlane` class.
 * <p>
 * This class encapsulates the behavior and properties of an enemy plane, including its movement,
 * projectile firing, and health attributes. It supports customizable parameters such as position,
 * speed, fire rate, and health while also providing default values.
 * </p>
 */
public class EnemyPlane extends FighterPlane {

    private static final String IMAGE_NAME = "enemyplane.png";
    private static final int IMAGE_HEIGHT = 70;
    private static final double DEFAULT_FIRE_RATE = 0.01;
    private static final int DEFAULT_HEALTH = 1;
    private final double fireRate;
    private final int horizontalVelocity;
    private final double projectileXOffset;
    private final double projectileYOffset;

    /**
     * Constructs a customizable enemy plane with specified attributes.
     *
     * @param initialXPos     The initial X position of the enemy plane.
     * @param initialYPos     The initial Y position of the enemy plane.
     * @param horizontalVelocity  The speed at which the enemy plane moves horizontally.
     * @param fireRate       The likelihood of firing a projectile in each frame.
     * @param health         The health value representing the durability of the enemy plane.
     */
    public EnemyPlane(double initialXPos, double initialYPos, int horizontalVelocity, double fireRate, int health) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, health);
        this.horizontalVelocity = horizontalVelocity;
        this.fireRate = fireRate;
        this.projectileXOffset = -100.0;
        this.projectileYOffset = -5;
    }

    /**
     * Default constructor for enemy planes with predefined attributes.
     *
     * @param initialXPos The initial X position of the enemy plane.
     * @param initialYPos The initial Y position of the enemy plane.
     */
    public EnemyPlane(double initialXPos, double initialYPos) {
        this(initialXPos, initialYPos, -6, DEFAULT_FIRE_RATE, DEFAULT_HEALTH);
    }

    /**
     * Updates the position of the enemy plane horizontally on the screen.
     */
    @Override
    public void updatePosition() {
        moveHorizontally(horizontalVelocity);
    }

    /**
     * Fires a projectile if the enemy plane is within screen bounds and the conditions for firing are met.
     *
     * @return An instance of `EnemyProjectile` representing the fired projectile, or `null` if the conditions aren't met.
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        if (canFireProjectile() && isWithinScreenBounds()) {
            double projectileXPosition = getProjectileXPosition(projectileXOffset);
            double projectileYPosition = getProjectileYPosition(projectileYOffset);
            return new EnemyProjectile(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    /**
     * Updates the enemy plane's state in each game frame.
     */
    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Determines if the enemy plane can fire a projectile in the current game frame.
     *
     * @return {@code true} if a projectile should be fired, based on the fire rate probability.
     */
    private boolean canFireProjectile() {
        return Math.random() < fireRate;
    }

    /**
     * Checks if the enemy plane remains within the visible bounds of the game screen.
     *
     * @return {@code true} if the enemy plane's position is within screen bounds, {@code false} otherwise.
     */
    private boolean isWithinScreenBounds() {
        return getLayoutX() + getTranslateX() > 0;
    }
}
