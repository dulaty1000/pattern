package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Task implements Switch{
    @FXML
    private Button teamButton;
    @FXML
    private Button projectButton;

    @FXML
    public void switchToTask(MouseEvent event) {

    }

    @FXML
    public void switchToProject(MouseEvent event) {
        loadFXML("Project.fxml");
    }

    @FXML
    public void switchToTeam(MouseEvent event) {

        loadFXML("Team.fxml");
    }
    public void loadFXML(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setMaximized(true);
            stage.setScene(new Scene(root));
            Stage projectStage = (Stage) projectButton.getScene().getWindow();
            projectStage.close();
            Stage teamStage = (Stage) teamButton.getScene().getWindow();
            teamStage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
