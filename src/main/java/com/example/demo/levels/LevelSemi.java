package com.example.demo.levels;

import com.example.demo.characters.ActiveActorDestructible;
import com.example.demo.characters.EnemyPlane;

import javafx.scene.Scene;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LevelSemi extends LevelParent {
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.LevelTwo";
    private static final int TOTAL_WAVES = 4;
    private static final int BASE_ENEMIES_PER_WAVE = 5;
    private static final double BASE_SPAWN_PROBABILITY = 0.20;
    private static final int PLAYER_INITIAL_HEALTH = 5;

    private int currentWave;
    private boolean waveInProgress;
    private int enemiesSpawnedThisWave;
    private double spawnProbability;

    private Text waveCounterText; // Text for wave counter

    public LevelSemi(double screenHeight, double screenWidth) {
        super(screenHeight, screenWidth, BACKGROUND_IMAGE_NAME, PLAYER_INITIAL_HEALTH);
        this.currentWave = 1;
        this.waveInProgress = true;
        this.enemiesSpawnedThisWave = 0;
        this.spawnProbability = BASE_SPAWN_PROBABILITY;
    }

    @Override
    public Scene initializeScene() {
        Scene scene = super.initializeScene();
        initializeWaveCounter(); // Initialize wave counter
        return scene;
    }

    private void initializeWaveCounter() {
        waveCounterText = new Text("Wave: " + currentWave + " / " + TOTAL_WAVES);
        waveCounterText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        waveCounterText.setFill(javafx.scene.paint.Color.YELLOW);
        waveCounterText.setTranslateX(600); // Place below the kill counter
        waveCounterText.setTranslateY(40); // Adjusted position
        getRoot().getChildren().add(waveCounterText);
    }

    private void updateWaveCounter() {
        waveCounterText.setText("Wave: " + currentWave + " / " + TOTAL_WAVES);
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (currentWave > TOTAL_WAVES && getCurrentNumberOfEnemies() == 0) {
            goToNextLevel(NEXT_LEVEL);
        }
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

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

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }
}
