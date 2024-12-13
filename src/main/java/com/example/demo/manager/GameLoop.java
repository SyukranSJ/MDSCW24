package com.example.demo.manager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * The GameLoop class manages the main game loop using JavaFX's Timeline.
 * It handles starting, pausing, and stopping the game loop.
 */
public class GameLoop {
    private final Timeline timeline;
    //currently unused but may be utilize in the upcoming features.
    private final Runnable updateTask;

    private static final int DEFAULT_FRAME_DELAY = 50; // Milliseconds

    /**
     * Constructs a new GameLoop instance with the specified update task and default frame delay.
     *
     * @param updateTask the task to run on each frame update
     */
    public GameLoop(Runnable updateTask) {
        this(updateTask, DEFAULT_FRAME_DELAY);
    }

    /**
     * Constructs a new GameLoop instance with the specified update task and frame delay.
     *
     * @param updateTask the task to run on each frame update
     * @param frameDelay the delay between frames in milliseconds
     */
    public GameLoop(Runnable updateTask, int frameDelay) {
        this.updateTask = updateTask;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(frameDelay), e -> updateTask.run()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Starts or resumes the game loop.
     */
    public void start() {
        if (timeline.getStatus() != Timeline.Status.RUNNING) {
            timeline.play();
        }
    }

    /**
     * Pauses the game loop.
     */
    public void pause() {
        if (timeline.getStatus() == Timeline.Status.RUNNING) {
            timeline.pause();
        }
    }

    /**
     * Stops the game loop.
     */
    public void stop() {
        timeline.stop();
    }

    /**
     * Checks if the game loop is currently running.
     *
     * @return true if running, false otherwise.
     */
    public boolean isRunning() {
        return timeline.getStatus() == Timeline.Status.RUNNING;
    }
}
