package at.fhv.master.laendleenergy.domain.exceptions;

public class MemberNotFoundException  extends Exception {

    public MemberNotFoundException() {
        super("Member not found.");
    }
}
