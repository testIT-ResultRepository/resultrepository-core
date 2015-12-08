package info.novatec.testit.resultrepository.api.statistics;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class JavaSerializationTest extends AbstractSerializationTest {

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T biDirectionalSerialization(T object) throws IOException {

        assertThat(object).isInstanceOf(Serializable.class);

        byte[] byteArray = null;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(object);
            byteArray = baos.toByteArray();
        }

        try (ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
             ObjectInputStream ois = new ObjectInputStream(bais)) {
            return ( T ) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException(e);
        }

    }

}
