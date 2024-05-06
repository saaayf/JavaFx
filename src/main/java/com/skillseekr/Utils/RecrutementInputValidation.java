package com.skillseekr.Utils;

import com.skillseekr.Models.Hire.Recrutement;
import java.util.Date;

public class RecrutementInputValidation {

    public static boolean validateRecrutement(Recrutement recrutement) {
        return validateTitre(recrutement.getTitre()) &&
                validateDescription(recrutement.getDescription()) &&
                validateDate(recrutement.getDate()) &&
                validateStatut(recrutement.getStatut());
    }

    private static boolean validateTitre(String titre) {
        if (titre == null || titre.isEmpty()) {
            System.err.println("Titre must not be null or empty.");
            return false;
        }
        return true;
    }

    private static boolean validateDescription(String description) {
        if (description == null || description.isEmpty()) {
            System.err.println("Description must not be null or empty.");
            return false;
        }
        return true;
    }

    private static boolean validateDate(Date date) {
        if (date == null) {
            System.err.println("Date must not be null.");
            return false;
        }
        return true;
    }

    private static boolean validateStatut(String statut) {
        if (statut == null || statut.isEmpty()) {
            System.err.println("Statut must not be null or empty.");
            return false;
        }
        return true;
    }
}
