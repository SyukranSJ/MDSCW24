package com.example.demo.controller;

import javafx.application.Application;

/**
 * Main class for launching the application.
 * This class initializes the JavaFX application by invoking the MainMenu class.
 */
public class Main {
    /**
     * The entry point of the application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        Application.launch(MainMenu.class, args); // Launch the MainMenu
    }
}
