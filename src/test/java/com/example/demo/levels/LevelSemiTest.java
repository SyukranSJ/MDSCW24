package com.example.demo.levels;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;



class LevelSemiTest extends ApplicationTest {

    private LevelSemi levelSemi;

    @BeforeEach
    void setUp() {
        // Set up a LevelSemi instance before each test.
        levelSemi = new LevelSemi(600, 800);  // Example screen size: 600x800
    }

    @Test
    void testInitializeScene() {
        // Test the scene initialization.
        Scene scene = levelSemi.initializeScene();

        assertNotNull(scene);
        assertTrue(scene.getRoot() instanceof javafx.scene.layout.Pane);
    }

    @Test
    void testInitializeWaveCounter() {
        levelSemi.initializeScene();

        // Find the Text node by iterating through the children and checking the type
        Text waveCounter = null;
        for (Node node : levelSemi.getRoot().getChildren()) {
            if (node instanceof Text && ((Text) node).getText().startsWith("Wave:")) {
                waveCounter = (Text) node;
                break;
            }
        }

        assertNotNull(waveCounter, "Wave counter Text node should not be null.");
        assertEquals("Wave: 1 / 4", waveCounter.getText());
    }

    @Test
    void testUpdateWaveCounter() {
        // Test if the wave counter updates correctly after progressing a wave.
        levelSemi.initializeScene();
        levelSemi.startNextWave(); // Proceed to wave 2

        // Find the Text node by iterating through the children and checking the type
        Text waveCounter = null;
        for (Node node : levelSemi.getRoot().getChildren()) {
            if (node instanceof Text && ((Text) node).getText().startsWith("Wave:")) {
                waveCounter = (Text) node;
                break;
            }
        }
        assertNotNull(waveCounter, "Wave counter Text node should not be null.");
        assertEquals("Wave: 2 / 4", waveCounter.getText());
    }

    @Test
    void testSpawnEnemyUnits() {
        // Test if the correct number of enemies are spawned during the wave.
        levelSemi.spawnEnemyUnits();

        int numberOfEnemies = levelSemi.getCurrentNumberOfEnemies();
        assertTrue(numberOfEnemies > 0, "Enemies should be spawned in the wave.");
    }

    @Test
    void testStartNextWave() {
        // Test if the next wave starts and the wave counter updates.
        LevelSemi levelSemi = new LevelSemi(800, 700);
        levelSemi.initializeScene();
        levelSemi.startNextWave();

        assertTrue(levelSemi.getCurrentWave() > 1, "Wave should progress.");
    }
}
