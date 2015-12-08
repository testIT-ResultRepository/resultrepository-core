package info.novatec.testit.resultrepository.api.dto.serialization;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonSerializationTest extends AbstractSerializationTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T biDirectionalSerialization(T object) throws IOException {
        String stringValue = MAPPER.writeValueAsString(object);
        return ( T ) MAPPER.readValue(stringValue, object.getClass());
    }

}
