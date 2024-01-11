package at.fhv.master.laendleenergy.domain.serializer;

import at.fhv.master.laendleenergy.domain.events.MemberRemovedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class MemberRemovedSerializer {
    public MemberRemovedSerializer() {}

    public static String parse(MemberRemovedEvent event) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper.writeValueAsString(event);
    }
}
