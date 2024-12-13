package com.example.demo.levels;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.characters.ActiveActorDestructible;
import com.example.demo.characters.FighterPlane;
import com.example.demo.characters.UserPlane;
import com.example.demo.manager.CollisionManager;
import com.example.demo.manager.GameLoop;
import com.example.demo.manager.InputHandler;
import com.example.demo.manager.BackgroundMusicPlayer;

/**
 * The LevelParent class serves as an abstract base class for all game levels.
 * It manages the game loop, actors, and scene updates.
 */
public abstract class LevelParent {
    private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
    private final double screenHeight;
    private final double screenWidth;
    private final double enemyMaximumYPosition;

    private final Group root;
    private final Scene scene;
    private final ImageView background;

    private final UserPlane user;
    private final GameLoop gameLoop;

    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;

    private int currentNumberOfEnemies;
    private LevelView levelView;

    private final StringProperty levelNameProperty = new SimpleStringProperty();
    private Text killCounterText;

    private final CollisionManager collisionManager; // Added collision manager

    private InputHandler inputHandler; // Reference to InputHandler

    /**
     * Constructs a new LevelParent instance with the specified screen height, width, background image, and player initial health.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     * @param backgroundImageName the name of the background image
     * @param playerInitialHealth the initial health of the player
     */
    public LevelParent(double screenHeight, double screenWidth, String backgroundImageName, int playerInitialHealth) {
        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);
        this.user = new UserPlane(playerInitialHealth);

        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();

        this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;

        this.levelView = instantiateLevelView();
        this.currentNumberOfEnemies = 0;

        // Initialize GameLoop with the updateScene method
        this.gameLoop = new GameLoop(this::updateScene);

        // Initialize CollisionManager
        this.collisionManager = new CollisionManager(friendlyUnits, enemyUnits, userProjectiles, enemyProjectiles, user, screenWidth);

        friendlyUnits.add(user);

        // Initialize InputHandler
        inputHandler = new InputHandler(user, this); // Pass LevelParent to InputHandler

		loadCustomFont();
    }
    /**
     * Abstract method to initialize friendly units.
     * This method should be implemented by subclasses to add friendly units to the scene.
     */
    protected abstract void initializeFriendlyUnits();

    /**
     * Abstract method to check if the game is over.
     * This method should be implemented by subclasses to define game over conditions.
     *
     * @return
     */
    protected abstract boolean checkIfGameOver();

    /**
     * Abstract method to spawn enemy units.
     * This method should be implemented by subclasses to spawn enemy units during gameplay.
     */
    protected abstract void spawnEnemyUnits();

    /**
     * Abstract method to instantiate the level view.
     * This method should be implemented by subclasses to create a LevelView instance.
     *
     * @return a new LevelView instance
     */
    protected abstract LevelView instantiateLevelView();

    /**
     * Initializes the scene by setting up the background, friendly units, and UI elements.
     *
     * @return the initialized Scene object
     */
    public Scene initializeScene() {
        initializeBackground();
        initializeFriendlyUnits();
        levelView.showHeartDisplay();

        killCounterText = new Text("Kills: " + user.getNumberOfKills());
        killCounterText.setFont(Font.font("Orbitron", FontWeight.BOLD, 15));
        killCounterText.setFill(Color.DARKRED);
        killCounterText.setTranslateX(10);
        killCounterText.setTranslateY(100);
        root.getChildren().add(killCounterText);

        return scene;
    }

    /**
     * Loads a custom font for the game.
     */
	private void loadCustomFont() {
        try {
            Font.loadFont(getClass().getResourceAsStream("/com/example/demo/font/Orbitron.ttf"), 15);
            System.out.println("Custom font loaded successfully.");
        } catch (Exception e) {
            System.out.println("Failed to load custom font: " + e.getMessage());
        }
    }

    /**
     * Starts the game by starting the game loop.
     */
    public void startGame() {
        gameLoop.start();
    }

    /**
     * Pauses the game by pausing the game loop.
     */
    public void pauseGame() {
        gameLoop.pause();
    }

    /**
     * Stops the game by stopping the game loop.
     */
    public void stopGame() {
        gameLoop.stop();
    }

    /**
     * Transitions to the next level by setting the level name property.
     *
     * @param levelName the name of the next level
     */
    public void goToNextLevel(String levelName) {
        user.destroy();
        levelNameProperty.set(levelName);

       // Ensure background music continues playing
       BackgroundMusicPlayer bgMusic = BackgroundMusicPlayer.getInstance();
       if (bgMusic != null) {
           bgMusic.play();
       }
    }

    /**
     * Gets the name of the current level.
     *
     * @return the name of the current level
     */
    public String getLevelName() {
        return levelNameProperty.get();
    }

    /**
     * Sets the name of the current level.
     *
     * @param levelName the name of the current level
     */
    public void setLevelName(String levelName) {
        levelNameProperty.set(levelName);
    }

    /**
     * Gets the level name property.
     *
     * @return the level name property
     */
    public StringProperty levelNameProperty() {
        return levelNameProperty;
    }

    /**
     * Updates the scene by spawning enemies, updating actors, handling collisions, and checking game over conditions.
     */
    private void updateScene() {
        spawnEnemyUnits();
        updateActors();
        generateEnemyFire();
        updateNumberOfEnemies();
        collisionManager.handleCollisions(); // added CollisionManager for collisions
        removeAllDestroyedActors();
        updateKillCount();
        updateKillCounter();
        updateLevelView();
        checkIfGameOver();
    }

    /**
     * Initializes the background image and sets up key event handlers.
     */
    private void initializeBackground() {
        background.setFocusTraversable(true);
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);
        background.setOnKeyPressed(inputHandler::handleKeyPressed); // Use InputHandler for keyPressed
        background.setOnKeyReleased(inputHandler::handleKeyReleased); // Use InputHandler for keyReleased

        root.getChildren().add(background);
    }

    /**
     * Adds a projectile to the scene and the list of user projectiles.
     *
     * @param projectile the projectile to add
     */
    public void addProjectileToScene(ActiveActorDestructible projectile) {
        root.getChildren().add(projectile); // Add the projectile to the scene
        userProjectiles.add(projectile); // Add to the list of user projectiles
    }

    /**
     * Generates enemy fire by spawning projectiles from enemy units.
     */
    private void generateEnemyFire() {
        enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
    }

    /**
     * Spawns an enemy projectile and adds it to the scene and the list of enemy projectiles.
     *
     * @param projectile the projectile to spawn
     */
    private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            root.getChildren().add(projectile);
            enemyProjectiles.add(projectile);
        }
    }

    /**
     * Updates the state of all actors in the game.
     */
    private void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
    }

    /**
     * Removes all destroyed actors from the scene and their respective lists.
     */
    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
    }

    /**
     * Removes destroyed actors from the specified list and the scene.
     *
     * @param actors the list of actors to remove destroyed actors from
     */
    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream()
                .filter(ActiveActorDestructible::isDestroyed)
                .toList();
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

    /**
     * Updates the kill count by incrementing the user's kill count for each destroyed enemy.
     */
    private void updateKillCount() {
        for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
            user.incrementKillCount();
        }
    }

    /**
     * Updates the kill counter text to display the current number of kills.
     */
    private void updateKillCounter() {
        killCounterText.setText("Kills: " + user.getNumberOfKills());
    }

    /**
     * Updates the level view to reflect the current state of the game.
     */
    private void updateLevelView() {
        levelView.removeHearts(user.getHealth());
    }

    /**
     * Handles the win condition by stopping the game loop and displaying the win image.
     */
    protected void winGame() {
        gameLoop.stop();
        levelView.showWinImage();
    }

    /**
     * Handles the lose condition by stopping the game loop and displaying the game over image.
     */
    protected void loseGame() {
        gameLoop.stop();
        levelView.showGameOverImage();

    }

    /**
     * Gets the user plane.
     *
     * @return the user plane
     */
    protected UserPlane getUser() {
        return user;
    }

    /**
     * Gets the root group of the scene.
     *
     * @return the root group
     */
    protected Group getRoot() {
        return root;
    }

    /**
     * Gets the current number of enemies.
     *
     * @return the current number of enemies
     */
    protected int getCurrentNumberOfEnemies() {
        return enemyUnits.size();
    }

    /**
     * Adds an enemy unit to the scene and the list of enemy units.
     *
     * @param enemy the enemy unit to add
     */
    protected void addEnemyUnit(ActiveActorDestructible enemy) {
        enemyUnits.add(enemy);
        root.getChildren().add(enemy);
    }

    /**
     * Gets the maximum Y position for enemies.
     *
     * @return the maximum Y position for enemies
     */
    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    /**
     * Gets the screen width.
     *
     * @return the screen width
     */
    protected double getScreenWidth() {
        return screenWidth;
    }

    /**
     * Checks if the user plane is destroyed.
     *
     * @return true if the user plane is destroyed, false otherwise
     */
    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }

    /**
     * Updates the number of enemies.
     */
    private void updateNumberOfEnemies() {
        currentNumberOfEnemies = enemyUnits.size();
    }
}
