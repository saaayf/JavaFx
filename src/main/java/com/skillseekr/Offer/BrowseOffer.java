package com.skillseekr.Offer;

import com.skillseekr.Models.Offers.Offer;
import com.skillseekr.Services.Offers.ServiceOffer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class BrowseOffer implements Initializable {

    @FXML
    private AnchorPane parentPane;

    @FXML
    private FlowPane offerContainer; // FlowPane to contain the dynamically created offer cards

    private ServiceOffer serviceOffer; // ServiceOffer instance for retrieving offer data

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceOffer = new ServiceOffer(); // Instantiate ServiceOffer
        try {
            List<Offer> offers = serviceOffer.getPublishedOffers(); // Retrieve published offers
            populateOfferCards(offers); // Populate offer cards
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }

        // Listen for changes to the width of the FlowPane
        offerContainer.widthProperty().addListener((observable, oldValue, newValue) -> {
            // Recalculate and update the number of columns
            int numColumns = calculateNumColumns();
            offerContainer.setHgap(20); // Horizontal gap
            offerContainer.setVgap(20); // Vertical gap
            offerContainer.setPrefWrapLength(Region.USE_COMPUTED_SIZE); // Reset the preferred wrap length
        });

        // AnchorPane constraints to make the FlowPane fill the entire width
        AnchorPane.setLeftAnchor(offerContainer, 0.0);
        AnchorPane.setRightAnchor(offerContainer, 0.0);
        AnchorPane.setTopAnchor(offerContainer, 200.0); // Adjust as needed
        AnchorPane.setBottomAnchor(offerContainer, 0.0); // Adjust as needed
    }



    private void populateOfferCards(List<Offer> offers) {
        int numColumns = calculateNumColumns(); // Calculate the number of columns based on available width
        offerContainer.getChildren().clear(); // Clear existing offer cards
        offerContainer.setHgap(20); // Horizontal gap
        offerContainer.setVgap(20); // Vertical gap
        offerContainer.setPrefWrapLength(Region.USE_COMPUTED_SIZE); // Reset the preferred wrap length

        for (Offer offer : offers) {
            // Load FXML structure for offer card
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/Skillseekr/Offer/OfferCard.fxml"));
            AnchorPane offerCard = null;
            try {
                offerCard = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception
            }

            // Set offer details
            OfferCard controller = loader.getController();
            controller.setOfferDetails(offer);
            controller.applyButtonStyles();

            // Add offer card to container
            offerContainer.getChildren().add(offerCard);
        }
    }

    private int calculateNumColumns() {
        double availableWidth = offerContainer.getWidth();
        double cardWidth =251.0;
        int numColumns = Math.max((int) (availableWidth / cardWidth), 1); // Ensure at least one column
        return numColumns;
    }



    @FXML
    private void handleInternshipFilter() {
        try {
            List<Offer> offers = serviceOffer.getPublishedInternshipOffers(); // Retrieve published internship offers
            offerContainer.getChildren().clear(); // Clear existing offer cards
            populateOfferCards(offers); // Populate offer cards based on the filter
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    @FXML
    private void handleMissionFilter() {
        try {
            List<Offer> offers = serviceOffer.getPublishedMissionOffers(); // Retrieve published mission offers
            offerContainer.getChildren().clear(); // Clear existing offer cards
            populateOfferCards(offers); // Populate offer cards based on the filter
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}
