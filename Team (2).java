package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Team {
    @FXML
    private Button taskButton;
    @FXML
    private Button projectButton;

    @FXML
    public void switchToTeam(MouseEvent event) {

    }
    @FXML
    public void switchToTask(MouseEvent event) {
        loadFXML("task.fxml");
    }
    @FXML
    public void switchToProject(MouseEvent event) {
        loadFXML("Project.fxml");
    }
    public void loadFXML(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setMaximized(true);
            stage.setScene(new Scene(root));
            Stage taskStage = (Stage) taskButton.getScene().getWindow();
            taskStage.close();
            Stage projectStage = (Stage) projectButton.getScene().getWindow();
            projectStage.close();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
