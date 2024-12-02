package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameLoop {
    private final Timeline timeline;
    private final Runnable updateTask;

    private static final int DEFAULT_FRAME_DELAY = 50; // Milliseconds

    public GameLoop(Runnable updateTask) {
        this(updateTask, DEFAULT_FRAME_DELAY);
    }

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
