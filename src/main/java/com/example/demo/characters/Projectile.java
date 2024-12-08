package com.example.demo.characters;

/**
 * Represents a generic projectile in the game. 
 * Subclasses of this class must define their own behavior for updating the position of the projectile.
 */
public abstract class Projectile extends ActiveActorDestructible {

    /**
     * Constructs a new projectile with the specified image name, height, and initial position.
     *
     * @param imageName   The name of the image file that represents the projectile.
     * @param imageHeight The height of the projectile image.
     * @param initialXPos The initial X position of the projectile on the screen.
     * @param initialYPos The initial Y position of the projectile on the screen.
     */
    public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        super(imageName, imageHeight, initialXPos, initialYPos);
    }

    /**
     * Defines what happens when the projectile takes damage.
     * By default, this method destroys the projectile.
     * This can be overridden in subclasses if different behavior is needed.
     */
    @Override
    public void takeDamage() {
        handleDestruction();
    }

    /**
     * Handles the destruction of the projectile.
     * This method is invoked when the projectile is destroyed.
     */
    protected void handleDestruction() {
        this.destroy();
        // Additional destruction behavior can be added here, such as triggering sound effects or animations.
    }

    /**
     * Updates the position of the projectile.
     * Subclasses must implement this method to define how the projectile moves.
     */
    @Override
    public abstract void updatePosition();
}
