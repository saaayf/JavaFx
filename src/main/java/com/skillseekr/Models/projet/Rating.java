package com.skillseekr.Models.projet;

public class Rating {

    int idUser;
    int idProjet;
    Double value ;

    public Rating(int idUser, int idProjet, Double value) {
        this.idUser = idUser;
        this.idProjet = idProjet;
        this.value = value;
    }

    public Rating() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }


    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }


    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }


}
