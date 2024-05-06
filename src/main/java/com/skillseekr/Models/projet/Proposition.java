package com.skillseekr.Models.projet;

import java.time.LocalDate;

public class Proposition {

    private int id ;
    private String description ;
    private Float propBudget;
    private LocalDate propDelai ;
    private Projet projet ;
    private int idUser;

    public Proposition() {
    }

    public Proposition(int id, String description, Float propBudget, LocalDate propDelai, Projet projet, int idUser) {
        this.id = id;
        this.description = description;
        this.propBudget = propBudget;
        this.propDelai = propDelai;
        this.projet = projet;
        this.idUser = idUser;
    }

    public Proposition(String description, Float propBudget, LocalDate propDelai, Projet projet, int idUser) {
        this.description = description;
        this.propBudget = propBudget;
        this.propDelai = propDelai;
        this.projet = projet;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPropBudget() {
        return propBudget;
    }

    public void setPropBudget(Float propBudget) {
        this.propBudget = propBudget;
    }

    public LocalDate getPropDelai() {
        return propDelai;
    }

    public void setPropDelai(LocalDate propDelai) {
        this.propDelai = propDelai;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "Proposition{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", propBudget=" + propBudget +
                ", propDelai=" + propDelai +
                ", projet=" + projet +
                ", idUser=" + idUser +
                '}';
    }
}
