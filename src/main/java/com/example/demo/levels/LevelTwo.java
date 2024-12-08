package com.example.demo.levels;

import com.example.demo.characters.Boss;

public class LevelTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private final Boss boss;
    
   // private LevelView levelView; // Corrected to LevelView instead of LevelViewLevelTwo

    public LevelTwo(double screenHeight, double screenWidth) {
        super(screenHeight, screenWidth, BACKGROUND_IMAGE_NAME, PLAYER_INITIAL_HEALTH);
        boss = createBoss();
    }

    protected Boss createBoss() {
        return new Boss(); // Customize initialization of Boss
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
            addBossToScene();
        }
    }

    private void addBossToScene() {
        addEnemyUnit(boss);
        getRoot().getChildren().addAll(boss.getHealthBar(), boss.getShieldImage());
    }


    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
    }

}

