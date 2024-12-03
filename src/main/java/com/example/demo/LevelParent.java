package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

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
    }

    protected abstract void initializeFriendlyUnits();

    protected abstract void checkIfGameOver();

    protected abstract void spawnEnemyUnits();

    protected abstract LevelView instantiateLevelView();

    public Scene initializeScene() {
        initializeBackground();
        initializeFriendlyUnits();
        levelView.showHeartDisplay();

        killCounterText = new Text("Kills: " + user.getNumberOfKills());
        killCounterText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        killCounterText.setFill(Color.BLACK);
        killCounterText.setTranslateX(10);
        killCounterText.setTranslateY(100);
        root.getChildren().add(killCounterText);

        return scene;
    }

    public void startGame() {
        gameLoop.start();
    }

    public void pauseGame() {
        gameLoop.pause();
    }

    public void stopGame() {
        gameLoop.stop();
    }

    public void goToNextLevel(String levelName) {
        user.destroy();
        levelNameProperty.set(levelName);
    }

    public String getLevelName() {
        return levelNameProperty.get();
    }

    public void setLevelName(String levelName) {
        levelNameProperty.set(levelName);
    }

    public StringProperty levelNameProperty() {
        return levelNameProperty;
    }

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

    private void initializeBackground() {
        background.setFocusTraversable(true);
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);
        background.setOnKeyPressed(inputHandler::handleKeyPressed); // Use InputHandler for keyPressed
        background.setOnKeyReleased(inputHandler::handleKeyReleased); // Use InputHandler for keyReleased

        root.getChildren().add(background);
    }

    public void addProjectileToScene(ActiveActorDestructible projectile) {
        root.getChildren().add(projectile); // Add the projectile to the scene
        userProjectiles.add(projectile); // Add to the list of user projectiles
    }

    private void generateEnemyFire() {
        enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
    }

    private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            root.getChildren().add(projectile);
            enemyProjectiles.add(projectile);
        }
    }

    private void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
    }

    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
    }

    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream()
                .filter(ActiveActorDestructible::isDestroyed)
                .toList();
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

    private void updateKillCount() {
        for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
            user.incrementKillCount();
        }
    }

    private void updateKillCounter() {
        killCounterText.setText("Kills: " + user.getNumberOfKills());
    }

    private void updateLevelView() {
        levelView.removeHearts(user.getHealth());
    }

    protected void winGame() {
        gameLoop.stop();
        levelView.showWinImage();
    }

    protected void loseGame() {
        gameLoop.stop();
        levelView.showGameOverImage();
    }

    protected UserPlane getUser() {
        return user;
    }

    protected Group getRoot() {
        return root;
    }

    protected int getCurrentNumberOfEnemies() {
        return enemyUnits.size();
    }

    protected void addEnemyUnit(ActiveActorDestructible enemy) {
        enemyUnits.add(enemy);
        root.getChildren().add(enemy);
    }

    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    protected double getScreenWidth() {
        return screenWidth;
    }

    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }

    private void updateNumberOfEnemies() {
        currentNumberOfEnemies = enemyUnits.size();
    }
}
