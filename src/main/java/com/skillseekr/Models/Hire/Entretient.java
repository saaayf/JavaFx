package com.skillseekr.Models.Hire;
import java.sql.Date;
public class Entretient {
    private Integer id;
    private Date date;
    private String type;
    private String resultat;
    private Integer id_rec_id;

    public Entretient(Integer id, Date date, String type, String resultat, Integer id_rec_id) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.resultat = resultat;
        this.id_rec_id = id_rec_id;
    }

    public Entretient(Date date, String type, String resultat, Integer id_rec_id) {
        this.date = date;
        this.type = type;
        this.resultat = resultat;
        this.id_rec_id = id_rec_id;
    }

    public Entretient() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public Integer getId_rec_id() {

        return id_rec_id;
    }

    public void setId_rec_id(Integer id_rec_id) {
        this.id_rec_id = id_rec_id;
    }


    @Override
    public String toString() {
        return "Entretient{" +
                "id=" + id +
                ", date=" + date +
                ", type='" + type + '\'' +
                ", resultat='" + resultat + '\'' +
                ", id_rec=" + id_rec_id +
                '}';
    }
}
