package com.example.demo.levels;

import com.example.demo.characters.ActiveActorDestructible;
import com.example.demo.characters.EnemyPlane;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * The LevelSemi class represents the semi-final level of the game.
 * It extends the LevelParent class and provides specific configurations and behaviors for this level.
 */
public class LevelSemi extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.LevelTwo";
    private static final int TOTAL_WAVES = 4;
    private static final int BASE_ENEMIES_PER_WAVE = 5;
    private static final double BASE_SPAWN_PROBABILITY = 0.20;
    private static final int PLAYER_INITIAL_HEALTH = 5;

    public int getCurrentWave() {
        return currentWave;
    }

    public static int getTotalWaves() {
        return TOTAL_WAVES;
    }

    private int currentWave;
    private boolean waveInProgress;
    private int enemiesSpawnedThisWave;
    private double spawnProbability;

    private Text waveCounterText; // Text for wave counter

    /**
     * Constructs a new LevelSemi instance with the specified screen height and width.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    public LevelSemi(double screenHeight, double screenWidth) {
        super(screenHeight, screenWidth, BACKGROUND_IMAGE_NAME, PLAYER_INITIAL_HEALTH);
        this.currentWave = 1;
        this.waveInProgress = true;
        this.enemiesSpawnedThisWave = 0;
        this.spawnProbability = BASE_SPAWN_PROBABILITY;
    }

    /**
     * Initializes the scene by setting up the background, friendly units, and UI elements.
     *
     * @return the initialized Scene object
     */
    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        initializeWaveCounter(); // Initialize wave counter
        return scene;
    }

    /**
     * Initializes the wave counter text and adds it to the scene.
     */
    private void initializeWaveCounter() {
        waveCounterText = new Text("Wave: " + currentWave + " / " + TOTAL_WAVES);
        waveCounterText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        waveCounterText.setFill(javafx.scene.paint.Color.YELLOW);
        waveCounterText.setTranslateX(600);
        waveCounterText.setTranslateY(40); 
        getRoot().getChildren().add(waveCounterText);
    }

    /**
     * Updates the wave counter text to display the current wave.
     */
    private void updateWaveCounter() {
        waveCounterText.setText("Wave: " + currentWave + " / " + TOTAL_WAVES);
    }

    /**
     * Checks if the game is over by evaluating the player's health and wave progress.
     * If the player is destroyed, the game is lost. If all waves are cleared, the next level is loaded.
     *
     * @return
     */
    @Override
    protected boolean checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
            return true;
        } else if (currentWave > TOTAL_WAVES && getCurrentNumberOfEnemies() == 0) {
            goToNextLevel(NEXT_LEVEL);
        }
        return false;
    }

    /**
     * Initializes the friendly units for this level by adding the player's character to the scene.
     */
    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * Spawns enemy units for this level based on the spawn probability and the total number of enemies.
     * New enemies are added to the scene until the total number of enemies is reached.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (!waveInProgress) return;

        int enemiesInCurrentWave = BASE_ENEMIES_PER_WAVE + (currentWave - 1) * 3;

        while (enemiesSpawnedThisWave < enemiesInCurrentWave && Math.random() < spawnProbability) {
            double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition() + 35;
            ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);

            addEnemyUnit(newEnemy);
            enemiesSpawnedThisWave++;
        }

        if (enemiesSpawnedThisWave >= enemiesInCurrentWave && getCurrentNumberOfEnemies() == 0) {
            waveInProgress = false;
            enemiesSpawnedThisWave = 0;
            currentWave++;
            updateWaveCounter(); // Update wave counter for new wave
            startNextWave();
        }
    }

    /**
     * Starts the next wave by resetting the wave progress and updating the wave counter.
     * If all waves are cleared, the game over condition is checked.
     */
    protected void startNextWave() {
        if (currentWave <= TOTAL_WAVES) {
            waveInProgress = true;
            spawnProbability += 0.05;
            updateWaveCounter(); // Ensure the wave counter is updated
        } else {
            waveCounterText.setText("All waves cleared!"); // Final wave message
            checkIfGameOver();
        }
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
