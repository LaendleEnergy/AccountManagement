package at.fhv.master.laendleenergy.domain;

public enum Gender {
    MALE("männlich"),
    FEMALE("weiblich"),
    DIVERSE("divers");

    private final String name;

    Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
