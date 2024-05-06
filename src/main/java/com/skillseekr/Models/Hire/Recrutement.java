package com.skillseekr.Models.Hire;
import java.sql.Date;

public class Recrutement {
    private Integer id;
    private String titre;
    private String description;
    private Date date;
    private String statut;
    public Recrutement (){}

    public Recrutement(Integer id, String titre, String description, Date date, String statut) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.statut = statut;
    }

    public Recrutement(String titre, String description, Date date, String statut) {
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.statut = statut;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Recrutement{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", statut='" + statut + '\'' +
                '}';
    }
}
