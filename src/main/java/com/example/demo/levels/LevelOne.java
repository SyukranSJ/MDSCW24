package com.example.demo.levels;

import com.example.demo.characters.ActiveActorDestructible;
import com.example.demo.characters.EnemyPlane;

/**
 * The LevelOne class represents the first level of the game.
 * It extends the LevelParent class and provides specific configurations and behaviors for Level One.
 * This includes setting up the background image, initializing enemies, and managing player interactions.
 * The game advances when the player reaches the required kill targets or ends if the player is destroyed.
 */
public class LevelOne extends LevelParent {

    // Constants for level configuration
	/**
	 * The name of the background image for Level One.
	 * Located in the resources path: "/com/example/demo/images/background1.jpg".
	 */
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";

	/**
	 * The fully qualified class name representing the next level (LevelSemi).
	 */
    private static final String NEXT_LEVEL = "com.example.demo.LevelSemi";
	/**
	 * The number of enemy units present in Level One.
	 */
    private static final int TOTAL_ENEMIES = 5;
	/**
	 * The number of enemy kills required to advance to the next level.
	 */
    private static final int KILLS_TO_ADVANCE = 2;
	/**
	 * The probability of spawning a new enemy during gameplay.
	 */
    private static final double ENEMY_SPAWN_PROBABILITY = 0.20;
	/**
	 * The initial health of the player character in Level One.
	 */
    private static final int PLAYER_INITIAL_HEALTH = 5;

	/**
     * Constructs a new LevelOne instance with the specified screen height and width.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
	 * @throws IllegalArgumentException if screenHeight or screenWidth are non-positive
     */
	public LevelOne(double screenHeight, double screenWidth) {
		super(screenHeight, screenWidth, BACKGROUND_IMAGE_NAME, PLAYER_INITIAL_HEALTH);
	}

	/**
     * Checks if the game is over by evaluating the player's health and kill count.
     * If the player is destroyed, the game is lost. If the player reaches the kill target, the next level is loaded.
     *
     * @return
     */
	@Override
	protected boolean checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (userHasReachedKillTarget())
			goToNextLevel(NEXT_LEVEL);
        return false;
    }
	/**
     * Initializes the friendly units for Level One by adding the player's character to the scene.
     */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	/**
     * Spawns enemy units for Level One based on the spawn probability and the total number of enemies.
     * New enemies are added to the scene until the total number of enemies is reached.
     */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition() + 35;
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	/**
     * Instantiates the LevelView for Level One, which manages the UI elements specific to this level.
     *
     * @return a new LevelView instance for Level One
     */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	/**
     * Checks if the player has reached the kill target to advance to the next level.
     *
     * @return true if the player has reached the kill target, false otherwise
     */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

}
