package info.novatec.testit.resultrepository.junit.report;

import java.io.File;
import java.io.InputStream;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import info.novatec.testit.resultrepository.junit.exceptions.InitializationException;
import info.novatec.testit.resultrepository.junit.report.xml.Testsuite;


/**
 * Instances of this class can be used to read XML based JUnit reports as an
 * object structure.
 *
 * @see Testsuite
 */
public class JUnitReportReader {

    private final Unmarshaller jaxbUnmarshaller;

    /**
     * Creates a new instance of a {@link JUnitReportReader}.
     *
     * @throws InitializationException if the reader could not be initialized because of
     * errors in the JUnit report schema definition
     */
    public JUnitReportReader() throws InitializationException {
        jaxbUnmarshaller = JUnitReportUtils.createJUnitReportUnmarshaller();
    }

    /**
     * Reads a {@link Testsuite test suite report} from a {@link File file}.
     *
     * @param file the file to read as a report
     * @return the test suite report
     * @throws JAXBException if the given file could not be read as a test suite
     * report
     */
    public Testsuite readFrom(File file) throws JAXBException {
        return ( Testsuite ) jaxbUnmarshaller.unmarshal(file);
    }

    /**
     * Reads a {@link Testsuite test suite report} from an {@link InputStream
     * input stream}.
     *
     * @param inputStream the stream to read as a report
     * @return the test suite report
     * @throws JAXBException if the given file could not be read as a test suite
     * report
     */
    public Testsuite readFrom(InputStream inputStream) throws JAXBException {
        return ( Testsuite ) jaxbUnmarshaller.unmarshal(inputStream);
    }

}
