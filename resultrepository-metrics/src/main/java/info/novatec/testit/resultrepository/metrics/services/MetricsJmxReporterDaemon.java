package info.novatec.testit.resultrepository.metrics.services;

import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Service;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;


@Service
public class MetricsJmxReporterDaemon implements SmartLifecycle {

    @Autowired
    private MetricRegistry registry;

    private JmxReporter reporter;
    private boolean isRunning;

    @PostConstruct
    public void init() {
        reporter = JmxReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build();
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void start() {
        reporter.start();
        isRunning = true;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public int getPhase() {
        return isRunning() ? 1 : 0;
    }

    @Override
    public void stop(Runnable callback) {
        stop();
        callback.run();
    }

    @Override
    public void stop() {
        reporter.stop();
        isRunning = false;
    }

    @PreDestroy
    public void close() {
        reporter.close();
    }

}
