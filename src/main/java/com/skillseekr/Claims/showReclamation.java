package com.skillseekr.Claims;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.cell.PropertyValueFactory;
import com.skillseekr.Models.Claims.Reclamation;
import com.skillseekr.Services.Claims.ServiceReclamation;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.application.Platform;

public class showReclamation {

    @FXML
    private TableView<Reclamation> ClaimTableView;

    @FXML
    private TableColumn<Reclamation, Integer> idColumn;

    @FXML
    private TableColumn<Reclamation, String> titleColumn;

    @FXML
    private TableColumn<Reclamation, Integer> user_idColumn;

    @FXML
    private TableColumn<Reclamation, String> contentColumn;

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem deleteMenuItem;

    @FXML
    private Pagination pagination;

    private ServiceReclamation serviceReclamation;

    public showReclamation() {
        serviceReclamation = new ServiceReclamation();
    }

    @FXML
    private void deleteSelectedClaim() {
        Reclamation selectedClaim = ClaimTableView.getSelectionModel().getSelectedItem();
        if (selectedClaim != null) {
            try {
                serviceReclamation.delete(selectedClaim);
                loadDataIntoTableView();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idrec"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        user_idColumn.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));

        loadDataIntoTableView();
        refreshTablePeriodically();
        initializeActionColumn();
    }

    private void initializeActionColumn() {
        TableColumn<Reclamation, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Reclamation, Void> call(final TableColumn<Reclamation, Void> param) {
                final TableCell<Reclamation, Void> cell = new TableCell<>() {
                    private final Button editButton = new Button("Edit");

                    {
                        // Set button action
                        editButton.setOnAction(event -> {
                            Reclamation reclamation = getTableView().getItems().get(getIndex());
                            openEditForm(reclamation);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(editButton);
                        }
                    }
                };
                return cell;
            }
        });
        ClaimTableView.getColumns().add(actionColumn); // Add the action column to the TableView
    }

    // Method to open the edit form
    private void openEditForm(Reclamation reclamation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Claims/editReclamation.fxml"));
            Parent root = loader.load();
            editReclamation controller = loader.getController();
            controller.initData(reclamation);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); // Set modality to APPLICATION_MODAL
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadDataIntoTableView() {
        try {
            ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(serviceReclamation.show());
            ClaimTableView.setItems(reclamations);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void refreshTable() {
        try {
            serviceReclamation = new ServiceReclamation(); // Initialize the service
            List<Reclamation> reclamations = serviceReclamation.show(); // Fetch all offers from the service

            int itemsPerPage = 4; // Number of claims to display per page
            int pageCount = (int) Math.ceil((double) reclamations.size() / itemsPerPage); // Calculate the number of pages

            pagination.setPageCount(pageCount); // Set the total number of pages

            int currentPageIndex = pagination.getCurrentPageIndex();
            int startIndex = currentPageIndex * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, reclamations.size());
            List<Reclamation> claimsPerPage = reclamations.subList(startIndex, endIndex);
            ClaimTableView.getItems().setAll(claimsPerPage);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., show error message)
        }
    }

    private void refreshTablePeriodically() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000); // Sleep for 5 seconds
                    Platform.runLater(this::refreshTable); // Refresh the table on the JavaFX thread
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
