package com.example.demo;

public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private final Boss boss;
    private LevelView levelView; // Corrected to LevelView instead of LevelViewLevelTwo

    public LevelTwo(double screenHeight, double screenWidth) {
        super(screenHeight, screenWidth, BACKGROUND_IMAGE_NAME, PLAYER_INITIAL_HEALTH);
        boss = new Boss();
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            winGame();
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            addEnemyUnit(boss);
            //getRoot().getChildren().add(boss.getHealthBar()); // Add health bar to the scene
            //getRoot().getChildren().add(boss.getShield()); // Add shield bar to the scene
            
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
        return levelView;
    }
}

