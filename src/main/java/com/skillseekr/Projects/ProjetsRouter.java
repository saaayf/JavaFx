package com.skillseekr.Projects;

import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class ProjetsRouter {

    private static ProjetsRouter instance ;
    private AnchorPane Currentcontent ;


    private ProjetsRouter() {
    }

    public static ProjetsRouter getInstance() {
        if (instance == null) {
            instance = new ProjetsRouter();
        }
        return instance;
    }

    public void setContent(AnchorPane content) {
        this.Currentcontent = content;
    }

    public AnchorPane getContent() {
        return this.Currentcontent;
    }
    public void showContent(Parent content) {
        this.Currentcontent.getChildren().clear();
        this.Currentcontent.getChildren().add(content);
    }

}
