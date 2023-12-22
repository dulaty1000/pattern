package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private ImageView workImageView;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button cancelButton;
    @FXML
    private Label emptyField;

    private Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File butterfly = new File("@../../../../../../image/butterfly.jpg");
        Image butterflyImage = new Image(butterfly.toURI().toString());
        workImageView.setImage(butterflyImage);
    }
    @FXML
    public void signupButtonOnAction(ActionEvent event) throws IOException {
        if (firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty() ||
                usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()) {
            emptyField.setText("Please enter your information");
        } else {
            try (Connection connection = DatabaseConnection.getConnection()) {
                // Check if the username already exists
                String username = usernameTextField.getText();
                if (isUsernameExists(connection, username)) {
                    emptyField.setText("Username already exists. Please choose another username.");
                } else {
                    // Insert user information into the registered_user table
                    insertUser(connection);
                    emptyField.setText("Your information saved");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Project.fxml"));
                    Parent loginRoot = loader.load();

                    // Create a new stage for SignUpApplication
                    Stage loginStage = new Stage();
                    loginStage.setTitle("Main Page!");
                    loginStage.setScene(new Scene(loginRoot, 650, 450));

                    // Close the LoginApplication stage
                    Stage signUpstage = (Stage) signUpButton.getScene().getWindow();
                    signUpstage.close();

                    // Show the SignUpApplication stage
                    loginStage.show();

                    // You can open the main application window here if needed
                }
            } catch (SQLException e) {
                e.printStackTrace();
                emptyField.setText("Error saving user information.");
                System.out.println("Error saving user information.");
            }
        }
    }
    private boolean isUsernameExists(Connection connection, String username) throws SQLException {
        // Check if the username already exists in the database
        String checkUsernameQuery = "SELECT COUNT(*) FROM registered_user WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkUsernameQuery)) {
            preparedStatement.setString(1, username);
            try (var resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        }
    }

        // Insert user information into the registered_user table
        private void insertUser(Connection connection) throws SQLException {
            // Insert user information into the registered_user table
            String insertUserQuery = "INSERT INTO registered_user (firstname, lastname, username, password) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertUserQuery)) {
                preparedStatement.setString(1, firstNameTextField.getText());
                preparedStatement.setString(2, lastNameTextField.getText());
                preparedStatement.setString(3, usernameTextField.getText());
                preparedStatement.setString(4, passwordTextField.getText());
                preparedStatement.executeUpdate();
            }
        }



    public void cancelButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/login.fxml"));
        Parent loginRoot = loader.load();

        // Create a new stage for SignUpApplication
        Stage loginStage = new Stage();
        loginStage.setTitle("Login Page!");
        loginStage.setScene(new Scene(loginRoot, 650, 450));

        // Close the LoginApplication stage
        Stage signUpstage = (Stage) cancelButton.getScene().getWindow();
        signUpstage.close();

        // Show the SignUpApplication stage
        loginStage.show();
    }

    static class User {
        private String firstName;
        private String lastName;
        private String username;
        private String password;

        public User(String firstName, String lastName, String username, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.password = password;
        }

        @Override
        public String toString() {
            // Format user information for writing to the file
            return String.format("\nFirst name: %s\n" + "Last name: %s\n" + "Username: %s\n" + "Password: %s", firstName, lastName, username, password);
        }
    }
}
