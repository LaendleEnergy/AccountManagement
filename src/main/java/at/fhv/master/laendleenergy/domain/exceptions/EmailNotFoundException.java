package at.fhv.master.laendleenergy.domain.exceptions;

public class EmailNotFoundException extends Exception {

    public EmailNotFoundException() {
        super("No user with specified email address found.");
    }
}
