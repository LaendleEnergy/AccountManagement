package at.fhv.master.laendleenergy.domain.exceptions;

public class WrongEmailOrPasswordException extends Exception {

    public WrongEmailOrPasswordException() {
        super("E-Mail Adresse oder Passwort nicht korrekt.");
    }
}
