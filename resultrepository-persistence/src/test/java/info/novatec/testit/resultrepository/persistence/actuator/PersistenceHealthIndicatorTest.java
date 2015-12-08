package info.novatec.testit.resultrepository.persistence.actuator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

import info.novatec.testit.resultrepository.persistence.settings.HealthCheckSettings;
import info.novatec.testit.resultrepository.persistence.settings.PersistenceSettings;


@RunWith(MockitoJUnitRunner.class)
public class PersistenceHealthIndicatorTest {

    @Spy
    HealthCheckSettings healthCheckSettings;
    @Spy
    PersistenceSettings persistenceSettings;

    @Mock
    GraphDatabaseService graphService;

    @InjectMocks
    PersistenceHealthIndicator cut;

    @Test
    public void testThatStatusIsDownIfGraphServiceIsNotAvailable() {
        when(graphService.isAvailable(healthCheckSettings.getTimeout())).thenReturn(false);
        Health health = cut.health();
        assertThat(health.getStatus()).isEqualTo(Status.DOWN);
    }

    @Test
    public void testThatStatusIsUpIfGraphServiceIsAvailable() {
        when(graphService.isAvailable(healthCheckSettings.getTimeout())).thenReturn(true);
        Health health = cut.health();
        assertThat(health.getStatus()).isEqualTo(Status.UP);
    }

}
