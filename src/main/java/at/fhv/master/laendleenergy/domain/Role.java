package at.fhv.master.laendleenergy.domain;

public enum Role {
    ADMIN("Admin"),
    USER("User");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
