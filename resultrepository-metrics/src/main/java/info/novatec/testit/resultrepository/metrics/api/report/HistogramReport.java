package info.novatec.testit.resultrepository.metrics.api.report;

import javax.xml.bind.annotation.XmlRootElement;

import com.codahale.metrics.Histogram;


@XmlRootElement
@SuppressWarnings("serial")
public class HistogramReport extends AbstractSnapshotReport {

    public HistogramReport() {
        // for serialization
    }

    public HistogramReport(String name, Histogram histogram) {
        super(name, histogram.getCount(), histogram.getSnapshot(), 1);
    }

}
