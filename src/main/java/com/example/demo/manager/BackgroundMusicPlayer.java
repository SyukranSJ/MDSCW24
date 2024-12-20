package com.example.demo.manager;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * The BackgroundMusicPlayer class manages the background music for the game.
 * It follows the Singleton pattern to ensure only one instance of the music player exists.
 */
public class BackgroundMusicPlayer {


    private static BackgroundMusicPlayer instance;
    private MediaPlayer mediaPlayer;

    /**
     * Private constructor to enforce Singleton pattern.
     * Initializes the MediaPlayer with the specified audio file and sets it to loop indefinitely.
     *
     * @param audioPath the path to the audio file
     */
    private BackgroundMusicPlayer(String audioPath) {
        try {
            Media backgroundMusic = new Media(BackgroundMusicPlayer.class.getResource(audioPath).toExternalForm());
            mediaPlayer = new MediaPlayer(backgroundMusic);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);  // Play music in a loop
        } catch (Exception e) {
            System.out.println("Audio file not found: " + audioPath);
        }
    }

   /**
     * Gets the singleton instance of the BackgroundMusicPlayer.
     * If the instance does not exist, it creates a new one with the default audio file.
     *
     * @return the singleton instance of the BackgroundMusicPlayer
     */
    public static BackgroundMusicPlayer getInstance() {
        if (instance == null) {
            instance = new BackgroundMusicPlayer("/com/example/demo/audio/Madeon_Finale.mp3");
        }
        return instance;
    }

    /**
     * Sets the volume of the background music.
     * The volume should be a value between 0.0 (mute) and 1.0 (maximum volume).
     *
     * @param volume the desired volume level
     */
    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    /**
     * Gets the current volume of the background music.
     *
     * @return the current volume level between 0.0 and 1.0
     */
    public double getVolume() {
        if (mediaPlayer != null) {
            return mediaPlayer.getVolume();
        }
        return 0.0;
    }

    /**
     * Plays the background music.
     */
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    /**
     * Pauses the background music.
     */
    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    /**
     * Resumes the background music.
     */
    public void resume() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    /**
     * Stops the background music.
     */
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    /**
     * Checks if the background music is currently playing.
     *
     * @return true if the music is playing, false otherwise
     */
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }
}
