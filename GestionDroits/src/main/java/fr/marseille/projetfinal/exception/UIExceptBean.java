package fr.marseille.projetfinal.exception;

public class UIExceptBean extends Exception {

    public UIExceptBean(String message) {
        super(message);
        // TODO need un Logger implementation
        System.out.println(message);
    }

}
