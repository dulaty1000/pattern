package com.example.demo;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DropboxExample extends Application {

    private static final String ACCESS_TOKEN = "sl.BsPA_dNbKH_Z67Rb-jK1HDUZN9VOltrL61RbpKs5I6vwt2tJZN7GJSpneKMgV2hbosEiHtfQLEuMd_ifCd46mloGdhxuwAhtri-wz8MY_LfJBI5hn4viIXRt8HyPmfsNBETnCosfb6JD";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize Dropbox
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        // Example usage
        try {
            // Upload a file using JavaFX FileChooser
            File chosenFile = chooseFile(primaryStage);
            if (chosenFile != null) {


                // Download a file (optional)
                downloadFile(client, "/dropbox_folder/chushpan.txt", "downloaded_file.txt");

                // Delete the uploaded file
                deleteFile(client, "/dropbox_folder/chushpan.txt");
            }
        } catch (IOException | DbxException e) {
            e.printStackTrace();
        }
    }

    private File chooseFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file to upload");
        return fileChooser.showOpenDialog(primaryStage);
    }

    private void uploadFile(DbxClientV2 client, File localFile, String dropboxPath, Stage primaryStage)
            throws IOException, DbxException {
        File chosenFile = chooseFile(primaryStage);
        if (localFile != null) {
            try (InputStream in = new FileInputStream(localFile)) {
                FileMetadata metadata = client.files().uploadBuilder(dropboxPath)
                        .uploadAndFinish(in);
                System.out.println("File uploaded successfully: " + metadata.getName());
            }
        }
    }

    private void downloadFile(DbxClientV2 client, String dropboxPath, String localFilePath)
            throws IOException, DbxException {
        try (InputStream in = client.files().download(dropboxPath).getInputStream()) {
            // Implement logic to save the downloaded content to a local file
            // For simplicity, we are not saving the content in this example
            System.out.println("File downloaded successfully: " + localFilePath);
        }
    }

    private void deleteFile(DbxClientV2 client, String dropboxPath) throws DbxException {
        client.files().delete(dropboxPath);
        System.out.println("File deleted successfully: " + dropboxPath);
    }
}
