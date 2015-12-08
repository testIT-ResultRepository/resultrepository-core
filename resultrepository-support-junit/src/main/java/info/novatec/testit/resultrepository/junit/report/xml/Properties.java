package info.novatec.testit.resultrepository.junit.report.xml;

import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "properties")
@XmlAccessorType(XmlAccessType.FIELD)
public class Properties {

    @XmlElement(name = "property", required = false)
    private List<Property> propertyList = new LinkedList<Property>();

    public List<Property> getPropertyList() {
        return propertyList;
    }

}
