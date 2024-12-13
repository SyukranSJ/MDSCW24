package com.example.demo.manager;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BackgroundMusicPlayer {

    private static BackgroundMusicPlayer instance;
    private MediaPlayer mediaPlayer;

    // Private constructor to enforce Singleton pattern
    private BackgroundMusicPlayer(String audioPath) {
        try {
            Media backgroundMusic = new Media(BackgroundMusicPlayer.class.getResource(audioPath).toExternalForm());
            mediaPlayer = new MediaPlayer(backgroundMusic);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);  // Play music in a loop
        } catch (Exception e) {
            System.out.println("Audio file not found: " + audioPath);
        }
    }

    // Singleton instance getter
    public static BackgroundMusicPlayer getInstance() {
        if (instance == null) {
            instance = new BackgroundMusicPlayer("/com/example/demo/audio/Madeon_Finale.mp3");
        }
        return instance;
    }

    // Play music
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    // Pause music
    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    // Resume music
    public void resume() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    // Stop music
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    // Check if the music is playing
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }
}
