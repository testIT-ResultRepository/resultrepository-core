package info.novatec.testit.resultrepository.junit.report.xml;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "testsuite")
@XmlAccessorType(XmlAccessType.FIELD)
public class Testsuite {

    @XmlAttribute(required = true)
    private String name;
    @XmlAttribute(required = true)
    private String time;
    @XmlAttribute(required = true)
    private Integer tests;
    @XmlAttribute(required = true)
    private Integer errors;
    @XmlAttribute(required = true)
    private Integer skipped;
    @XmlAttribute(required = true)
    private Integer failures;

    @XmlElement(required = false)
    private Properties properties;
    @XmlElement(name = "testcase", required = false)
    private List<Testcase> testcases = new LinkedList<Testcase>();

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public Integer getTests() {
        return tests;
    }

    public Integer getErrors() {
        return errors;
    }

    public Integer getSkipped() {
        return skipped;
    }

    public Integer getFailures() {
        return failures;
    }

    public Properties getProperties() {
        return properties;
    }

    public List<Testcase> getTestcases() {
        return testcases;
    }

}
