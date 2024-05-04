package controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import models.Projet;
import models.Proposition;
import services.ServiceProposition;

import java.io.IOException;
import java.sql.SQLException;

public class PropositionsListController {
    private Projet projet;
    @javafx.fxml.FXML
    private ListView<Proposition> ListViewProp;
    @javafx.fxml.FXML
    private Button BtnRetour;
    @javafx.fxml.FXML
    private Button BtnAjouter;

    private ServiceProposition serviceProposition = new ServiceProposition();

    void setProjet (Projet projet) throws SQLException {
        this.projet = projet;
        afficherPropositions();
    }

    void afficherPropositions() throws SQLException {
        ListViewProp.setCellFactory(listView -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Proposition proposition, boolean empty) {
                super.updateItem(proposition, empty);

                if (empty || proposition == null) {
                    setText(null);
                } else {
                    setText(proposition.getDescription() + " - " + proposition.getPropDelai() + " - " + proposition.getPropBudget());
                }
            }
        });

        ListViewProp.getItems().clear();
        ListViewProp.getItems().addAll(serviceProposition.getByIdProjet(projet.getId()));
    }

    @javafx.fxml.FXML
    public void OnMouseClicked(Event event) {
        Proposition proposition = ListViewProp.getSelectionModel().getSelectedItem();
        if (proposition != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PropositionDetails.fxml"));
                Parent root = loader.load();
                PropositionDetailsController controller = loader.getController();
                controller.setProposition(proposition);
                BtnRetour.getScene().setRoot(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @javafx.fxml.FXML
    public void OnRetourClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProjetDetails.fxml"));
        Parent root = loader.load();
        ProjetDetailsController controller = loader.getController();
        controller.setProjet(projet);
        this.BtnRetour.getScene().setRoot(root);
    }

    @javafx.fxml.FXML
    public void OnAjouterClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProposition.fxml"));
        Parent root = loader.load();
        AjouterPropositionController controller = loader.getController();
        controller.setProjet(projet);
        this.BtnRetour.getScene().setRoot(root);
    }
}
