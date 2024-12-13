package com.example.demo.UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The WinImage class represents the image displayed when the player wins the game.
 * It extends the ImageView class to display the win image at a specified position.
 */
public class WinImage extends ImageView {

    private static final String DEFAULT_IMAGE_NAME = "/com/example/demo/images/youwin.png";
    private static final int DEFAULT_HEIGHT = 500;
    private static final int DEFAULT_WIDTH = 600;

    /**
     * Constructs a new WinImage instance with the specified x and y positions.
     * Uses default image path, width, and height.
     *
     * @param xPosition the x position of the win image
     * @param yPosition the y position of the win image
     */
    public WinImage(double xPosition, double yPosition) {
        this(xPosition, yPosition, DEFAULT_IMAGE_NAME, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * Constructs a new WinImage instance with the specified x and y positions, image path, width, and height.
     *
     * @param xPosition the x position of the win image
     * @param yPosition the y position of the win image
     * @param imagePath the path to the image file
     * @param width the width of the win image
     * @param height the height of the win image
     */
    public WinImage(double xPosition, double yPosition, String imagePath, int width, int height) {
        this.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
        this.setVisible(false);
        this.setFitHeight(height);
        this.setFitWidth(width);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }

    /**
     * Displays the win image on the screen.
     */
    public void showWinImage() {
        this.setVisible(true);
    }
}
