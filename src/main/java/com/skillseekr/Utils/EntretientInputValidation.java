package com.skillseekr.Utils;
import com.skillseekr.Models.Hire.Entretient;
import java.util.Date;

public class EntretientInputValidation {

    public static boolean validateEntretient(Entretient entretient) {
        return validateDate(entretient.getDate()) &&
                validateType(entretient.getType()) &&
                validateResultat(entretient.getResultat()) &&
                validateIdRecId(entretient.getId_rec_id());
    }

    private static boolean validateDate(Date date) {
        if (date == null) {
            System.err.println("Date must not be null.");
            return false;
        }
        return true;
    }

    private static boolean validateType(String type) {
        if (type == null || type.isEmpty()) {
            System.err.println("Type must not be null or empty.");
            return false;
        }
        return true;
    }

    private static boolean validateResultat(String resultat) {
        if (resultat == null || resultat.isEmpty()) {
            System.err.println("Resultat must not be null or empty.");
            return false;
        }
        return true;
    }

    private static boolean validateIdRecId(Integer idRecId) {
        if (idRecId == null) {
            System.err.println("ID Rec must not be null.");
            return false;
        }
        return true;
    }
}
