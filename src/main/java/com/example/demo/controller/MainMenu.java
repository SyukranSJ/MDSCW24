package com.example.demo.controller;

import com.example.demo.manager.BackgroundMusicPlayer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.control.Label;



/**
 * The MainMenu class represents the main menu of the game.
 * It initializes the main menu scene with background music and buttons to start the game, select levels, view instructions,
 * and exit the game. The MainMenu interacts with the Controller to handle game transitions and manages background
 * images and UI elements like buttons and text.
 */
public class MainMenu extends Application {

    private Controller controller;
    private BackgroundMusicPlayer bgMusic;

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned, and after the system is ready for the application to begin running.
     *
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     */
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
        Button howToPlayButton = new Button("How to Play");       
        Button exitButton = new Button("Exit");

        // Set up button actions
        startGameButton.setOnAction(e -> startGame());
        levelSelectButton.setOnAction(e -> showLevelSelectMenu());
        exitButton.setOnAction(e -> {
            bgMusic.stop();
            primaryStage.close();
        });
        howToPlayButton.setOnAction(e -> showHowToPlayPage(primaryStage)); 

       
        
        // Music Control Label
        Label musicControlLabel = new Label("Music Control");
        musicControlLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");
        
        // Volume Slider
        Slider volumeSlider = new Slider(0, 1, bgMusic.getVolume());
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(0.25);
        volumeSlider.setBlockIncrement(0.1);
        
        // Force smaller size
        volumeSlider.setPrefWidth(100);
        volumeSlider.setMinWidth(100);
        volumeSlider.setMaxWidth(100);
        
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            bgMusic.setVolume(newValue.doubleValue());
        });
        
        // Combine Label and Slider in a VBox
        VBox musicControlBox = new VBox(10, musicControlLabel, volumeSlider);
        musicControlBox.setAlignment(Pos.CENTER);
        musicControlBox.setStyle("-fx-padding: 10;");

        VBox menuLayout = new VBox(25, startGameButton, levelSelectButton,howToPlayButton,volumeSlider, exitButton);
        menuLayout.setStyle("-fx-alignment: center; -fx-padding: 350 0 0 0;");

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

    /**
     * Starts the game by launching the first level.
     */
    private void startGame() {
        controller.launchGame(); 
    }

    /**
     * Displays the level selection menu.
     * Allows the user to choose between different levels.
     */
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

        VBox levelSelectLayout = new VBox(28, levelOneButton, levelSemiButton, levelTwoButton, backButton);
        levelSelectLayout.setStyle("-fx-alignment: center; -fx-padding: 350 0 0 0;");

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

    /**
     * Displays the "How to Play" page.
     * Provides instructions on how to play the game.
     *
     * @param primaryStage the primary stage for this application, onto which the application scene can be set.
     */
    private void showHowToPlayPage(Stage primaryStage) {
        
        Image spaceKeyImage = new Image(getClass().getResourceAsStream("/com/example/demo/images/Space.png"));
        Image wKeyImage = new Image(getClass().getResourceAsStream("/com/example/demo/images/W.png"));
        Image sKeyImage = new Image(getClass().getResourceAsStream("/com/example/demo/images/S.png"));
        Image pKeyImage = new Image(getClass().getResourceAsStream("/com/example/demo/images/p.png"));
    
        ImageView spaceKeyView = new ImageView(spaceKeyImage);
        ImageView wKeyView = new ImageView(wKeyImage);
        ImageView sKeyView = new ImageView(sKeyImage);
        ImageView pKeyView = new ImageView(pKeyImage);
    
        double keySize = 50;
        spaceKeyView.setFitWidth(keySize);
        spaceKeyView.setFitHeight(keySize);
        wKeyView.setFitWidth(keySize);
        wKeyView.setFitHeight(keySize);
        sKeyView.setFitWidth(keySize);
        sKeyView.setFitHeight(keySize);
        pKeyView.setFitWidth(keySize);
        pKeyView.setFitHeight(keySize);
    
        Text spaceText = new Text("Spacebar - Shoot");
        Text wText = new Text("W - Move Up");
        Text sText = new Text("S - Move Down");
        Text pText = new Text("P - Pause");
    
        HBox spaceBox = new HBox(10, spaceKeyView, spaceText);
        spaceBox.setAlignment(Pos.CENTER);
    
        HBox wBox = new HBox(10, wKeyView, wText);
        wBox.setAlignment(Pos.CENTER);
    
        HBox sBox = new HBox(10, sKeyView, sText);
        sBox.setAlignment(Pos.CENTER);
    
        HBox pBox = new HBox(10, pKeyView, pText);
        pBox.setAlignment(Pos.CENTER);
    
        // Create the Back Button
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> start(controller.getStage()));  // Go back to Main Menu
    
        // Combine everything into the main layout
        HBox mainLayout = new HBox(20, wBox, sBox);
        mainLayout.setAlignment(Pos.CENTER);
        HBox secondLayout = new HBox(20, spaceBox,pBox);
        secondLayout.setAlignment(Pos.CENTER);
        VBox mainLayout2 = new VBox(10 ,backButton);
        mainLayout2.setAlignment(Pos.CENTER);

        VBox combinedLayout = new VBox(20, mainLayout, secondLayout,mainLayout2);
        combinedLayout.setAlignment(Pos.CENTER);
    
        Scene howToPlayScene = new Scene(combinedLayout, 1300, 700);
    
        primaryStage.setTitle("How to Play");
        primaryStage.setScene(howToPlayScene);
        primaryStage.show();    
    }   

    /**
     * Handles the transition to a specified level.
     *
     * @param levelName the fully qualified name of the level class to transition to.
     */
    private void goToLevel(String levelName) {
        try {
            controller.handleLevelChange(levelName);
        } catch (Exception e) {
            controller.showErrorDialog("Failed to load level: " + levelName, e);
        }
    }

    /**
     * Stops the background music when the application is stopped.
     *
     * @throws Exception if an error occurs during stopping.
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        if (bgMusic != null) {
            bgMusic.stop();
        }
    }
    /**
     * The main method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be launched through deployment artifacts, e.g., in IDEs with limited FX support.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}