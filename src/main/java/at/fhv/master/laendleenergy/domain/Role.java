package at.fhv.master.laendleenergy.domain;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    ADMIN("Admin"),
    USER("User");

    private final String name;

    private static final Map<String, Role> lookup = new HashMap<String, Role>();

    static {
        for (Role r : Role.values()) {
            lookup.put(r.getName(), r);
        }
    }

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Role get(String name) {
        return lookup.get(name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                '}';
    }
}
