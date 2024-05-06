package com.skillseekr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/com/Skillseekr/User/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("SkillSeekr");
        stage.setScene(scene);
        stage.setMaximized(true); // Maximize the stage to make it full-screen
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
