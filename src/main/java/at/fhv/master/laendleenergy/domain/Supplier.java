package at.fhv.master.laendleenergy.domain;

import java.util.HashMap;
import java.util.Map;

public enum Supplier {
    VKW("VKW");

    private final String name;

    private static final Map<String, Supplier> lookup = new HashMap<String, Supplier>();

    static {
        for (Supplier s : Supplier.values()) {
            lookup.put(s.getName(), s);
        }
    }
    Supplier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Supplier get(String name) {
        return lookup.get(name);
    }
}
