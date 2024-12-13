package com.example.demo.levels;

import javafx.scene.Group;
import com.example.demo.UI.WinImage;
import com.example.demo.UI.GameOverImage;
import com.example.demo.UI.HeartDisplay;

/**
 * The LevelView class manages the UI elements specific to a game level.
 * It handles the display of hearts, win image, and game over image.
 */
public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	private static final int LOSS_SCREEN_X_POSITION = 550;
	private static final int LOSS_SCREEN_Y_POSISITION = 280;
	private final Group root;
	private final WinImage winImage;
	private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	
	/**
     * Constructs a new LevelView instance with the specified root group and number of hearts to display.
     *
     * @param root the root group to which UI elements are added
     * @param heartsToDisplay the initial number of hearts to display
     */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSISITION);
	}

	/**
     * Displays the heart display on the screen.
     */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
     * Displays the win image on the screen.
     */
	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

	/**
     * Displays the game over image on the screen.
     */
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
		
	}
	
	/**
     * Removes hearts from the heart display based on the number of hearts remaining.
     *
     * @param heartsRemaining the number of hearts remaining
     */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

}
