package info.novatec.testit.resultrepository.server.api;

import java.util.Set;
import java.util.function.Predicate;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.interfaces.Build;
import info.novatec.testit.resultrepository.api.interfaces.Tag;
import info.novatec.testit.resultrepository.api.interfaces.TestGroupResult;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


/**
 * Services implementing this interface provide operations regarding
 * {@link Build builds}.
 */
public interface BuildsService {

    /**
     * Persists the given {@link BuildData build} if it doesn't already exist.
     * It's existence is determined by the unique name of it's
     * {@link BuildJobData build job} in combination with the build's number.
     *
     * @param build the build to persist
     * @return the persisted build containing updated information, like it's
     * unique ID, as a new instance
     */
    BuildData persist(BuildData build);

    /**
     * Looks up a {@link BuildData build} by it's unique ID.
     *
     * @param buildId the unique ID of the build
     * @return the found build
     * @throws DataNotFoundException if no build for the given ID was found
     */
    BuildData findById(Long buildId) throws DataNotFoundException;

    /**
     * Looks up the {@link BuildJobData build job} of a {@link BuildData build}.
     * The build is identified by it's unique ID. If there is no link to a build
     * job, <code>null</code> is returned.
     *
     * @param buildId the unique ID of the build
     * @return the found build job or <code>null</code>
     * @throws DataNotFoundException if no build for the given ID was found
     */
    BuildJobData findBuildJob(Long buildId) throws DataNotFoundException;

    /**
     * Looks up the {@link TagData tags} of a {@link BuildData build}. The build
     * is identified by it's unique ID.
     *
     * @param buildId the unique ID of the build
     * @return all tags of the build
     * @throws DataNotFoundException if no build for the given ID was found
     */
    Set<TagData> findTags(Long buildId) throws DataNotFoundException;

    /**
     * Looks up the {@link TagData tags} of a {@link BuildData build}. The build
     * is identified by it's unique ID.
     *
     * @param buildId the unique ID of the build
     * @param filter the filter a tag has to match in order to be returned by
     * this method
     * @return all tags of the build
     * @throws DataNotFoundException if no build for the given ID was found
     */
    Set<TagData> findTags(Long buildId, Predicate<Tag> filter) throws DataNotFoundException;

    /**
     * Looks up the {@link TestGroupResultData test group results} of a
     * {@link BuildData build}. The build is identified by it's unique ID.
     *
     * @param buildId the unique ID of the build
     * @return all results of the build
     * @throws DataNotFoundException if no build for the given ID was found
     */
    Set<TestGroupResultData> findTestGroupResults(Long buildId) throws DataNotFoundException;

    /**
     * Looks up the {@link TestGroupResultData test group results} of a
     * {@link BuildData build}. The build is identified by it's unique ID.
     *
     * @param buildId the unique ID of the build
     * @param filter the filter a result has to match in order to be returned by
     * this method
     * @return all results of the build
     * @throws DataNotFoundException if no build for the given ID was found
     */
    Set<TestGroupResultData> findTestGroupResults(Long buildId, Predicate<TestGroupResult> filter)
        throws DataNotFoundException;

}
