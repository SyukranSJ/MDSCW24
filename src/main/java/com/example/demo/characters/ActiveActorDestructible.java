package com.example.demo.characters;

/**
 * An abstract class representing a destructible active actor in the game.
 * <p>
 * This class extends {@link ActiveActor} and implements the {@link Destructible} interface.
 * It combines the functionality of a movable actor with the ability to be destroyed.
 * Subclasses must define specific behaviors for actor updates and damage handling.
 * </p>
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

    /** The state indicating whether the actor has been destroyed. */
    private boolean isDestroyed = false; // Default state is not destroyed.

    /**
     * Constructor to initialize the destructible active actor.
     * <p>
     * Sets up the actor with the specified image, height, and position on the screen.
     * </p>
     *
     * @param imageName   The name of the image file representing the actor.
     * @param imageHeight The height of the actor's image (in pixels).
     * @param initialXPos The initial X-coordinate where the actor will be placed.
     * @param initialYPos The initial Y-coordinate where the actor will be placed.
     */
    public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        super(imageName, imageHeight, initialXPos, initialYPos);
    }

    /**
     * Updates the position of the actor on the game screen.
     * <p>
     * Subclasses must provide an implementation for this method to define how the actor moves
     * and interacts with the environment.
     * </p>
     */
    @Override
    public abstract void updatePosition();

    /**
     * Updates the state or behavior of the actor.
     * <p>
     * Subclasses must implement this method to define specific behaviors such as movement,
     * interaction, animations, or any custom logic required in the game.
     * </p>
     */
    public abstract void updateActor();

    /**
     * Handles the logic for taking damage.
     * <p>
     * Subclasses should define how the actor responds to incoming damage, such as reducing health,
     * playing animations, or triggering destruction effects.
     * </p>
     */
    @Override
    public abstract void takeDamage();

    /**
     * Marks the actor as destroyed.
     * <p>
     * Sets the actor's destruction state to true, indicating it is no longer active or functional in the game.
     * </p>
     */
    @Override
    public void destroy() {
        setDestroyed(true);
    }

    /**
     * Sets the destruction state of the actor.
     *
     * @param isDestroyed True if the actor is destroyed, false otherwise.
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
