package com.example.mykpp4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;

import java.io.IOException;


public class HelloApplication extends Application {
    private double y = 0;
    private double x = 0;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("KPP4_GUI.fxml"));
        Parent root = fxmlLoader.load();

        // Створення сцени
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //mouse event
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);

            stage.setOpacity(.8);
        });

        root.setOnMouseReleased((MouseEvent event) -> {
            stage.setOpacity(1);
        });

        stage.setTitle("Hello!");
        stage.initStyle(StageStyle.TRANSPARENT);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}