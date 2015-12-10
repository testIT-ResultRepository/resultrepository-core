package info.novatec.testit.resultrepository.persistence.actuator;

import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import info.novatec.testit.resultrepository.persistence.settings.HealthCheckSettings;
import info.novatec.testit.resultrepository.persistence.settings.PersistenceSettings;


@Component
public class PersistenceHealthIndicator implements HealthIndicator {

    private static final String REASON = "reason";
    private static final String DATABASE_NOT_AVAILABLE = "database not available";

    private static final String CLASS = "class";
    private static final String SETTINGS = "settings";

    @Autowired
    private HealthCheckSettings healthCheckSettings;
    @Autowired
    private PersistenceSettings persistenceSettings;

    @Autowired
    private GraphDatabaseService graphService;

    @Override
    public Health health() {

        if (!graphService.isAvailable(healthCheckSettings.getTimeout())) {
            return Health.down().withDetail(REASON, DATABASE_NOT_AVAILABLE).build();
        }

        return Health.up()
            .withDetail(CLASS, graphService.getClass().getName())
            .withDetail(SETTINGS, persistenceSettings)
            .build();

    }

}
