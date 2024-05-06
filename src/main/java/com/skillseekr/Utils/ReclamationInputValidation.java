package com.skillseekr.Utils;

import com.skillseekr.Models.Claims.Reclamation;

public class ReclamationInputValidation {
    public static boolean validateReclamation(Reclamation reclamation) {
        // Check if any of the fields are null
        if (reclamation.getUser_id() == null || reclamation.getTitle() == null || reclamation.getContent() == null) {
            return false;
        }
        return true;
    }
}
