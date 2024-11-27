package com.example.demo;

import java.util.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75;
	private static final double BOSS_FIRE_RATE = 0.001;
	private static final double BOSS_SHIELD_PROBABILITY = .1;
	private static final int IMAGE_HEIGHT = 130;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 10;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = 0;
	private static final int Y_POSITION_LOWER_BOUND = 525;
	private static final int MAX_FRAMES_WITH_SHIELD = 100;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;
	private final ShieldImage shieldImage;
    private Rectangle healthBar;
    private int currentHealth;

	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		shieldImage = new ShieldImage(INITIAL_X_POSITION, INITIAL_Y_POSITION);
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		currentHealth = HEALTH;
        createHealthBar();
		initializeMovePattern();
	}

	

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);

		}
		updateShieldPosition();  // Update the shield position after the boss moves
	}
	
	private void updateShieldPosition() {
		shieldImage.setLayoutX(getLayoutX() + getTranslateX() - 95);
		shieldImage.setLayoutY(getLayoutY() + getTranslateY() - 165);
	}


	@Override
	public void updateActor() {
		updatePosition();
		updateShield(); // This will toggle the shield visibility
		//updateHealthBar(); // This will update the health bar width based on health
	}


	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}
	
	@Override
	public void takeDamage() {
        if (!isShielded) {
            super.takeDamage();
            currentHealth -= 1;  // damage reduce health by 1
            if (currentHealth < 0) {
                currentHealth = 0;
            }
            updateHealthBar();  // Update the health bar when damage is taken
        }
    }

	private void createHealthBar() {
        // Create the health bar with initial full width
        healthBar = new Rectangle(1000, 10, currentHealth, 20);  // X, Y, width, height
        healthBar.setFill(Color.GREEN);  // Set the initial color to green
    }

    public Rectangle getHealthBar() {
        return healthBar;
    }

	public void updateHealthBar() {
        // Update the health bar width based on current health
        healthBar.setWidth(currentHealth);
        if (currentHealth > 10) {
            healthBar.setFill(Color.GREEN);
        } else if (currentHealth > 5) {
            healthBar.setFill(Color.YELLOW);
        } else {
            healthBar.setFill(Color.RED);

        }
    }

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++;
			shieldImage.showShield();
			updateShieldPosition();
		}
		else if (shieldShouldBeActivated()) {
			activateShield();	
			updateShieldPosition();
			
		}
		if (shieldExhausted()) {
			
			deactivateShield();
			
		}
	}

	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	private void activateShield() {
		isShielded = true;
		//shieldImage.showShield();
	}

	private void deactivateShield() {
		isShielded = false;
		shieldImage.hideShield();
		framesWithShieldActivated = 0;
	}

	public ShieldImage getShieldImage() {
		return shieldImage;
	}


}
