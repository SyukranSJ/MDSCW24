package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WinImage extends ImageView {

    private static final String DEFAULT_IMAGE_NAME = "/com/example/demo/images/youwin.png";
    private static final int DEFAULT_HEIGHT = 500;
    private static final int DEFAULT_WIDTH = 600;

    public WinImage(double xPosition, double yPosition) {
        this(xPosition, yPosition, DEFAULT_IMAGE_NAME, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    public WinImage(double xPosition, double yPosition, String imagePath, int width, int height) {
        this.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
        this.setVisible(false);
        this.setFitHeight(height);
        this.setFitWidth(width);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }

    public void showWinImage() {
        this.setVisible(true);
    }
}
