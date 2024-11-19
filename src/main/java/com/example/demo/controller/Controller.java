package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.LevelParent;

 public class Controller {

    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
    private final Stage stage;
    private final StringProperty levelNameProperty = new SimpleStringProperty();

    public Controller(Stage stage) {
        this.stage = stage;

        // Add a listener to the levelNameProperty
        levelNameProperty.addListener((observable, oldValue, newValue) -> {
            try {
                goToLevel(newValue);
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
                    | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText(e.getClass().toString());
                alert.show();
                e.printStackTrace();
            }
        });
    }

    public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        stage.show();
        goToLevel(LEVEL_ONE_CLASS_NAME);
    }

    private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> myClass = Class.forName(className);
        Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
        LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth());
        myLevel.levelNameProperty().bindBidirectional(levelNameProperty);
        Scene scene = myLevel.initializeScene();
        stage.setScene(scene);
        myLevel.startGame();
    }
}