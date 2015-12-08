package info.novatec.testit.resultrepository.metrics.api.report;

import javax.xml.bind.annotation.XmlRootElement;

import com.codahale.metrics.Counter;


@XmlRootElement
@SuppressWarnings("serial")
public class CounterReport extends AbstractReport {

    public CounterReport() {
        // for serialization
    }

    public CounterReport(String name, Counter counter) {
        super(name, counter.getCount());
    }

}
