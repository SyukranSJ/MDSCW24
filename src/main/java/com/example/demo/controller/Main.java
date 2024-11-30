package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 750;
    private static final String TITLE = "Sky Battle";
    private Controller gameController;

    @Override
    public void start(Stage stage) {
        setupStage(stage);
        initializeController(stage);
        launchGameSafely();
    }

    private void setupStage(Stage stage) {
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.setWidth(SCREEN_WIDTH);
        stage.setHeight(SCREEN_HEIGHT);
    }

    private void initializeController(Stage stage) {
        gameController = new Controller(stage);
    }

    private void launchGameSafely() {
        try {
            gameController.launchGame();
        } catch (Exception e) {
            handleError(e);
        }
    }

    private void handleError(Exception e) {
        // Display an error dialog to the user
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Game Launch Error");
        alert.setHeaderText("An error occurred while launching the game.");
        alert.setContentText(e.getMessage());
        alert.showAndWait();

        // Log the full stack trace for debugging purposes
        e.printStackTrace();
        if (e instanceof InvocationTargetException) {
            ((InvocationTargetException) e).getCause().printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
