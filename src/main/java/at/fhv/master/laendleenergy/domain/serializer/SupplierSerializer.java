package at.fhv.master.laendleenergy.domain.serializer;

import at.fhv.master.laendleenergy.domain.Supplier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedList;
import java.util.List;

public class SupplierSerializer {

    public SupplierSerializer() {}

    public static List<String> parse() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> parsed = new LinkedList<>();

        for (Supplier s : Supplier.values()) {
            parsed.add(objectMapper.writeValueAsString(s));
        }

        return parsed;
    }
}
