package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private ImageView lunaImageView;

    @FXML
    private Label loginLabel;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button signupButton;

    @FXML
    private TextField userTextfield;

    @FXML
    private Label invalidPasswordLabel;
    @FXML
    private ImageView instaImage;
    @FXML
    private ImageView telegramImage;


    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File luna = new File("image/luna.jpg");
        Image lunaImage = new Image(luna.toURI().toString());
        lunaImageView.setImage(lunaImage);
        File inst = new File("image/insta.png");
        Image instImage = new Image(inst.toURI().toString());
        instaImage.setImage(instImage);
        File teleg = new File("image/teleg.png");
        Image telegrammInsta = new Image(teleg.toURI().toString());
        telegramImage.setImage(telegrammInsta);
    }
    @FXML
    public void loginButtonOnAction(ActionEvent event) throws IOException {
        String username = userTextfield.getText();
        String password = passwordTextField.getText();

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (isUserCredentialsValid(connection, username, password)) {
                // Credentials are valid, open the next page
                openNextPage();
            } else {
                invalidPasswordLabel.setText("Invalid username or password. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            invalidPasswordLabel.setText("Error checking user credentials.");
        }
    }

    private void openNextPage() throws IOException {
        // Open the next page (replace this with your code to open the desired page)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/Project.fxml"));
        Parent nextPageRoot = loader.load();

        // Create a new stage for the next page
        Stage nextPageStage = new Stage();
        nextPageStage.setTitle("Main Page!");
        nextPageStage.setScene(new Scene(nextPageRoot, 650, 450));

        // Close the current stage
        Stage currentStage = (Stage) loginButton.getScene().getWindow();
        currentStage.close();

        // Show the next page stage
        nextPageStage.show();
    }


    public void signupButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/sign-up.fxml"));
        Parent signUpRoot = loader.load();

        // Create a new stage for SignUpApplication
        Stage signUpStage = new Stage();
        signUpStage.setTitle("Sign Up");
        signUpStage.setScene(new Scene(signUpRoot, 650, 450));

        // Close the LoginApplication stage
        Stage loginStage = (Stage) loginButton.getScene().getWindow();
        loginStage.close();

        // Show the SignUpApplication stage
        signUpStage.show();
    }

    private boolean isUserCredentialsValid(Connection connection, String username, String password) throws SQLException {
        // Check if the username and password match in the database
        String checkCredentialsQuery = "SELECT COUNT(*) FROM registered_user WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkCredentialsQuery)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
