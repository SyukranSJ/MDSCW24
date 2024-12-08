package com.example.demo.characters;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;

/**
 * Represents an active actor in the game.
 * <p>
 * An active actor is a movable and graphical element on the game screen.
 * This class extends {@link ImageView} and provides fundamental functionalities
 * for positioning, movement, and image loading.
 * </p>
 */
public abstract class ActiveActor extends ImageView {

    /** The location of the images folder within the resources. */
    private static final String IMAGE_LOCATION = "/com/example/demo/images/";

    /**
     * Constructs an ActiveActor with the specified image and position.
     * <p>
     * Initializes the actor with an image, sets its height, and positions it at the
     * specified X and Y coordinates on the game screen.
     * </p>
     *
     * @param imageName   the name of the image file to use (e.g., "hero.png")
     * @param imageHeight the height to display the image (in pixels)
     * @param initialXPos the initial X position of the actor on the screen
     * @param initialYPos the initial Y position of the actor on the screen
     */
    public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        this.setImage(loadImage(imageName));
        this.setLayoutX(initialXPos);
        this.setLayoutY(initialYPos);
        this.setFitHeight(imageHeight);
        this.setPreserveRatio(true);
    }

    /**
     * Abstract method to be implemented by subclasses to update the actor's position.
     * <p>
     * Subclasses will define how the actor's position changes based on game logic,
     * such as movement, collisions, or interactions.
     * </p>
     */
    public abstract void updatePosition();

    /**
     * Moves the actor horizontally by the specified distance.
     * <p>
     * Updates the actor's horizontal position by changing the X-coordinate with the given
     * distance. This movement is relative to the actor's current position.
     * </p>
     *
     * @param horizontalMove the distance to move horizontally (in pixels)
     */
    protected void moveHorizontally(double horizontalMove) {
        this.setTranslateX(getTranslateX() + horizontalMove);
    }

    /**
     * Moves the actor vertically by the specified distance.
     * <p>
     * Updates the actor's vertical position by changing the Y-coordinate with the provided
     * distance. This movement is relative to the actor's current position.
     * </p>
     *
     * @param verticalMove the distance to move vertically (in pixels)
     */
    protected void moveVertically(double verticalMove) {
        this.setTranslateY(getTranslateY() + verticalMove);
    }

    /**
     * Loads an image from the resource path.
     * <p>
     * Constructs the path to the specified image within the resources directory and attempts
     * to load it. If the image is not found, it throws an {@link IllegalArgumentException}.
     * </p>
     *
     * @param imageName the name of the image file (e.g., "enemy.png")
     * @return an {@link Image} object representing the loaded image
     * @throws IllegalArgumentException if the image cannot be found at the specified path
     */
    private Image loadImage(String imageName) {
        String imagePath = IMAGE_LOCATION + imageName;
        URL resourceUrl = getClass().getResource(imagePath);

        if (resourceUrl == null) {
            throw new IllegalArgumentException("Image not found: " + imagePath);
        }

        return new Image(resourceUrl.toExternalForm());
    }
}
