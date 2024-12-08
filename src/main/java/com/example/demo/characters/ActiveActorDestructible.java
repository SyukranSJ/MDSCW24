package com.example.demo.characters;

/**
 * An abstract class representing a destructible active actor.
 * Combines the functionality of `ActiveActor` and `Destructible` interfaces.
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

    private boolean isDestroyed = false; // Default state is not destroyed.

    /**
     * Constructor to initialize the destructible active actor.
     *
     * @param imageName    The name of the image file for the actor.
     * @param imageHeight  The height of the image.
     * @param initialXPos  The initial X position of the actor.
     * @param initialYPos  The initial Y position of the actor.
     */
    public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        super(imageName, imageHeight, initialXPos, initialYPos);
    }

    /**
     * Updates the position of the actor.
     * This method must be implemented by subclasses.
     */
    @Override
    public abstract void updatePosition();

    /**
     * Updates the state or behavior of the actor.
     * This method must be implemented by subclasses.
     */
    public abstract void updateActor();

    /**
     * Handles logic for taking damage.
     * This method must be implemented by subclasses.
     */
    @Override
    public abstract void takeDamage();

    /**
     * Marks the actor as destroyed.
     */
    @Override
    public void destroy() {
        setDestroyed(true);
    }

    /**
     * Sets the destruction state of the actor.
     *
     * @param isDestroyed  True if the actor is destroyed, false otherwise.
     */
    protected void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    /**
     * Checks if the actor is destroyed.
     *
     * @return True if the actor is destroyed, false otherwise.
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }
}
