package info.novatec.testit.resultrepository.junit.report.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;


@XmlRootElement(name = "error")
@XmlAccessorType(XmlAccessType.FIELD)
public class Error {

    @XmlAttribute(required = false)
    private String message;
    @XmlAttribute(required = true)
    private String type;

    @XmlValue
    private String stacktrace;

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public String getStacktrace() {
        return stacktrace;
    }

}
