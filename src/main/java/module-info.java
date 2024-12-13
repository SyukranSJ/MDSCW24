module com.example.demo {

    requires javafx.controls;

    requires javafx.fxml;
    requires transitive javafx.base;
    requires javafx.media;

    requires transitive javafx.graphics;
    opens com.example.demo.UI to javafx.fxml;
    opens com.example.demo.characters to javafx.fxml;
    opens com.example.demo.controller to javafx.fxml;
    opens com.example.demo.levels to javafx.fxml;
    opens com.example.demo.manager to javafx.fxml;

    exports com.example.demo.controller;
}