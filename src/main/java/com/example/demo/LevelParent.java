package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        // Add a listener to the levelNameProperty
        levelNameProperty.addListener((observable, oldValue, newValue) ->
            System.out.println("Level name changed from " + oldValue + " to " + newValue)
        );

        friendlyUnits.add(user);
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
        handleEnemyPenetration();
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handlePlaneCollisions();
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
        background.setOnKeyPressed(e -> handleKeyPressed(e));
        background.setOnKeyReleased(e -> handleKeyReleased(e));

        root.getChildren().add(background);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.P) {
                if (gameLoop.isRunning()) {
                    gameLoop.pause();
                    System.out.println("Game Paused");
                } else {
                    gameLoop.start();
                    System.out.println("Game Resumed");
                }
            }
        });
    }

    private void handleKeyPressed(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == KeyCode.W) user.moveUp();
        if (kc == KeyCode.S) user.moveDown();
        if (kc == KeyCode.SPACE) fireProjectile();
    }

    private void handleKeyReleased(KeyEvent e) {
        KeyCode kc = e.getCode();
        if (kc == KeyCode.W || kc == KeyCode.S) user.stop();
    }

    private void fireProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        root.getChildren().add(projectile);
        userProjectiles.add(projectile);
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
                .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

    private void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    private void handleUserProjectileCollisions() {
        handleCollisions(userProjectiles, enemyUnits);
    }

    private void handleEnemyProjectileCollisions() {
        handleCollisions(enemyProjectiles, friendlyUnits);
    }

    private void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                }
            }
        }
    }

    private void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemyHasPenetratedDefenses(enemy)) {
                user.takeDamage();
                enemy.destroy();
            }
        }
    }

    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > screenWidth;
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
