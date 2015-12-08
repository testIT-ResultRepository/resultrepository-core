package info.novatec.testit.resultrepository.junit.report;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import info.novatec.testit.resultrepository.junit.exceptions.InitializationException;
import info.novatec.testit.resultrepository.junit.report.xml.Testsuite;


/**
 * A collection of utility methods used when working with JUnit reports.
 */
public final class JUnitReportUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JUnitReportUtils.class);

    private static final int MILLIS_PER_SECOND = 1000;

    private static final String PREFIX = "TEST-";
    private static final String SUFFIX = ".xml";

    /**
     * Can be used to filter directories when listing the content of another
     * directory.
     */
    public static final FileFilter DIRECTORY_FILTER = new FileFilter() {

        @Override
        public boolean accept(File pathname) {
            return pathname.isDirectory();
        }

    };

    /**
     * Can be used to filter JUnit report files when listing the content of a
     * directory. A valid report file name starts with {@value #PREFIX} and ends
     * with {@value #SUFFIX}.
     */
    public static final FilenameFilter REPORT_FILE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(File dir, String name) {
            return name.startsWith(PREFIX) && name.endsWith(SUFFIX);
        }

    };

    /**
     * Converts the given JUnit report formatted time string into a long
     * duration. The format consists of two numbers separated by a point. The
     * first number represents seconds the second number represents
     * milliseconds.
     * <p>
     * <b>Examples:</b>
     * <ul>
     * <li>0.430 - 430ms</li>
     * <li>1.010 - 1010ms</li>
     * <li>120.0 - 120000ms</li>
     * </ul>
     *
     * @param reportTime the time string to parse
     * @return the time value in milliseconds
     */
    public static long convertReportTimeToMillis(String reportTime) {

        long returnValue = 0;
        if (isEmpty(reportTime)) {
            return returnValue;
        }

        String[] splitArray = StringUtils.split(reportTime, '.');
        if (splitArray.length == 1) {
            returnValue = Long.parseLong(splitArray[0]) * MILLIS_PER_SECOND;
        } else if (splitArray.length == 2) {
            returnValue = Long.parseLong(splitArray[0]) * MILLIS_PER_SECOND + Long.parseLong(splitArray[1]);
        } else {
            throw new IllegalArgumentException("Unknown JUnit report time format: " + reportTime);
        }

        return returnValue;

    }

    /**
     * Creates a new JUnit report JAX-B {@link Unmarshaller}. This unmarshaller
     * includes a strict schema validation. The schema is generated using the
     * {@link Testsuite} class as a reference.
     *
     * @return the newly created unmarshaller
     * @throws InitializationException if the unmarshaller or its context could
     * not be initialized.
     */
    public static Unmarshaller createJUnitReportUnmarshaller() throws InitializationException {

        File schemaFile = null;

        try {

            JAXBContext context = JAXBContext.newInstance(Testsuite.class);
            schemaFile = generateTemporarySchemaFile(context);

            Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaFile);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(new ValidationEventHandler() {

                @Override
                public boolean handleEvent(ValidationEvent event) {
                    return event.getSeverity() == ValidationEvent.WARNING;
                }

            });

            return unmarshaller;

        } catch (JAXBException e) {
            throw new InitializationException("Error while creating a JAX-B unmarshaller for JUnit reports!", e);
        } catch (IOException e) {
            throw new InitializationException("Error while creating temporary JUnit report schema file!", e);
        } catch (SAXException e) {
            throw new InitializationException("Error while parsing JUnit report schema file!", e);
        } finally {
            if (schemaFile != null && schemaFile.delete()) {
                LOGGER.debug("deleted temporary schema: {}", schemaFile);
            }
        }

    }

    private static File generateTemporarySchemaFile(JAXBContext context) throws IOException {
        final File tempFile = File.createTempFile("jaxb-junit-report-schema", ".xsd");
        context.generateSchema(new SchemaOutputResolver() {

            @Override
            public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
                StreamResult result = new StreamResult(tempFile);
                result.setSystemId(tempFile.toURI().toURL().toString());
                return result;
            }

        });
        return tempFile;
    }

    private JUnitReportUtils() {
        // utility constructor
    }

}
