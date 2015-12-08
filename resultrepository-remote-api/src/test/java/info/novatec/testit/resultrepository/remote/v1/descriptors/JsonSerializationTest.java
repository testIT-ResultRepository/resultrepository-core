package info.novatec.testit.resultrepository.remote.v1.descriptors;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * These tests assert that all dto classes can be serialized to and from JSON.
 */
public class JsonSerializationTest extends AbstractSerializationTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T biDirectionalSerialization(T object) throws IOException {
        String stringValue = MAPPER.writeValueAsString(object);
        return ( T ) MAPPER.readValue(stringValue, object.getClass());
    }

}
