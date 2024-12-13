package com.example.demo.UI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The GameOverImage class represents the game over image displayed when the player loses the game.
 * It extends the ImageView class to display the game over image at a specified position.
 */
public class GameOverImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	/**
     * Constructs a new GameOverImage instance with the specified x and y positions.
     *
     * @param xPosition the x position of the game over image
     * @param yPosition the y position of the game over image
     */
	public GameOverImage(double xPosition, double yPosition) {
		setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()) );
		setLayoutX(xPosition);
		setLayoutY(yPosition);
	}

}
