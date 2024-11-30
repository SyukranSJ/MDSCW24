package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.LevelParent;

public class Controller {

    private static final Map<String, Class<? extends LevelParent>> LEVEL_CLASSES = new HashMap<>();

    static {
        // Register level classes
        LEVEL_CLASSES.put("com.example.demo.LevelOne", com.example.demo.LevelOne.class);
        LEVEL_CLASSES.put("com.example.demo.LevelTwo", com.example.demo.LevelTwo.class); // Add more as needed
    }

    private final Stage stage;
    private final StringProperty levelNameProperty = new SimpleStringProperty();

    public Controller(Stage stage) {
        this.stage = stage;
        // Listener for level changes
        levelNameProperty.addListener((observable, oldValue, newValue) -> handleLevelChange(newValue));
    }

    public void launchGame() {
        stage.show();
        handleLevelChange("com.example.demo.LevelOne"); // Start at LevelOne
    }

    private void handleLevelChange(String levelName) {
        try {
            goToLevel(levelName);
        } catch (Exception e) {
            showErrorDialog("Failed to load level: " + levelName, e);
        }
    }

    private void goToLevel(String levelName) throws Exception {
        Class<? extends LevelParent> levelClass = LEVEL_CLASSES.get(levelName);
        if (levelClass == null) {
            throw new IllegalArgumentException("Level not found: " + levelName);
        }

        // Instantiate level
        Constructor<? extends LevelParent> constructor = levelClass.getConstructor(double.class, double.class);
        LevelParent level = constructor.newInstance(stage.getHeight(), stage.getWidth());

        // Bind properties and initialize scene
        level.levelNameProperty().bindBidirectional(levelNameProperty);
        Scene scene = level.initializeScene();
        stage.setScene(scene);
        level.startGame();
    }

    private void showErrorDialog(String message, Exception e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
        e.printStackTrace();
    }
}
