package com.skillseekr.Projects;

import com.skillseekr.Models.projet.Proposition;
import com.skillseekr.Services.projets.ServiceProposition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class PropositionDetailsController {
    private Proposition proposition;
    @javafx.fxml.FXML
    private TextArea tfDesc;
    @javafx.fxml.FXML
    private TextField tfBudget;
    @javafx.fxml.FXML
    private DatePicker DateDelai;
    @javafx.fxml.FXML
    private Button BtnModif;
    @javafx.fxml.FXML
    private Button BtnSupp;
    @javafx.fxml.FXML
    private Button BtnRetour;

    private ServiceProposition serviceProposition = new ServiceProposition();

    private Alert alert = new Alert(Alert.AlertType.NONE);

    public void setProposition(Proposition proposition) {
        this.proposition = proposition;
        this.tfDesc.setText(proposition.getDescription());
        this.tfBudget.setText(String.valueOf(proposition.getPropBudget()));
        this.DateDelai.setValue(proposition.getPropDelai());

    }

    @javafx.fxml.FXML
    public void OnModifClicked(ActionEvent actionEvent) throws SQLException, IOException {
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
            proposition.setDescription(tfDesc.getText());
            proposition.setPropBudget(Float.parseFloat(tfBudget.getText()));
            proposition.setPropDelai(DateDelai.getValue());
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Voulez-vous vraiment modifier cette proposition?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                serviceProposition.update(proposition);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Proposition modifiée avec succès");
                alert.show();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects/PropositionsList.fxml"));
                Parent root = loader.load();
                PropositionsListController controller = loader.getController();
                controller.setProjet(proposition.getProjet());
                ProjetsRouter.getInstance().showContent(root);
            }
        }
    }

    @javafx.fxml.FXML
    public void OnSuppClicked(ActionEvent actionEvent) throws SQLException, IOException {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Voulez-vous vraiment supprimer cette proposition?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            serviceProposition.delete(proposition.getId());
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Proposition supprimée avec succès");
            alert.show();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects/PropositionsList.fxml"));
            Parent root = loader.load();
            PropositionsListController controller = loader.getController();
            controller.setProjet(proposition.getProjet());
          ProjetsRouter.getInstance().showContent(root);
        }
    }

    @javafx.fxml.FXML
    public void OnRetourClicked(ActionEvent actionEvent) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects/PropositionsList.fxml"));
        Parent root = loader.load();
        PropositionsListController controller = loader.getController();
        controller.setProjet(proposition.getProjet());
       ProjetsRouter.getInstance().showContent(root);
    }
}
