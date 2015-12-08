package info.novatec.testit.resultrepository.server.api;

import java.util.Set;
import java.util.function.Predicate;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.interfaces.Build;
import info.novatec.testit.resultrepository.api.interfaces.BuildJob;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


/**
 * Services implementing this interface provide operations regarding
 * {@link BuildJob build jobs}.
 */
public interface BuildJobsService {

    /**
     * Persists the given {@link BuildJobData build job} if it doesn't already
     * exist. It's existence is determined by its unique name.
     *
     * @param buildJob the build job to persist
     * @return the persisted build job containing updated information, like it's
     * unique ID, as a new instance
     */
    BuildJobData persist(BuildJobData buildJob);

    /**
     * Returns all {@link BuildJobData build jobs} from the repository.
     *
     * @return all build jobs as a set
     */
    Set<BuildJobData> findAll();

    /**
     * Returns all {@link BuildJobData build jobs} from the repository.
     *
     * @param filter the filter a build job has to match in order to be returned
     * by this method
     * @return all build jobs as a set
     */
    Set<BuildJobData> findAll(Predicate<BuildJob> filter);

    /**
     * Looks up a {@link BuildJobData build job} by it's unique ID.
     *
     * @param buildJobId the unique ID of the build job
     * @return the found build job
     * @throws DataNotFoundException if no build job for the given ID was found
     */
    BuildJobData findById(Long buildJobId) throws DataNotFoundException;

    /**
     * Looks up a {@link BuildJobData build job} by it's unique ID and returns
     * all of it's {@link BuildData builds}.
     *
     * @param buildJobId the unique ID of the build job
     * @return all builds of the build job
     * @throws DataNotFoundException if no build job for the given ID was found
     */
    Set<BuildData> findBuilds(Long buildJobId) throws DataNotFoundException;

    /**
     * Looks up a {@link BuildJobData build job} by it's unique ID and returns
     * all of it's {@link BuildData builds}.
     *
     * @param buildJobId the unique ID of the build job
     * @param filter the filter a build has to match in order to be returned by
     * this method
     * @return all builds of the build job
     * @throws DataNotFoundException if no build job for the given ID was found
     */
    Set<BuildData> findBuilds(Long buildJobId, Predicate<Build> filter) throws DataNotFoundException;

}
