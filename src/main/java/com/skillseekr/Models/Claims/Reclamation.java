package com.skillseekr.Models.Claims;

import java.util.Date;
import java.util.List;

public class Reclamation {
    private Integer idrec;
    private Integer user_id;
    private String title;
    private String content;

    // Constructor with id
    public Reclamation(Integer id, Integer user_id, String title, String content) {
        this.idrec = idrec;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
    }

    // Constructor without id
    public Reclamation(Integer user_id, String title, String content) {
        this.user_id = user_id;
        this.title = title;
        this.content = content;
    }

    // Default constructor
    public Reclamation() {
        // Default constructor
    }

    // Getters and Setters
    public Integer getIdrec() {
        return idrec;
    }

    public void setIdrec(Integer idrec) {
        this.idrec = idrec;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // toString method
    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + idrec +
                ", user_id=" + user_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
