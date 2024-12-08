package com.example.demo.characters;

import java.util.*;
import com.example.demo.UI.ShieldImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents the boss character in the game.
 * <p>
 * The Boss class extends {@link FighterPlane} and contains functionalities such as movement patterns,
 * shield mechanics, projectile attacks, health management, and damage interactions.
 * It has an evolving shield state with visual indicators and dynamic health bar updates.
 * </p>
 */
public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75;
	private static final double BOSS_FIRE_RATE = 0.1;
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
	/** List representing the boss's movement pattern */
	private final List<Integer> movePattern;
	/** The current state of the boss's shield */
	private ShieldState shieldState;
	/** The number of consecutive moves in the same direction */
	private int consecutiveMovesInSameDirection;
	/** The index of the current move in the move pattern */
	private int indexOfCurrentMove;
	/** The number of frames the shield has been activated */
	private int framesWithShieldActivated;
	/** The shield image associated with the boss */
	private final ShieldImage shieldImage;
	/** The health bar representing the boss's health */
    private Rectangle healthBar;
	/** The current health of the boss */
    private int currentHealth;

	 /**
     * Enum representing the possible states of the boss's shield.
     */
	public enum ShieldState {
		ACTIVE,   // Shield is fully functional
		DAMAGED,  // Shield is partially functional
		INACTIVE  // Shield is off
	}
	
 	/**
     * Constructs the boss character with default properties and initializes essential components.
     * <p>
     * Sets up the boss's image, movement pattern, shield mechanics, health, and health bar.
     * </p>
     */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		shieldImage = new ShieldImage(INITIAL_X_POSITION, INITIAL_Y_POSITION);
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		shieldState = ShieldState.INACTIVE;
		currentHealth = HEALTH;
        createHealthBar();
		initializeMovePattern();
	}
	/**
     * Updates the boss's position on the game screen.
     * Moves the boss vertically according to the next move in the movement pattern while ensuring it stays within bounds.
     */
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

	/**
     * Updates the shield's position on the screen based on the boss's layout and translation properties.
     */
	private void updateShieldPosition() {
		shieldImage.setLayoutX(getLayoutX() + getTranslateX() - 95);
		shieldImage.setLayoutY(getLayoutY() + getTranslateY() - 165);
	}

	/**
     * Updates the boss's state and behavior every frame.
     * Moves the boss, updates shield mechanics, and handles visual updates.
     */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield(); // This will toggle the shield visibility
		
	}

	/**
     * Fires a projectile if the boss's firing condition is met.
     *
     * @return A new instance of {@link ActiveActorDestructible} representing the projectile, or null if it doesn't fire.
     */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}
	
	/**
     * Handles the boss taking damage, considering shield mechanics and health reduction.
     * <p>
     * Transitions the shield state or reduces the boss's health based on the current shield status.
     * </p>
     */
	@Override
	public void takeDamage() {
        if (shieldState == ShieldState.ACTIVE) {
            shieldState = ShieldState.DAMAGED;  // Transition to DAMAGED state
            shieldImage.weakenShield();           // Visual indicator for damage
        } else if (shieldState == ShieldState.DAMAGED) {
            shieldState = ShieldState.INACTIVE; // Fully deactivate the shield
            shieldImage.hideShield();
        } else {
            super.takeDamage();                // Directly reduce health if shield is INACTIVE
            currentHealth -= 1;
            updateHealthBar();
        }
    }

	/**
     * Creates a health bar to visually represent the boss's health status on the screen.
     */
	private void createHealthBar() {
        // Create the health bar with initial full width
        healthBar = new Rectangle(1000, 10, currentHealth, 20);  // X, Y, width, height
        healthBar.setFill(Color.GREEN);  // Set the initial color to green
    }

    public Rectangle getHealthBar() {
        return healthBar;
    }

	/**
     * Updates the health bar based on the boss's current health value.
     *
     * Changes the health bar color depending on the health percentage:
     * - Green: Full health
     * - Yellow: Medium health
     * - Red: Low health
     */
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

	/**
     * Initializes the boss's movement pattern, ensuring it follows a predefined sequence of movements.
     */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
     * Updates the shield state and visual representation based on game mechanics and random probability.
     */
	private void updateShield() {
		if (shieldState == ShieldState.ACTIVE) {
			framesWithShieldActivated++;
			shieldImage.showShield();
			updateShieldPosition();
		}
		else if (shieldShouldBeActivated()) {
			activateShield();	
			
		}
		if (shieldExhausted()) {
			
			deactivateShield();
			
		}
	}

	/**
	 * Retrieves the next move from the boss's movement pattern sequence.
	 * <p>
	 * This method updates the boss's movement by selecting the next move in the `movePattern` list.
	 * It tracks consecutive moves in the same direction and reshuffles the movement pattern
	 * once a specified limit (`MAX_FRAMES_WITH_SAME_MOVE`) is reached. The method ensures that
	 * the boss continuously follows a dynamic movement pattern without getting stuck in a loop.
	 * </p>
	 *
	 * @return The next movement value representing the boss's vertical movement direction.
	 *         Positive value for downward movement, negative value for upward movement, and zero indicates no movement.
	 */
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

	/**
	 * Determines whether the boss should fire a projectile in the current game frame.
	 * <p>
	 * This method uses a random probability check to determine if the boss should shoot a projectile
	 * based on the defined `BOSS_FIRE_RATE`. The higher the `BOSS_FIRE_RATE` value, the more frequently
	 * the boss fires projectiles. It provides a dynamic and unpredictable attack pattern.
	 * </p>
	 *
	 * @return `true` if the boss fires a projectile in the current frame; `false` otherwise.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Calculates the initial vertical position for the projectile launched by the boss.
	 * <p>
	 * This method computes the Y-coordinate where the projectile will be spawned. It considers the
	 * boss's current layout position and translation properties along with an offset value
	 * (`PROJECTILE_Y_POSITION_OFFSET`) to ensure proper visual alignment for projectiles on the screen.
	 * </p>
	 *
	 * @return A `double` representing the Y-coordinate for the projectile's spawn position.
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
     * Determines if the boss should activate the shield based on a probability check.
     *
     * @return True if the shield should activate, false otherwise.
     */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
     * Checks if the shield has been active long enough to deactivate it automatically.
     *
     * @return True if the shield is exhausted, false otherwise.
     */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
     * Activates the shield and updates its visual state on the screen.
     */
	private void activateShield() {
		shieldState = ShieldState.ACTIVE;
		shieldImage.showShield();
		updateShieldPosition();
	}

	/**
     * Deactivates the shield and resets the relevant state variables.
     */
	private void deactivateShield() {
		shieldState = ShieldState.INACTIVE;
		shieldImage.hideShield();
		framesWithShieldActivated = 0;
	}

	/**
     * Sets the opacity of the shield to visually dim it on the screen.
     */
	public void dimShield() {
		shieldImage.setOpacity(0.5); // Dimming the shield by setting opacity
	}	

	/**
     * Returns the `ShieldImage` instance representing the boss's shield.
     *
     * @return The shield's visual representation.
     */
	public ShieldImage getShieldImage() {
		return shieldImage;
	}


}
