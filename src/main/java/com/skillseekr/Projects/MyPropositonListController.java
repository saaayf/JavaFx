package com.skillseekr.Projects;

import com.skillseekr.Models.projet.Projet;
import com.skillseekr.Models.projet.Proposition;
import com.skillseekr.Services.projets.ServiceProposition;
import com.skillseekr.User.SessionManager;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


import java.io.IOException;
import java.sql.SQLException;

public class MyPropositonListController {
    private Projet projet;
    @javafx.fxml.FXML
    private ListView<Proposition> ListViewProp;
    @javafx.fxml.FXML
    private Button BtnRetour;

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
        if (SessionManager.getCurrentUser().getRoles().contains("ROLE_ADMIN")) {
            Proposition proposition = ListViewProp.getSelectionModel().getSelectedItem();
            if (proposition != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects/PropositionDetails.fxml"));
                    Parent root = loader.load();
                    PropositionDetailsController controller = loader.getController();
                    controller.setProposition(proposition);
                    ProjetsRouter.getInstance().showContent(root);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @javafx.fxml.FXML
    public void OnRetourClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects/ProjetDetails.fxml"));
        Parent root = loader.load();
        ProjetDetailsController controller = loader.getController();
        controller.setProjet(projet);
        ProjetsRouter.getInstance().showContent(root);
    }

    @Deprecated
    public void OnAjouterClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects/AjouterProposition.fxml"));
        Parent root = loader.load();
        AjouterPropositionController controller = loader.getController();
        controller.setProjet(projet);
        ProjetsRouter.getInstance().showContent(root);
    }
}
