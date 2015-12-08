package info.novatec.testit.resultrepository.api.statistics;

import info.novatec.testit.resultrepository.api.interfaces.Build;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;


/**
 * This {@link AbstractResultStatistic result statistic} contains statistical
 * information about all the {@link TestResult test results} of a specific
 * {@link Build build}.
 *
 * @since 2.0.0
 */
@SuppressWarnings("serial")
public class BuildResultStatistic extends AbstractResultStatistic {

    /**
     * The unique ID of the {@link Build build} this {@link BuildResultStatistic
     * statistic} is referring to.
     */
    private Long buildId;

    public Long getBuildId() {
        return buildId;
    }

    public void setBuildId(Long buildId) {
        this.buildId = buildId;
    }

}
