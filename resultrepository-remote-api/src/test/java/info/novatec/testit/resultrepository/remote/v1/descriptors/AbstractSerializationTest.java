package info.novatec.testit.resultrepository.remote.v1.descriptors;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import javax.xml.bind.JAXBException;

import org.junit.Test;


public abstract class AbstractSerializationTest {

    @Test
    public void testSerializationOfTagDescriptor() throws Exception {

        TagDescriptor descriptor = new TagDescriptor("some pattern").usingEquality();

        assertThat(biDirectionalSerialization(descriptor)).isEqualToComparingFieldByField(descriptor);

    }

    protected abstract <T> T biDirectionalSerialization(T object) throws IOException, JAXBException;

}
