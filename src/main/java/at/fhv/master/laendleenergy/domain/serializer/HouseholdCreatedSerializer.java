package at.fhv.master.laendleenergy.domain.serializer;

import at.fhv.master.laendleenergy.domain.events.HouseholdCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class HouseholdCreatedSerializer {
    public HouseholdCreatedSerializer() {}

    public static String parse(HouseholdCreatedEvent event) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.writeValueAsString(event);
    }
}
