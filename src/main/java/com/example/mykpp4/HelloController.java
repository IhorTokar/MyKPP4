package com.example.mykpp4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HelloController {
    @FXML
    private Button Lab1Btn;

    @FXML
    private Button Lab2Btn;

    @FXML
    private Button close;

    @FXML
    private AnchorPane main_form;

    //DATABASE TOOLS
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    double x = 0;
    double y = 0;

    public void switchScene(String fxmlFile) {
        try {
            Stage currentStage = (Stage) main_form.getScene().getWindow();
            currentStage.close();

            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
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

            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Подія для кнопки "Lab 1"
    public void Lab1Btnevent() {
        switchScene("Lab4_1.fxml");
    }

    // Подія для кнопки "Lab 2"
    public void Lab2Btnevent() {
        switchScene("Lab4_2.fxml");
    }

    public void Close(){
        System.exit(0);
    }
}