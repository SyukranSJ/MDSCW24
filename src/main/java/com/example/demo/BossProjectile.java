package com.example.demo;

public class BossProjectile extends Projectile {
    
    private static final String IMAGE_NAME = "fireball.png";
    private static final int IMAGE_HEIGHT = 55;
    private static final int HORIZONTAL_VELOCITY = -15;
    private static final int INITIAL_X_POSITION = 950;

    // Constructor to initialize with a specific Y position
    public BossProjectile(double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
    }

    // Implement updatePosition method from the parent class (Projectile)
    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);  // Move horizontally using HORIZONTAL_VELOCITY
    }

    // Implement updateActor method, as required by ActiveActorDestructible
    @Override
    public void updateActor() {
        updatePosition();  // Call updatePosition to handle movement
    }
}
