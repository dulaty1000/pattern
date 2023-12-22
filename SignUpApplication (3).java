package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class SignUpApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent fxmlLoader =  FXMLLoader.load(getClass().getResource("/com/example/demo/sign-up.fxml"));
        Scene scene = new Scene(fxmlLoader, 650, 450);
        stage.setTitle("Sign Up!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}