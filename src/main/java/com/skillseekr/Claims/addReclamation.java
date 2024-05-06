package com.skillseekr.Claims;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import com.skillseekr.Models.Claims.Reclamation;
import com.skillseekr.Services.Claims.ServiceReclamation;
import java.sql.SQLException;

public class addReclamation {

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField user_idTextField;

    @FXML
    private TextArea ContentTextField;

    @FXML
    private Button saveButton;

    private ServiceReclamation serviceReclamation = new ServiceReclamation();

    @FXML
    private void initialize() {
        serviceReclamation = new ServiceReclamation();
    }

    @FXML
    private void handleSaveButtonAction() {
        String title = titleTextField.getText();
        String user_id = user_idTextField.getText();
        String content = ContentTextField.getText();

        // You can perform validation here to ensure all fields are filled

        // Create a new Reclamation object with the entered data
        Reclamation reclamation = new Reclamation(Integer.parseInt(user_id), title, content);

        try {
            // Save the Reclamation using the service
            serviceReclamation.addReclamation(reclamation, Integer.parseInt(user_id));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any potential exceptions here
        }
    }
}
