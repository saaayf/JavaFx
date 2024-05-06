package com.skillseekr.User;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UserController {
    @FXML
    private AnchorPane contentPane;

    @FXML
    private void handleUsersButtonClick() {
        try {
            AnchorPane showUserPane = FXMLLoader.load(getClass().getResource("/com/Skillseekr/User/Back.fxml"));
            contentPane.getChildren().setAll(showUserPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
