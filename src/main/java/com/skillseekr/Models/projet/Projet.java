package com.skillseekr.Models.projet;

import java.time.LocalDate;

public class Projet {

    private int id ;
    private String titre ;
    private String competences ;
    private Float budget ;
    private LocalDate date_deb ;
    private LocalDate date_fin ;
    private String proprietaire ;

    private int idUser ;

    public Projet() {
    }

    public Projet(int id, String titre, String competences, Float budget, LocalDate date_deb, LocalDate date_fin, String proprietaire, int idUser) {
        this.id = id;
        this.titre = titre;
        this.competences = competences;
        this.budget = budget;
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.proprietaire = proprietaire;
        this.idUser = idUser;
    }

    public Projet(String titre, String competences, Float budget, LocalDate date_deb, LocalDate date_fin, String proprietaire, int idUser) {
        this.titre = titre;
        this.competences = competences;
        this.budget = budget;
        this.date_deb = date_deb;
        this.date_fin = date_fin;
        this.proprietaire = proprietaire;
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCompetences() {
        return competences;
    }

    public void setCompetences(String competences) {
        this.competences = competences;
    }

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public LocalDate getDate_deb() {
        return date_deb;
    }

    public void setDate_deb(LocalDate date_deb) {
        this.date_deb = date_deb;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(String proprietaire) {
        this.proprietaire = proprietaire;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "Projet{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", competences='" + competences + '\'' +
                ", budget='" + budget + '\'' +
                ", date_deb=" + date_deb +
                ", date_fin=" + date_fin +
                ", proprietaire='" + proprietaire + '\'' +
                ", idUser=" + idUser +
                '}';
    }
}
