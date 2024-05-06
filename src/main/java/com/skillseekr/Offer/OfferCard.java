package com.skillseekr.Offer;

import com.skillseekr.Models.Offers.Offer;
import com.skillseekr.Models.Offers.Skill;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane; // Import DialogPane
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OfferCard {

    @FXML
    private Label titleLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label skillsLabel;

    @FXML
    private Button btnDesc;

    @FXML
    private Button btnApply;

    @FXML
    private Button btnDownload; // New download button

    private Offer offer;

    public void setOfferDetails(Offer offer) {
        this.offer = offer; // Store the offer data

        titleLabel.setText(offer.getTitle());
        dateLabel.setText(offer.getCreated_at().toString()); // Assuming created_at is a Date object
        authorLabel.setText(offer.getAuthor());

        // Display list of skills
        StringBuilder skillsText = new StringBuilder();
        for (Skill skill : offer.getSkills()) {
            skillsText.append(skill.getSkill()).append(", ");
        }
        // Remove the last comma and space
        String skillsString = skillsText.substring(0, skillsText.length() - 2);
        skillsLabel.setText(skillsString);

        // Set action for "View More" button to show alert with offer description
        btnDesc.setOnAction(event -> {
            String offerDetails = "Description: " + offer.getDescription() + "\n\n" +
                    "Motive: "  + offer.getMotive()  + "\n" +
                    "Location: "  + offer.getLocation()  + "\n" +
                    "Type: " +  offer.getType() ;

            showAlertWithHTML("Offer Details", offerDetails);
        });

        btnDownload.setOnAction((event) -> {
            if (offer.getFile_name() == null) {
                // Show alert error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No attached file to download for this offer");
                alert.showAndWait();
            } else {
                try {
                    downloadMatchingFile(offer.getFile_name(), getMatchingDownloadUrl(offer.getFile_name()));
                    // Show success message to user
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Offer downloaded successfully.");
                    alert.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                    // Show error message to user
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Failed to download offer. Please try again later.");
                    alert.showAndWait();
                }
            }
        });
    }

    private static String getMatchingDownloadUrl(String fileName) {
        String filePath = new File("src/main/resources/Public/" + fileName).toURI().toString();
        return filePath;
    }

    public static void downloadMatchingFile(String fileName, String downloadUrl) throws IOException {
        // Check if downloaded file already exists (optional)
        File downloadedFile = new File(System.getProperty("user.home") + "/Downloads/SkillSeekrDD/" + fileName);
        if (downloadedFile.exists()) {
            System.out.println("File " + fileName + " already exists in Downloads folder.");
            return;
        }

        // Download the file
        URL url = new URL(downloadUrl);
        IOUtils.copy(url.openStream(), new FileOutputStream(downloadedFile));
        System.out.println("File " + fileName + " downloaded successfully.");
    }

    private void showAlertWithHTML(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        // Get the dialog pane
        DialogPane dialogPane = alert.getDialogPane();
        // Set a larger size for the dialog pane
        dialogPane.setPrefWidth(600);
        dialogPane.setPrefHeight(400);

        alert.showAndWait();
    }

    public void applyButtonStyles() {
        // Apply CSS styles to buttons
        btnDesc.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white;");
        btnApply.setStyle("-fx-background-color: #ad7df8; -fx-text-fill: white;");
        btnDownload.setStyle("-fx-background-color: #ffcccc; -fx-text-fill: white;"); // Pastel pink color

        // Hover styles
        btnDesc.setOnMouseEntered(e -> btnDesc.setStyle("-fx-background-color: #1976d2; -fx-text-fill: white;"));
        btnDesc.setOnMouseExited(e -> btnDesc.setStyle("-fx-background-color: #2196f3; -fx-text-fill: white;"));

        btnApply.setOnMouseEntered(e -> btnApply.setStyle("-fx-background-color: #894afa; -fx-text-fill: white;"));
        btnApply.setOnMouseExited(e -> btnApply.setStyle("-fx-background-color: #ad7df8; -fx-text-fill: white;"));

        btnDownload.setOnMouseEntered(e -> btnDownload.setStyle("-fx-background-color: #ff53d5; -fx-text-fill: white;"));
        btnDownload.setOnMouseExited(e -> btnDownload.setStyle("-fx-background-color: #f525b5; -fx-text-fill: white;"));
    }
    @FXML
    private void handleApplyButtonAction() {
        try {
            // Load the FXML file for the ApplyOffer scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Offer/Apply.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Apply to Offer");
            stage.setScene(new Scene(root));

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
