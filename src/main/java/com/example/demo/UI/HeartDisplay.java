package com.example.demo.UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * The HeartDisplay class manages the display of hearts representing the player's health.
 * It provides methods to initialize, display, and remove hearts.
 */
public class HeartDisplay {
    
    private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
    private static final int HEART_HEIGHT = 30;
    private static final int INITIAL_HEARTS = 0;  // Default to no hearts (adjustable)
    private static final int REMOVE_INDEX = 0;   // Always remove the first item

    private HBox container;
    private int numberOfHeartsToDisplay;
    private Image heartImage; // Cache the image to avoid reloading it each time

    /**
     * Constructs a new HeartDisplay instance with the specified position and number of hearts to display.
     *
     * @param xPosition the x position of the heart display
     * @param yPosition the y position of the heart display
     * @param heartsToDisplay the initial number of hearts to display
     */
    public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
        this.numberOfHeartsToDisplay = heartsToDisplay > INITIAL_HEARTS ? heartsToDisplay : INITIAL_HEARTS; // Ensure non-negative hearts count
        initializeContainer(xPosition, yPosition);
        loadHeartImage();
        initializeHearts();
    }

    /**
     * Initializes the container for the heart display.
     *
     * @param xPosition the x position of the container
     * @param yPosition the y position of the container
     */
    private void initializeContainer(double xPosition, double yPosition) {
        container = new HBox();
        container.setLayoutX(xPosition);
        container.setLayoutY(yPosition);
    }

    /**
     * Loads the heart image to be used in the heart display.
     */
    private void loadHeartImage() {
        // Load the heart image once, for reuse
        heartImage = new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm());
    }

    /**
     * Initializes the hearts in the heart display based on the number of hearts to display.
     */
    private void initializeHearts() {
        for (int i = 0; i < numberOfHeartsToDisplay; i++) {
            ImageView heart = new ImageView(heartImage);
            heart.setFitHeight(HEART_HEIGHT);
            heart.setPreserveRatio(true);
            container.getChildren().add(heart);
        }
    }

    /**
     * Removes a heart from the heart display.
     * Always removes the first heart in the container.
     */
    public void removeHeart() {
        if (!container.getChildren().isEmpty()) {
            container.getChildren().remove(REMOVE_INDEX);  // Always remove the first item
        }
    }

    /**
     * Gets the container for the heart display.
     *
     * @return the container for the heart display
     */
    public HBox getContainer() {
        return container;
    }
}
