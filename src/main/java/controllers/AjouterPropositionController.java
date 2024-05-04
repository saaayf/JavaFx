package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import models.Projet;
import models.Proposition;
import services.ServiceProposition;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterPropositionController {
    private Projet projet;
    @javafx.fxml.FXML
    private TextArea tfDesc;
    @javafx.fxml.FXML
    private TextField tfBudget;
    @javafx.fxml.FXML
    private DatePicker DateDelai;
    @javafx.fxml.FXML
    private Button BtnAjouter;
    @javafx.fxml.FXML
    private Button BtnAnnuler;
    @javafx.fxml.FXML
    private Button BtnRetour;

    private Alert alert = new Alert(Alert.AlertType.NONE);

    private ServiceProposition serviceProposition = new ServiceProposition();


    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    @javafx.fxml.FXML
    public void OnAjouterClicked(ActionEvent actionEvent) {
        if (tfDesc.getText().isEmpty() || tfBudget.getText().isEmpty() || DateDelai.getValue() == null) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();
        } else if (tfDesc.getText().length() < 3) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("La description doit contenir au moins 3 caractères");
            alert.show();
        } else if (!tfBudget.getText().matches("[0-9]+(\\.[0-9][0-9]?)?")) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Le budget doit être un nombre");
            alert.show();
        } else {
            try {
                Proposition proposition = new Proposition();
                proposition.setDescription(tfDesc.getText());
                proposition.setPropBudget(Float.parseFloat(tfBudget.getText()));
                proposition.setPropDelai(DateDelai.getValue());
                proposition.setProjet(projet);
                proposition.setIdUser(1);
                serviceProposition.add(proposition);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Proposition ajoutée avec succès");
                alert.show();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PropositionsList.fxml"));
                Parent root = loader.load();
                PropositionsListController controller = loader.getController();
                controller.setProjet(projet);
                BtnAjouter.getScene().setRoot(root);
            } catch (Exception e) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Erreur lors de l'ajout de la proposition");
                alert.show();
            }
        }
    }

    @javafx.fxml.FXML
    public void OnAnnulerClicked(ActionEvent actionEvent) {
        tfBudget.clear();
        tfDesc.clear();
        DateDelai.setValue(null);
    }

    @javafx.fxml.FXML
    public void OnRetourClicked(ActionEvent actionEvent) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/PropositionsList.fxml"));
        Parent root = loader.load();
        PropositionsListController controller = loader.getController();
        controller.setProjet(projet);
        BtnAjouter.getScene().setRoot(root);
    }
}
