package info.novatec.testit.resultrepository.metrics.api.report;

import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.rules.TemporaryFolder;


/**
 * These tests assert that all report classes can be serialized to and from XML.
 */
public class XmlSerializationTest extends AbstractSerializationTest {

    private static JAXBContext context;
    private static Marshaller marshaller;
    private static Unmarshaller unmarshaller;

    @ClassRule
    public static final TemporaryFolder FOLDER = new TemporaryFolder();

    @BeforeClass
    public static void initializeContext() throws JAXBException {
        context = JAXBContext.newInstance(CounterReport.class, HistogramReport.class, MeterReport.class, MetricsReport.class,
            TimerReport.class);
        marshaller = context.createMarshaller();
        unmarshaller = context.createUnmarshaller();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T> T biDirectionalSerialization(T object) throws JAXBException, IOException {
        File file = FOLDER.newFile();
        marshaller.marshal(object, file);
        return ( T ) unmarshaller.unmarshal(file);
    }

}
