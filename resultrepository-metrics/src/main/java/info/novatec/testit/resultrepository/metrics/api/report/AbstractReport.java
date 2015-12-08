package info.novatec.testit.resultrepository.metrics.api.report;

import java.io.Serializable;


@SuppressWarnings("serial")
public abstract class AbstractReport implements Serializable {

    private String name;
    private long count;
    private long timestamp;

    protected AbstractReport() {
        // for serialization
    }

    protected AbstractReport(String name, long count) {
        this.name = name;
        this.count = count;
        this.timestamp = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
