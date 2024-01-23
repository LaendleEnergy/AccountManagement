package at.fhv.master.laendleenergy.domain;

import java.util.HashMap;
import java.util.Map;

public enum Gender {
    MALE("männlich"),
    FEMALE("weiblich"),
    DIVERSE("divers"),
    NONE("");

    private final String name;

    private static final Map<String, Gender> lookup = new HashMap<String, Gender>();

    static {
        for (Gender g : Gender.values()) {
            lookup.put(g.getName(), g);
        }
    }

    Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Gender get(String name) {
        return lookup.get(name);
    }

    @Override
    public String toString() {
        return "Gender{" +
                "name='" + name + '\'' +
                '}';
    }
}
