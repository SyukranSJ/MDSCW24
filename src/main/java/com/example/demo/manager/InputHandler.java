package com.example.demo.manager;

import com.example.demo.characters.ActiveActorDestructible;
import com.example.demo.characters.UserPlane;
import com.example.demo.levels.LevelParent;
import com.example.demo.manager.BackgroundMusicPlayer;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputHandler {
    private UserPlane user;
    private LevelParent levelParent; // Reference to LevelParent for adding projectiles
    private boolean isPaused; // Track if the game is paused

    public InputHandler(UserPlane user, LevelParent levelParent) {
        this.user = user;
        this.levelParent = levelParent;
        this.isPaused = false; // Initially, the game is not paused
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused; // Update the pause state from outside
    }

    public void handleKeyPressed(KeyEvent e) {
        KeyCode kc = e.getCode();
        
        if (kc == KeyCode.P) {
            togglePause(); // Toggle pause/resume when 'P' is pressed
        }

        if (isPaused) {
            return; // Ignore input events if the game is paused
        }

        if (kc == KeyCode.W) user.moveUp();
        if (kc == KeyCode.S) user.moveDown();
        if (kc == KeyCode.SPACE) fireProjectile();
    }

    public void handleKeyReleased(KeyEvent e) {
        if (isPaused) {
            return; // Ignore input events if the game is paused
        }

        KeyCode kc = e.getCode();
        if (kc == KeyCode.W || kc == KeyCode.S) user.stop();
    }

    private void fireProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        if (projectile != null) {
            levelParent.addProjectileToScene(projectile); // Add projectile to the scene via LevelParent
        }
    }

    private void togglePause() {
        isPaused = !isPaused; // Toggle the pause state

        if (isPaused) {
            levelParent.pauseGame(); // Pause the game loop when paused
            BackgroundMusicPlayer.getInstance().pause(); 
        } else {
            levelParent.startGame(); // Resume the game loop when unpaused
            BackgroundMusicPlayer.getInstance().resume();
        }
    }
}
