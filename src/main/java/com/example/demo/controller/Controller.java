package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.levels.LevelParent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Controller class for managing the game's levels and handling transitions between them.
 * This class uses a map to register levels and dynamically loads the appropriate level
 * based on the provided level name.
 */
public class Controller {

    /**
     * A map that associates level names (fully qualified class names) with their corresponding classes.
     */
    private static final Map<String, Class<? extends LevelParent>> LEVEL_CLASSES = new HashMap<>();

    // Static block to initialize the LEVEL_CLASSES map with registered level classes
    static {
        LEVEL_CLASSES.put("com.example.demo.LevelOne", com.example.demo.levels.LevelOne.class);
        LEVEL_CLASSES.put("com.example.demo.LevelSemi", com.example.demo.levels.LevelSemi.class);
        LEVEL_CLASSES.put("com.example.demo.LevelTwo", com.example.demo.levels.LevelTwo.class);
    }

    /**
     * The primary stage of the application where the game's scenes are displayed.
     */
    private final Stage stage;

    /**
     * A StringProperty to track the current level's name. This allows for binding and updates when levels change.
     */
    private final StringProperty levelNameProperty = new SimpleStringProperty();

    /**
     * Constructor for the Controller class.
     *
     * @param stage The primary stage of the application where game scenes will be displayed.
     */
    public Controller(Stage stage) {
        this.stage = stage;
        // Listener to handle level changes when the levelNameProperty changes
        levelNameProperty.addListener((observable, oldValue, newValue) -> handleLevelChange(newValue));
    }

    /**
     * Gets the primary stage of the application.
     *
     * @return The application's primary stage.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Launches the game by showing the stage and starting the first level.
     */
    public void launchGame() {
        stage.show();
        handleLevelChange("com.example.demo.LevelOne"); // Start at LevelOne
    }

    /**
     * Handles the transition to a new level based on the level's name.
     *
     * @param levelName The fully qualified name of the level class to load.
     */
    public void handleLevelChange(String levelName) {
        try {
            goToLevel(levelName);
        } catch (Exception e) {
            showErrorDialog("Failed to load level: " + levelName, e);
        }
    }

    /**
     * Transitions to a specified level by dynamically loading and initializing the level.
     *
     * @param levelName The fully qualified name of the level class to load.
     * @throws Exception If there is an error during level instantiation or initialization.
     */
    private void goToLevel(String levelName) throws Exception {
        // Retrieve the class for the specified level name
        Class<? extends LevelParent> levelClass = LEVEL_CLASSES.get(levelName);
        if (levelClass == null) {
            throw new IllegalArgumentException("Level not found: " + levelName);
        }

        // Use reflection to instantiate the level with the required constructor
        Constructor<? extends LevelParent> constructor = levelClass.getConstructor(double.class, double.class);
        LevelParent level = constructor.newInstance(stage.getHeight(), stage.getWidth());

        // Bind the level's property to this controller's property and initialize the scene
        level.levelNameProperty().bindBidirectional(levelNameProperty);
        Scene scene = level.initializeScene();
        stage.setScene(scene);
        level.startGame();
    }

    /**
     * Displays an error dialog when an exception occurs during level loading or transition.
     *
     * @param message The error message to display to the user.
     * @param e       The exception that occurred.
     */
    public void showErrorDialog(String message, Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
        e.printStackTrace();
    }
}
