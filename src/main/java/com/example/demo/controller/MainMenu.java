package com.example.demo.controller;

import com.example.demo.manager.BackgroundMusicPlayer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application {

    private Controller controller;
    private BackgroundMusicPlayer bgMusic;

    @Override
    public void start(Stage primaryStage) {
        controller = new Controller(primaryStage);

        bgMusic = BackgroundMusicPlayer.getInstance();
        bgMusic.play();

       // Load the background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/demo/images/MainMenuImage3.png"));
        ImageView backgroundView = new ImageView(backgroundImage);

        // Make the background image stretch to fit the window size without blurring
        backgroundView.setPreserveRatio(false);
        backgroundView.setSmooth(false);  // Prevent scaling interpolation artifacts
        backgroundView.setCache(true);

        // Create buttons for the menu
        Button startGameButton = new Button("Start Game");
        Button levelSelectButton = new Button("Level Select");
        Button exitButton = new Button("Exit");

        // Set up button actions
        startGameButton.setOnAction(e -> startGame());
        levelSelectButton.setOnAction(e -> showLevelSelectMenu());
        exitButton.setOnAction(e -> {
            bgMusic.stop();
            primaryStage.close();
        });

        VBox menuLayout = new VBox(10, startGameButton, levelSelectButton, exitButton);
        menuLayout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Use StackPane to layer the background and buttons
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundView, menuLayout);

        // Bind the background view size to the scene size
        root.widthProperty().addListener((obs, oldVal, newVal) -> backgroundView.setFitWidth((double) newVal));
        root.heightProperty().addListener((obs, oldVal, newVal) -> backgroundView.setFitHeight((double) newVal));

        // Set up the scene
        Scene menuScene = new Scene(root, 1300, 700);
        primaryStage.setTitle("Main Menu");

        // Prevent resizing and full-screen capabilities
        primaryStage.setResizable(false);
        primaryStage.setScene(menuScene);

        primaryStage.show();
    }

    private void startGame() {
        controller.launchGame(); 
    }

    private void showLevelSelectMenu() {
        Image backgroundImage = new Image(getClass().getResourceAsStream("/com/example/demo/images/MainMenuImage3.png"));
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setPreserveRatio(false);
        backgroundView.setSmooth(true);
        backgroundView.setCache(true);

        Button levelOneButton = new Button("Level One");
        Button levelSemiButton = new Button("Level Semi");
        Button levelTwoButton = new Button("Level Two");
        Button backButton = new Button("Back to Main Menu");

        levelOneButton.setOnAction(e -> goToLevel("com.example.demo.LevelOne"));
        levelSemiButton.setOnAction(e -> goToLevel("com.example.demo.LevelSemi"));
        levelTwoButton.setOnAction(e -> goToLevel("com.example.demo.LevelTwo"));
        backButton.setOnAction(e -> start(controller.getStage()));

        VBox levelSelectLayout = new VBox(10, levelOneButton, levelSemiButton, levelTwoButton, backButton);
        levelSelectLayout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundView, levelSelectLayout);

        root.widthProperty().addListener((obs, oldVal, newVal) -> backgroundView.setFitWidth((double) newVal));
        root.heightProperty().addListener((obs, oldVal, newVal) -> backgroundView.setFitHeight((double) newVal));

        Scene levelSelectScene = new Scene(root, 1300, 700);
        controller.getStage().setTitle("Level Select");
        controller.getStage().setScene(levelSelectScene);

        // Prevent resizing and full-screen capabilities
        controller.getStage().setResizable(false);
    }

    private void goToLevel(String levelName) {
        try {
            controller.handleLevelChange(levelName);
        } catch (Exception e) {
            controller.showErrorDialog("Failed to load level: " + levelName, e);
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (bgMusic != null) {
            bgMusic.stop();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
