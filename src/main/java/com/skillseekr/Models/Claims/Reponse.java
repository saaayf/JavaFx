package com.skillseekr.Models.Claims;

import java.util.Date;

public class Reponse {
    private int idrep;
    private int reclamation_id;
    private String subject;
    private String message;
    private Date created_at;

    // Constructor with all parameters
    public Reponse(int idrep, int reclamation_id, String subject, String message, Date created_at) {
        this.idrep = idrep;
        this.reclamation_id = reclamation_id;
        this.subject = subject;
        this.message = message;
        this.created_at = created_at;
    }

    // Constructor without idrep parameter
    public Reponse(int reclamation_id, String subject, String message) {
        this.reclamation_id = reclamation_id;
        this.subject = subject;
        this.message = message;
        this.created_at = new Date(); // Set created_at to current date/time
    }

    // Default constructor
    public Reponse() {
        this.created_at = new Date(); // Set created_at to current date/time
    }

    // Getters and Setters
    public int getIdrep() {
        return idrep;
    }

    public void setIdrep(int idrep) {
        this.idrep = idrep;
    }

    public int getReclamation_id() {
        return reclamation_id;
    }

    public void setReclamation_id(int reclamation_id) {
        this.reclamation_id = reclamation_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    // toString method
    @Override
    public String toString() {
        return "Response{" +
                "idrep=" + idrep +
                ", reclamation_id=" + reclamation_id +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
