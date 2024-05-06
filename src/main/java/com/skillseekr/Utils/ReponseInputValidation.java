package com.skillseekr.Utils;

import com.skillseekr.Models.Claims.Reponse;

public class ReponseInputValidation {
    public static boolean validateReponse(Reponse reponse) {
        // Check if any of the fields are null
        if (reponse.getReclamation_id() == 0 || reponse.getSubject() == null || reponse.getMessage() == null || reponse.getCreated_at() == null) {
            return false;
        }
        return true;
    }
}
