package com.skillseekr.Projects;

import com.skillseekr.Models.projet.Projet;
import com.skillseekr.Services.projets.ServiceProjet;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ProjetsListController implements Initializable {
    @javafx.fxml.FXML
    private Button BtnAjouter;
    @javafx.fxml.FXML
    private ListView<Projet> ListProjets;
    private ServiceProjet serviceProjet = new ServiceProjet();
    @javafx.fxml.FXML
    private TextField SearchField;
    @javafx.fxml.FXML
    private ComboBox<String> ComboBudget;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ComboBudget.getItems().addAll("< 1000", "1000 - 5000", "5000 - 10000", "> 10000");
        try {
            afficherListeProjets(serviceProjet.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        SearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Projet> lp = null;
            try {
                lp = serviceProjet.getAll().stream().filter(projet -> projet.getTitre().contains(newValue)).toList();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                afficherListeProjets(lp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        ComboBudget.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    List<Projet> lp = null;
                    try {
                        lp = serviceProjet.getAll().stream().filter(projet -> {
                            switch (newValue) {
                                case "< 1000" -> {
                                    return projet.getBudget() < 1000;
                                }
                                case "1000 - 5000" -> {
                                    return projet.getBudget() >= 1000 && projet.getBudget() <= 5000;
                                }
                                case "5000 - 10000" -> {
                                    return projet.getBudget() >= 5000 && projet.getBudget() <= 10000;
                                }
                                case "> 10000" -> {
                                    return projet.getBudget() > 10000;
                                }
                                default -> {
                                    return false;
                                }
                            }
                        }).toList();
                        afficherListeProjets(lp);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void afficherListeProjets(List<Projet> lp) throws SQLException {

        ListProjets.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Projet projet, boolean empty) {
                super.updateItem(projet, empty);

                if (empty || projet == null) {
                    setText(null);
                } else {
                    // Format the text to display based on your Projet object's fields
                    setText(projet.getTitre() + " - " + projet.getCompetences() + " - " + projet.getBudget() + " - " + projet.getDate_deb() + " - " + projet.getDate_fin() + " - " + projet.getProprietaire());
                }
            }
        });
        ListProjets.getItems().clear(); // Clear existing items before adding new ones
        for (Projet p : lp) {
            this.ListProjets.getItems().add(p);
        }
    }

    @javafx.fxml.FXML
    public void Ajouter(ActionEvent actionEvent) throws IOException {

    }

    @javafx.fxml.FXML
    public void OnMouseClicked(Event event) {
        Projet projet = ListProjets.getSelectionModel().getSelectedItem();
        if (projet != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects/ProjetDetails.fxml"));
                Parent root = loader.load();
                ProjetDetailsController controller = loader.getController();
                controller.setProjet(projet);
                ProjetsRouter.getInstance().showContent(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }




}
