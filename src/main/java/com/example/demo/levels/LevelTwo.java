package com.example.demo.levels;

import com.example.demo.characters.Boss;

/**
 * The LevelTwo class represents the second level of the game.
 * It extends the LevelParent class and provides specific configurations and behaviors for this level.
 */
public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private final Boss boss;
    
   // private LevelView levelView; // Corrected to LevelView instead of LevelViewLevelTwo
   /**
     * Constructs a new LevelTwo instance with the specified screen height and width.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    public LevelTwo(double screenHeight, double screenWidth) {
        super(screenHeight, screenWidth, BACKGROUND_IMAGE_NAME, PLAYER_INITIAL_HEALTH);
        boss = createBoss();
    }

    /**
     * Creates and returns a new Boss instance.
     *
     * @return a new Boss instance
     */
    protected Boss createBoss() {
        return new Boss(); // Customize initialization of Boss
    }

    /**
     * Initializes the friendly units for this level by adding the player's character to the scene.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Checks if the game is over by evaluating the player's health and the boss's health.
     * If the player is destroyed, the game is lost. If the boss is destroyed, the game is won.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            winGame();
        }
    }

    /**
     * Spawns enemy units for this level by adding the boss to the scene if no enemies are present.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            addBossToScene();
        }
    }

    /**
     * Adds the boss to the scene along with its health bar and shield image.
     */
    private void addBossToScene() {
        addEnemyUnit(boss);
        getRoot().getChildren().addAll(boss.getHealthBar(), boss.getShieldImage());
    }

    /**
     * Instantiates the LevelView for this level, which manages the UI elements specific to this level.
     *
     * @return a new LevelView instance for this level
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

}