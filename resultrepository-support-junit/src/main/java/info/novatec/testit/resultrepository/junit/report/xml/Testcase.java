package info.novatec.testit.resultrepository.junit.report.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "testcase")
@XmlAccessorType(XmlAccessType.FIELD)
public class Testcase {

    @XmlAttribute(required = true)
    private String name;
    @XmlAttribute(required = true)
    private String classname;
    @XmlAttribute(required = true)
    private String time;

    @XmlElement(required = false)
    private Error error;
    @XmlElement(required = false)
    private Failure failure;
    @XmlElement(required = false)
    private Skipped skipped;

    public String getName() {
        return name;
    }

    public String getClassname() {
        return classname;
    }

    public String getTime() {
        return time;
    }

    public Error getError() {
        return error;
    }

    public Failure getFailure() {
        return failure;
    }

    public Skipped getSkipped() {
        return skipped;
    }

}
