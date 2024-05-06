package com.skillseekr.User;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button; // Import Button class
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import com.skillseekr.API.EmailAPI;
import com.skillseekr.Models.User.User;
import com.skillseekr.Services.User.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class showUser {
    @FXML
    private TableColumn<User, String> emailCol;

    @FXML
    private TableColumn<User, String> roleCol;

    @FXML
    private TableColumn<User, String> usernameCol;

    @FXML
    private TableColumn<User, Integer> verificationCol;

    @FXML
    private TableView<User> tableView;

    @FXML
    private TableColumn<User, Void> actionCol;


    @FXML
    void initialize() {
        ServiceUser serviceUser = new ServiceUser();
        try {
            List<User> users = serviceUser.show();
            ObservableList<User> observableList = FXCollections.observableList(users);
            tableView.setItems(observableList);

            // Set cell value factories for other columns
            usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            roleCol.setCellValueFactory(cellData -> {
                String role = cellData.getValue().getRoles();
                switch (role) {
                    case "[\"ROLE_ADMIN\"]":
                        return new SimpleStringProperty("Administrator");
                    case "[\"ROLE_RECRUTEUR\"]":
                        return new SimpleStringProperty("Recruiter");
                    case "[\"ROLE_FREELANCER\"]":
                        return new SimpleStringProperty("Freelancer");
                    default:
                        return new SimpleStringProperty(role);
                }
            });

            verificationCol.setCellValueFactory(cellData ->
                    new SimpleIntegerProperty(cellData.getValue().getIs_verified() ? 1 : 0).asObject());

            // Add edit and delete buttons to action column
            actionCol.setCellFactory(new Callback<>() {
                @Override
                public TableCell<User, Void> call(TableColumn<User, Void> param) {
                    return new TableCell<>() {
                        private final HBox manageBtn = new HBox();
                        private final Button editButton = new Button("Edit");
                        private final Button deleteButton = new Button("Delete");

                        {
                            // Set styles for buttons
                            editButton.setStyle("-fx-background-color: #77dd77; -fx-text-fill: white;");
                            deleteButton.setStyle("-fx-background-color: #f32d2d; -fx-text-fill: white;");


                            deleteButton.setOnMouseClicked(event -> {
                                User user = getTableView().getItems().get(getIndex());
                                ServiceUser userService = new ServiceUser();
                                try {
                                    userService.delete(user);
                                    refreshTableView();
                                } catch (SQLException ex) {
                                    Logger.getLogger(showUser.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });



                            editButton.setOnMouseClicked(event -> {
                                User user = getTableView().getItems().get(getIndex());
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/Skillseekr/User/editUser.fxml")); // Mettez à jour le chemin vers votre fichier FXML d'édition d'utilisateur
                                try {
                                    Parent root = loader.load();
                                    editUser editUserController = loader.getController();
                                    editUserController.initData(user); // Pass user data to the edit controller
                                    Stage stage = new Stage();
                                    stage.setScene(new Scene(root));
                                    stage.initStyle(StageStyle.UTILITY);
                                    stage.showAndWait();

                                    String subject;
                                    String message;
                                    if (user.getIs_verified()) {
                                        subject = "Your account has been verified";
                                        message = "Dear " + user.getUsername() + ", your account has been verified. You can now log in.";
                                    } else {
                                        subject = "Your account is not yet verified";
                                        message = "Dear " + user.getUsername() + ", Your account is currently blocked";
                                    }

                                    EmailAPI.sendEmail(user.getEmail(), subject, message); // Make sure EmailAPI class is correctly implemented for sending emails

                                    refreshTableView(); // Refresh the table after editing
                                } catch (IOException ex) {
                                    Logger.getLogger(showUser.class.getName()).log(Level.SEVERE, "Error handling edit user action", ex);
                                    // Handle the exception (e.g., show an error message)
                                }
                            });


                            manageBtn.getChildren().addAll(editButton, deleteButton);
                            manageBtn.setStyle("-fx-alignment: center;");
                            HBox.setMargin(deleteButton, new Insets(2, 2, 0, 10));
                            HBox.setMargin(editButton, new Insets(2, 3, 0, 12));
                        }

                        @Override
                        protected void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (!empty) {
                                setGraphic(manageBtn);
                            } else {
                                setGraphic(null);
                            }
                        }
                    };
                }
            });
        } catch (SQLException e) {
            System.err.println("Error initializing the table: " + e.getMessage());
        }
    }

    private void refreshTableView() {
        tableView.getItems().clear();
        ServiceUser serviceUser = new ServiceUser();
        try {
            List<User> users = serviceUser.show();
            ObservableList<User> observableList = FXCollections.observableList(users);
            tableView.setItems(observableList);
        } catch (SQLException e) {
            System.err.println("Error refreshing the table: " + e.getMessage());
        }
    }
}
