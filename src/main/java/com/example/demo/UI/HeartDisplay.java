package com.example.demo.UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HeartDisplay {
    
    private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
    private static final int HEART_HEIGHT = 30;
    private static final int INITIAL_HEARTS = 0;  // Default to no hearts (adjustable)
    private static final int REMOVE_INDEX = 0;   // Always remove the first item

    private HBox container;
    private int numberOfHeartsToDisplay;
    private Image heartImage; // Cache the image to avoid reloading it each time

    public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
        this.numberOfHeartsToDisplay = heartsToDisplay > INITIAL_HEARTS ? heartsToDisplay : INITIAL_HEARTS; // Ensure non-negative hearts count
        initializeContainer(xPosition, yPosition);
        loadHeartImage();
        initializeHearts();
    }

    private void initializeContainer(double xPosition, double yPosition) {
        container = new HBox();
        container.setLayoutX(xPosition);
        container.setLayoutY(yPosition);
    }

    private void loadHeartImage() {
        // Load the heart image once, for reuse
        heartImage = new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm());
    }

    private void initializeHearts() {
        for (int i = 0; i < numberOfHeartsToDisplay; i++) {
            ImageView heart = new ImageView(heartImage);
            heart.setFitHeight(HEART_HEIGHT);
            heart.setPreserveRatio(true);
            container.getChildren().add(heart);
        }
    }

    public void removeHeart() {
        if (!container.getChildren().isEmpty()) {
            container.getChildren().remove(REMOVE_INDEX);  // Always remove the first item
        }
    }

    public HBox getContainer() {
        return container;
    }
}
