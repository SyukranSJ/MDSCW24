module com.example.demo {

    requires javafx.controls;

    requires javafx.fxml;
    requires transitive javafx.base;
    requires javafx.media;
    requires org.junit.jupiter.api;
    requires org.testfx.junit5;
    requires org.junit.platform.commons;

    requires transitive javafx.graphics;


    opens com.example.demo.characters to javafx.fxml, org.junit.jupiter.api, org.junit.platform.commons;
    opens com.example.demo.levels to javafx.fxml, org.junit.jupiter.api, org.junit.platform.commons;
    opens com.example.demo.manager to javafx.fxml, org.junit.jupiter.api, org.junit.platform.commons;
    opens com.example.demo.UI to javafx.fxml, org.junit.jupiter.api, org.junit.platform.commons;

    exports com.example.demo.controller;
}