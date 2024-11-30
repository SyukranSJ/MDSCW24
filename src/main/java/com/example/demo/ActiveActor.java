package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;

/**
 * Represents an active actor in the game, which is a movable and graphical element.
 */
public abstract class ActiveActor extends ImageView {

    private static final String IMAGE_LOCATION = "/com/example/demo/images/";

    /**
     * Constructs an ActiveActor with the specified image and position.
     *
     * @param imageName   the name of the image file to use
     * @param imageHeight the height to display the image
     * @param initialXPos the initial X position
     * @param initialYPos the initial Y position
     */
    public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        this.setImage(loadImage(imageName));
        this.setLayoutX(initialXPos);
        this.setLayoutY(initialYPos);
        this.setFitHeight(imageHeight);
        this.setPreserveRatio(true);
    }

    /**
     * Abstract method to be implemented for updating the actor's position.
     */
    public abstract void updatePosition();

    /**
     * Moves the actor horizontally by the specified distance.
     *
     * @param horizontalMove the distance to move horizontally
     */
    protected void moveHorizontally(double horizontalMove) {
        this.setTranslateX(getTranslateX() + horizontalMove);
    }

    /**
     * Moves the actor vertically by the specified distance.
     *
     * @param verticalMove the distance to move vertically
     */
    protected void moveVertically(double verticalMove) {
        this.setTranslateY(getTranslateY() + verticalMove);
    }

    /**
     * Loads an image from the resource path.
     *
     * @param imageName the name of the image file
     * @return the loaded image
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
