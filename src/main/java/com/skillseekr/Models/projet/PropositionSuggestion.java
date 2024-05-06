package com.skillseekr.Models.projet;

public class PropositionSuggestion {

    private String Title;
    private String Description;

    public PropositionSuggestion(String title, String description) {
        Title = title;
        Description = description;
    }

    public PropositionSuggestion() {
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return Title ;

    }
}
