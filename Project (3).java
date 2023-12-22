package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Project implements Initializable {

    FileChooser fileChooser = new FileChooser();
    @FXML
    private Label Logo;
    @FXML
    private ListView<String> fileListView;

    @FXML
    private TextArea textArea;
    @FXML
    private Button saveButton;
    @FXML
    private Pane ProjectPane;
    @FXML
    private Pane TaskPane;
    @FXML
    private Pane TeamPane;
    @FXML
    private Button deleteButton;

    @FXML
    void getFile(MouseEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            try {
                loadFileIntoTextArea(file);
                saveButton.setVisible(true); // Make Save button visible
                deleteButton.setVisible(true); // Make Delete button visible
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void openSelectedFile(MouseEvent event) {
        String selectedItem = fileListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && !selectedItem.equals("Add New")) {
            File selectedFile = new File("C:\\IJI\\demo\\Structure", selectedItem);

            try {
                loadFileIntoTextArea(selectedFile);
                saveButton.setVisible(true); // Make Save button visible
                deleteButton.setVisible(true); // Make Delete button visible
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (selectedItem != null && selectedItem.equals("Add New")) {
            addNewFile();
        }
    }

    @FXML
    void saveFile(MouseEvent event) {
        String selectedFileName = fileListView.getSelectionModel().getSelectedItem();
        if (selectedFileName != null && !selectedFileName.equals("Add New")) {
            File selectedFile = new File("C:\\IJI\\demo\\Structure", selectedFileName);
            try {
                saveTextAreaContentToFile(selectedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void deleteFile(MouseEvent event) {
        String selectedFileName = fileListView.getSelectionModel().getSelectedItem();
        if (selectedFileName != null && !selectedFileName.equals("Add New")) {
            File selectedFile = new File("C:\\IJI\\demo\\Structure", selectedFileName);

            // Close the TextArea before deletion
            textArea.clear();

            // Attempt to delete the file using Files.delete
            Path filePath = Paths.get(selectedFile.toURI());
            try {
                Files.delete(filePath);
                System.out.println("File deleted successfully: " + selectedFileName);

// File deletion successful, update the ListView
                updateFileListView();
                saveButton.setVisible(true); // Hide Save button after deletion
                deleteButton.setVisible(true); // Hide Delete button after deletion
            } catch (Exception e) {
                // File deletion failed
                System.out.println("Failed to delete the file: " + selectedFileName);
                e.printStackTrace(); // Print the exception for further analysis
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileChooser.setInitialDirectory(new File("C:\\IJI\\demo\\Structure"));
        textArea.setVisible(true); // Set TextArea initially invisible
        saveButton.setVisible(true); // Set Save button initially invisible
        deleteButton.setVisible(true); // Set Delete button initially invisible


        // Populate the ListView with file names from the specified directory
        updateFileListView();
    }

    private void loadFileIntoTextArea(File file) throws FileNotFoundException {
        try (Scanner scanner = new Scanner(file)) {
            StringBuilder content = new StringBuilder();
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
            textArea.setText(content.toString());
            textArea.setVisible(true); // Show TextArea after loading the file
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void saveTextAreaContentToFile(File file) throws IOException {
        String content = textArea.getText();
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
        }
    }

    private void addNewFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\IJI\\demo\\Structure"));
        File newFile = fileChooser.showOpenDialog(new Stage());

        if (newFile != null) {
            // Copy the selected file to the application directory
            File destFile = new File("C:\\IJI\\demo\\Structure", newFile.getName());
            try {
                Files.copy(newFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                updateFileListView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateFileListView() {
        File folder = new File("C:\\IJI\\demo\\Structure");
        File[] files = folder.listFiles();
        if (files != null) {
            ObservableList<String> fileNames = FXCollections.observableArrayList();
            for (File file : files) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
            fileNames.add("Add New");
            fileListView.setItems(fileNames);
        }
    }
    String current ="task";
    @FXML
    void ProjectPaneButton(MouseEvent event) {
        TaskPane.setVisible(false);
        TeamPane.setVisible(false);
        ProjectPane.setVisible( true);
    }
    @FXML
    void TaskPaneButton(MouseEvent event) {
        TeamPane.setVisible(false);
        ProjectPane.setVisible( false);
        TaskPane.setVisible(true);
    }

    @FXML
    void TeamPaneButton(MouseEvent event) {
        ProjectPane.setVisible( false);
        TaskPane.setVisible(false);
        TeamPane.setVisible(true);
    }


}