package at.fhv.master.laendleenergy.domain.serializer;

import at.fhv.master.laendleenergy.domain.events.MemberAddedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MemberSerializer {
    public MemberSerializer() {}

    public static String parse(MemberAddedEvent event) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.writeValueAsString(event);
    }
}
