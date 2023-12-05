package at.fhv.master.laendleenergy.domain.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException() {
        super("User not found.");
    }
}
