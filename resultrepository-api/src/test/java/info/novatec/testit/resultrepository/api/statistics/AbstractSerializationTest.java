package info.novatec.testit.resultrepository.api.statistics;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import javax.xml.bind.JAXBException;

import org.junit.Test;


public abstract class AbstractSerializationTest {

    @Test
    public void testSerializationOfBuildStatistic() throws Exception {

        BuildResultStatistic statistic = new BuildResultStatistic();
        statistic.setBuildId(4711L);

        setDefaultStatisticData(statistic);
        assertThat(biDirectionalSerialization(statistic)).isEqualToComparingFieldByField(statistic);

    }

    @Test
    public void testSerializationOfTestGroupResultStatistic() throws Exception {

        TestGroupResultStatistic statistic = new TestGroupResultStatistic();
        statistic.setTestGroupResultId(4711L);

        setDefaultStatisticData(statistic);
        assertThat(biDirectionalSerialization(statistic)).isEqualToComparingFieldByField(statistic);

    }

    @Test
    public void testSerializationOfTestResultStatistic() throws Exception {

        TestResultStatistic statistic = new TestResultStatistic();
        statistic.setTestResultId(4711L);

        setDefaultStatisticData(statistic);
        assertThat(biDirectionalSerialization(statistic)).isEqualToComparingFieldByField(statistic);

    }

    private void setDefaultStatisticData(AbstractResultStatistic statistic) {
        statistic.setNumberOfExceptionTests(1);
        statistic.setNumberOfFailedTests(2);
        statistic.setNumberOfPassedTests(3);
        statistic.setNumberOfSkippedTests(4);
        statistic.setNumberOfUnknownTests(5);
        statistic.setTimestamp(1234567L);
        statistic.setTotalNumberOfTests(15);
        statistic.setTotalTestRuntime(42000);
    }

    protected abstract <T> T biDirectionalSerialization(T object) throws IOException, JAXBException;

}
