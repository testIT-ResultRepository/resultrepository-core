package info.novatec.testit.resultrepository.api.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithTimestamp;


public class ComparatorsTest {

    @Test
    public void testSortingOfEntityWithTimestamp_ASC() {

        EntityWithTimestamp entity1 = entityWithTimestamp(100L);
        EntityWithTimestamp entity2 = entityWithTimestamp(200L);
        EntityWithTimestamp entity3 = entityWithTimestamp(300L);

        List<EntityWithTimestamp> list = Arrays.asList(entity2, entity3, entity1);
        Collections.sort(list, Comparators.TIMESTAMP_ASC);

        assertThat(list).containsExactly(entity1, entity2, entity3);

    }

    @Test
    public void testSortingOfEntityWithTimestamp_DESC() {

        EntityWithTimestamp entity1 = entityWithTimestamp(100L);
        EntityWithTimestamp entity2 = entityWithTimestamp(200L);
        EntityWithTimestamp entity3 = entityWithTimestamp(300L);

        List<EntityWithTimestamp> list = Arrays.asList(entity2, entity3, entity1);
        Collections.sort(list, Comparators.TIMESTAMP_DESC);

        assertThat(list).containsExactly(entity3, entity2, entity1);

    }

    @Test
    public void testSortingOfBuildsByNumber_ASC() {

        BuildData build1 = new BuildData().setBuildNumber(1);
        BuildData build2 = new BuildData().setBuildNumber(2);
        BuildData build3 = new BuildData().setBuildNumber(3);

        List<BuildData> list = Arrays.asList(build2, build3, build1);
        Collections.sort(list, Comparators.BUILD_NUMBER_ASC);

        assertThat(list).containsExactly(build1, build2, build3);

    }

    @Test
    public void testSortingOfBuildsByNumber_DESC() {

        BuildData build1 = new BuildData().setBuildNumber(1);
        BuildData build2 = new BuildData().setBuildNumber(2);
        BuildData build3 = new BuildData().setBuildNumber(3);

        List<BuildData> list = Arrays.asList(build2, build3, build1);
        Collections.sort(list, Comparators.BUILD_NUMBER_DESC);

        assertThat(list).containsExactly(build3, build2, build1);

    }

    private EntityWithTimestamp entityWithTimestamp(Long timestamp) {
        EntityWithTimestamp entity = mock(EntityWithTimestamp.class);
        doReturn(timestamp).when(entity).getCreationTimestamp();
        return entity;
    }

}
