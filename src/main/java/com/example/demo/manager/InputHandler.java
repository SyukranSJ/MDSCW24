package com.example.demo.manager;

import com.example.demo.characters.ActiveActorDestructible;
import com.example.demo.characters.UserPlane;
import com.example.demo.levels.LevelParent;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * The InputHandler class handles keyboard input for the game.
 * It manages user plane movements, firing projectiles, and pausing/resuming the game.
 */
public class InputHandler {
    private UserPlane user;
    private LevelParent levelParent; // Reference to LevelParent for adding projectiles
    private boolean isPaused; // Track if the game is paused

    /**
     * Constructs a new InputHandler instance with the specified user plane and level parent.
     *
     * @param user the user plane
     * @param levelParent the level parent
     */
    public InputHandler(UserPlane user, LevelParent levelParent) {
        this.user = user;
        this.levelParent = levelParent;
        this.isPaused = false; // Initially, the game is not paused
    }

    /**
     * Sets the paused state of the game.
     *
     * @param paused the new paused state
     */
    public void setPaused(boolean paused) {
        this.isPaused = paused; // Update the pause state from outside
    }

    /**
     * Handles key pressed events.
     * Manages user plane movements, firing projectiles, and toggling pause/resume.
     *
     * @param e the key event
     */
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

    /**
     * Handles key released events.
     * Manages stopping the user plane movements.
     *
     * @param e the key event
     */
    public void handleKeyReleased(KeyEvent e) {
        if (isPaused) {
            return; // Ignore input events if the game is paused
        }

        KeyCode kc = e.getCode();
        if (kc == KeyCode.W || kc == KeyCode.S) user.stop();
    }

    /**
     * Fires a projectile from the user plane and adds it to the scene.
     */
    private void fireProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        if (projectile != null) {
            levelParent.addProjectileToScene(projectile); // Add projectile to the scene via LevelParent
        }
    }

    /**
     * Toggles the paused state of the game.
     * Pauses or resumes the game loop and background music.
     */
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
