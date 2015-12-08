package info.novatec.testit.resultrepository.remote.v1;

import java.util.Set;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.interfaces.Build;
import info.novatec.testit.resultrepository.api.statistics.BuildResultStatistic;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Services implementing this interface provide remote operations regarding
 * {@link Build builds}.
 *
 * @since 2.0.0
 */
public interface BuildsRemoteService {

    /**
     * Persists the given {@link BuildData build} if it doesn't already exist.
     * It's existence is determined by the unique name of it's
     * {@link BuildJobData build job} in combination with the build's number.
     *
     * @param build the build to persist
     * @return the persisted build containing updated information, like it's
     * unique ID, as a new instance
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    BuildData persist(BuildData build) throws RemoteOperationException;

    /**
     * Looks up a {@link BuildData build} by it's unique ID.
     *
     * @param buildId the unique ID of the build
     * @return the found build
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    BuildData findById(Long buildId) throws RemoteOperationException;

    /**
     * Looks up the {@link BuildJobData build job} of a {@link BuildData build}.
     * The build is identified by it's unique ID. If there is no link to a build
     * job, <code>null</code> is returned.
     *
     * @param buildId the unique ID of the build
     * @return the found build job or <code>null</code>
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    BuildJobData findBuildJob(Long buildId) throws RemoteOperationException;

    /**
     * Looks up the {@link TagData tags} of a {@link BuildData build}. The build
     * is identified by it's unique ID.
     *
     * @param buildId the unique ID of the build
     * @return all tags of the build
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<TagData> findTags(Long buildId) throws RemoteOperationException;

    /**
     * Looks up the {@link TestGroupResultData test group results} of a
     * {@link BuildData build}. The build is identified by it's unique ID.
     *
     * @param buildId the unique ID of the build
     * @return all results of the build
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<TestGroupResultData> findTestGroupResults(Long buildId) throws RemoteOperationException;

    /**
     * Calculates a {@link BuildResultStatistic statistic} of a {@link BuildData
     * build}. The build is identified by it's unique ID.
     *
     * @param buildId the unique ID of the build
     * @return the calculated statistic
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    BuildResultStatistic calculateStatistic(Long buildId) throws RemoteOperationException;

}
