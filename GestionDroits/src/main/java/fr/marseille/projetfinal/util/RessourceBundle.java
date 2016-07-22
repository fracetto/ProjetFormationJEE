package fr.marseille.projetfinal.util;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;

public class RessourceBundle {
    private static ResourceBundle labels = null;

    private RessourceBundle() {
    }

    public static ResourceBundle getBundle(HttpServletRequest request) {
        if (labels == null) {
            if (request.getLocale() == null) {
                System.out.println("[WARNING] Impossible de recuperer une langue.");

            } else {
                labels = ResourceBundle.getBundle("message", Locale.ENGLISH);
            }
        }
        return labels;
    }
}
