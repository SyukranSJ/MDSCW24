package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShieldImage extends ImageView {
    
    private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";
    private static final int DEFAULT_SHIELD_SIZE = 450;
    
    /**
     * Constructs a ShieldImage with the specified position and default size.
     * 
     * @param xPosition The initial X position of the shield.
     * @param yPosition The initial Y position of the shield.
     */
    public ShieldImage(double xPosition, double yPosition) {
        this(xPosition, yPosition, DEFAULT_SHIELD_SIZE);
    }

    /**
     * Constructs a ShieldImage with the specified position and size.
     * 
     * @param xPosition The initial X position of the shield.
     * @param yPosition The initial Y position of the shield.
     * @param size The size of the shield.
     */
    public ShieldImage(double xPosition, double yPosition, int size) {
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
        this.setImage(loadShieldImage());
        this.setVisible(false);
        this.setFitHeight(size);
        this.setFitWidth(size);
    }

    /**
     * Loads the shield image, ensuring the image is found.
     * 
     * @return the loaded image
     */
    private Image loadShieldImage() {
        Image shieldImage = new Image(getClass().getResource(IMAGE_NAME).toExternalForm());
        if (shieldImage.isError()) {
            throw new RuntimeException("Shield image not found at " + IMAGE_NAME);
        }
        return shieldImage;
    }

    /**
     * Applies a weakened effect to the shield, dimming it.
     */
    public void weakenShield() {
        applyWeakenEffect();
    }

    /**
     * Dims the shield to indicate it is weakened.
     */
    private void applyWeakenEffect() {
        this.setOpacity(0.5);
    }

    /**
     * Shows the shield, resetting its opacity to full visibility.
     */
    public void showShield() {
        this.setVisible(true);
        this.setOpacity(1.0); // Reset the shield's opacity
    }
    
    /**
     * Hides the shield, making it invisible and resetting its opacity.
     */
    public void hideShield() {
        this.setVisible(false);
        this.setOpacity(0.0); // Reset the shield's opacity
    }
}
