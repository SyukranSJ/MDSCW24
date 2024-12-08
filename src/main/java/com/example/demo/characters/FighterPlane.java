package com.example.demo.characters;

/**
 * An abstract class representing a fighter plane in the game.
 * <p>
 * This class serves as the base class for all fighter planes, including enemy and boss planes.
 * It contains common attributes and behaviors, such as health management, projectile positioning,
 * and damage handling.
 * </p>
 */
public abstract class FighterPlane extends ActiveActorDestructible {

    private int health;

    /**
     * Constructs a `FighterPlane` with the specified attributes.
     *
     * @param imageName    The name of the image representing the fighter plane.
     * @param imageHeight  The height of the fighter plane's image.
     * @param initialXPos  The initial X position of the fighter plane on the screen.
     * @param initialYPos  The initial Y position of the fighter plane on the screen.
     * @param health      The initial health value of the fighter plane.
     */
    public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
        super(imageName, imageHeight, initialXPos, initialYPos);
        this.health = health;
    }

    /**
     * Abstract method that must be implemented by subclasses to define projectile firing behavior.
     *
     * @return An instance of `ActiveActorDestructible` representing the projectile fired by the plane.
     */
    public abstract ActiveActorDestructible fireProjectile();

    /**
     * Handles taking damage by reducing health.
     * <p>
     * If the health value reaches zero, the fighter plane is destroyed by invoking the `destroy()` method.
     * </p>
     */
    @Override
    public void takeDamage() {
        health--;
        if (healthAtZero()) {
            this.destroy();  // Destroy the fighter plane when health reaches zero.
        }
    }

    /**
     * Calculates the X position where a projectile should be spawned, considering an offset.
     *
     * @param xPositionOffset The horizontal offset for the projectile's spawn position.
     * @return The adjusted X position for the projectile spawn.
     */
    protected double getProjectileXPosition(double xPositionOffset) {
        return getLayoutX() + getTranslateX() + xPositionOffset;
    }

    /**
     * Calculates the Y position where a projectile should be spawned, considering an offset.
     *
     * @param yPositionOffset The vertical offset for the projectile's spawn position.
     * @return The adjusted Y position for the projectile spawn.
     */
    protected double getProjectileYPosition(double yPositionOffset) {
        return getLayoutY() + getTranslateY() + yPositionOffset;
    }

    /**
     * Checks if the fighter plane's health has reached zero or below.
     *
     * @return `true` if health is zero or less; `false` otherwise.
     */
    protected boolean healthAtZero() {
        return health <= 0;
    }

    /**
     * Retrieves the current health value of the fighter plane.
     *
     * @return The health value representing the fighter plane's health status.
     */
    public int getHealth() {
        return health;
    }
}
