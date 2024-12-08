package com.example.demo.characters;

public class EnemyPlane extends FighterPlane {

    private static final String IMAGE_NAME = "enemyplane.png";
    private static final int IMAGE_HEIGHT = 70;
    private static final double DEFAULT_FIRE_RATE = 0.01;
    private static final int DEFAULT_HEALTH = 1;
    private final double fireRate;
    private final int horizontalVelocity;
    private final double projectileXOffset;
    private final double projectileYOffset;

    /**
     * Constructor for a customizable enemy plane.
     */
    public EnemyPlane(double initialXPos, double initialYPos, int horizontalVelocity, double fireRate, int health) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, health);
        this.horizontalVelocity = horizontalVelocity;
        this.fireRate = fireRate;
        this.projectileXOffset = -100.0;
        this.projectileYOffset = -5;
    }

    /**
     * Default constructor for enemy planes.
     */
    public EnemyPlane(double initialXPos, double initialYPos) {
        this(initialXPos, initialYPos, -6, DEFAULT_FIRE_RATE, DEFAULT_HEALTH);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(horizontalVelocity);
    }

    @Override
    public ActiveActorDestructible fireProjectile() {
        if (canFireProjectile() && isWithinScreenBounds()) {
            double projectileXPosition = getProjectileXPosition(projectileXOffset);
            double projectileYPosition = getProjectileYPosition(projectileYOffset);
            return new EnemyProjectile(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * Determines if the enemy plane can fire a projectile in this frame.
     */
    private boolean canFireProjectile() {
        return Math.random() < fireRate;
    }

    /**
     * Checks if the plane is within the visible bounds of the screen.
     */
    private boolean isWithinScreenBounds() {
        return getLayoutX() + getTranslateX() > 0;
    }
}
