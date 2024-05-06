package com.skillseekr;

import com.skillseekr.User.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    @FXML
    private Button btnUsers;

    @FXML
    private Button btnOffers;

    @FXML
    private Button btnCalendar;

    @FXML
    private Button btnClaims;

    @FXML
    private Button btnProjects;

    @FXML
    private Button btnHire;


    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) {

        if (mouseEvent.getSource() == btnOffers) {
            // Load Offers page
            if(SessionManager.getCurrentUser().getRoles().contains("ROLE_FREELANCER"))
            {
                loadPage("/com/Skillseekr/Offer/Browse.fxml");

            }
            else {

                loadPage("/com/Skillseekr/Offer/Offer.fxml");
            }
        } else if ((mouseEvent.getSource() == btnHire)) {
            loadPage("/com/Skillseekr/Hire/Recrutement.fxml");
        }
        else if ((mouseEvent.getSource() == btnCalendar)) {
            loadPage("/com/Skillseekr/Calendar.fxml");
        }
        else if ((mouseEvent.getSource() == btnClaims)) {
            loadPage("/com/Skillseekr/Claims/Claims.fxml");
        }
        else if ((mouseEvent.getSource() == btnProjects)) {
            loadPage("/com/Skillseekr/Projects/Projects.fxml");
        }
        else if ((mouseEvent.getSource() == btnUsers) && (SessionManager.getCurrentUser().getRoles().contains("ROLE_ADMIN"))) {
            loadPage("/com/Skillseekr/User/Back.fxml");
        }
    }

    private void loadPage(String fxml) {
        try {
            URL resourceUrl = getClass().getResource(fxml);
            if (resourceUrl == null) {
                throw new IllegalArgumentException("FXML file not found: " + fxml);
            }
            Parent root = FXMLLoader.load(resourceUrl);
            Scene scene = btnOffers.getScene(); // Get the scene from any UI element, assuming all UI elements are in the same scene
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

