package info.novatec.testit.resultrepository.metrics.api.report;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * These tests assert that all report classes can be serialized to and from
 * JSON.
 */
public class JavaSerializationTest extends AbstractSerializationTest {

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T biDirectionalSerialization(T object) throws IOException {

        assertThat(object).isInstanceOf(Serializable.class);

        byte[] byteArray = null;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(object);
                byteArray = baos.toByteArray();
            }
        }

        try (ByteArrayInputStream bais = new ByteArrayInputStream(byteArray)) {
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                return ( T ) ois.readObject();
            } catch (ClassNotFoundException e) {
                throw new IOException(e);
            }
        }

    }

}
