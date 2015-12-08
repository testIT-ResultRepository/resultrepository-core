package info.novatec.testit.resultrepository.junit.report.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "property")
@XmlAccessorType(XmlAccessType.FIELD)
public class Property {

    @XmlAttribute(required = true)
    private String name;
    @XmlAttribute(required = true)
    private String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
