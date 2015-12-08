package info.novatec.testit.resultrepository.remote.v1;

import java.util.Set;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.interfaces.BuildJob;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Services implementing this interface provide remote operations regarding
 * {@link BuildJob build jobs}.
 *
 * @since 2.0.0
 */
public interface BuildJobsRemoteService {

    /**
     * Persists the given {@link BuildJobData build job} if it doesn't already
     * exist. It's existence is determined by its unique name.
     *
     * @param buildJob the build job to persist
     * @return the persisted build job containing updated information, like it's
     * unique ID, as a new instance
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    BuildJobData persist(BuildJobData buildJob) throws RemoteOperationException;

    /**
     * Returns all {@link BuildJobData build jobs} from the repository.
     *
     * @return all build jobs as a set
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<BuildJobData> findAll() throws RemoteOperationException;

    /**
     * Looks up a {@link BuildJobData build job} by it's unique ID.
     *
     * @param buildJobId the unique ID of the build job
     * @return the found build job
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    BuildJobData findById(Long buildJobId) throws RemoteOperationException;

    /**
     * Looks up a {@link BuildJobData build job} by it's unique ID and returns
     * all of it's {@link BuildData builds}.
     *
     * @param buildJobId the unique ID of the build job
     * @return all builds of the build job
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<BuildData> findBuilds(Long buildJobId) throws RemoteOperationException;

}
