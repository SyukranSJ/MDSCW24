package com.example.demo.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {

    private Controller controller;

    @Override
    public void start(Stage primaryStage) {
        // Initialize the Controller
        controller = new Controller(primaryStage);

        // Create buttons for the menu
        Button startGameButton = new Button("Start Game");
        Button levelSelectButton = new Button("Level Select");
        Button exitButton = new Button("Exit");

        // Set up actions for buttons
        startGameButton.setOnAction(e -> startGame());
        levelSelectButton.setOnAction(e -> showLevelSelectMenu());
        exitButton.setOnAction(e -> primaryStage.close());

        // Arrange buttons in a vertical layout
        VBox menuLayout = new VBox(10, startGameButton, levelSelectButton, exitButton);
        menuLayout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Set up the scene
        Scene menuScene = new Scene(menuLayout, 1300, 700);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    private void startGame() {
        controller.launchGame(); // Start the game from LevelOne
    }
    

    private void showLevelSelectMenu() {
        // Create buttons for selecting levels
        Button levelOneButton = new Button("Level One");
        Button levelSemiButton = new Button("Level Semi");
        Button levelTwoButton = new Button("Level Two");
        Button backButton = new Button("Back to Main Menu");

        // Set up actions for buttons
        levelOneButton.setOnAction(e -> goToLevel("com.example.demo.LevelOne"));
        levelSemiButton.setOnAction(e -> goToLevel("com.example.demo.LevelSemi"));
        levelTwoButton.setOnAction(e -> goToLevel("com.example.demo.LevelTwo"));
        backButton.setOnAction(e -> start(controller.getStage())); // Return to main menu

        // Arrange buttons in a vertical layout
        VBox levelSelectLayout = new VBox(10, levelOneButton, levelSemiButton, levelTwoButton, backButton);
        levelSelectLayout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Set up the scene
        Scene levelSelectScene = new Scene(levelSelectLayout, 1300, 700);
        controller.getStage().setTitle("Level Select");
        controller.getStage().setScene(levelSelectScene);
    }

    private void goToLevel(String levelName) {
        try {
            controller.handleLevelChange(levelName);
        } catch (Exception e) {
            controller.showErrorDialog("Failed to load level: " + levelName, e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
