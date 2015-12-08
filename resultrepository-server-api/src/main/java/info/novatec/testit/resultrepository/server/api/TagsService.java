package info.novatec.testit.resultrepository.server.api;

import java.util.Set;
import java.util.function.Predicate;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.interfaces.Build;
import info.novatec.testit.resultrepository.api.interfaces.Tag;
import info.novatec.testit.resultrepository.api.interfaces.TestGroupResult;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;
import info.novatec.testit.resultrepository.server.api.exceptions.DataNotFoundException;


/**
 * Services implementing this interface provide operations regarding {@link Tag
 * tags}.
 */
public interface TagsService {

    /**
     * Persists the given {@link TagData tag} if it doesn't already exist. It's
     * existence is determined by its unique value.
     *
     * @param tag the tag to persist
     * @return the persisted tag containing updated information, like it's
     * unique ID, as a new instance
     */
    TagData persist(TagData tag);

    /**
     * Returns all {@link TagData tags} from the repository.
     *
     * @return all tags as a set
     */
    Set<TagData> findAll();

    /**
     * Returns all {@link TagData tags}, which match the given filter, from the
     * repository.
     *
     * @param filter the filter a tag has to match in order to be returned by
     * this method
     * @return all tags as a set
     */
    Set<TagData> findAll(Predicate<Tag> filter);

    /**
     * Looks up a {@link TagData tag} by it's unique ID.
     *
     * @param id the unique ID of the tag
     * @return the found tag
     * @throws DataNotFoundException if no tag for the given ID was found
     */
    TagData findById(Long id) throws DataNotFoundException;

    /**
     * Looks up a {@link TagData tag} by it's unique value.
     *
     * @param value the unique value of the tag
     * @return the found tag
     * @throws DataNotFoundException if no tag for the given value was found
     */
    TagData findByValue(String value) throws DataNotFoundException;

    /**
     * Looks up a {@link TagData tag} by it's unique ID and returns all of it's
     * {@link BuildData builds}.
     *
     * @param tagId the unique ID of the tag
     * @return all builds of the test
     * @throws DataNotFoundException if no tag for the given ID was found
     */
    Set<BuildData> findBuilds(Long tagId) throws DataNotFoundException;

    /**
     * Looks up a {@link TagData tag} by it's unique ID and returns all of it's
     * {@link BuildData builds}.
     *
     * @param tagId the unique ID of the tag
     * @param filter the filter a build has to match in order to be returned by
     * this method
     * @return all builds of the test
     * @throws DataNotFoundException if no tag for the given ID was found
     */
    Set<BuildData> findBuilds(Long tagId, Predicate<Build> filter) throws DataNotFoundException;

    /**
     * Looks up a {@link TagData tag} by it's unique ID and returns all of it's
     * {@link TestGroupResultData test group results}.
     *
     * @param tagId the unique ID of the tag
     * @return all test group results of the test
     * @throws DataNotFoundException if no tag for the given ID was found
     */
    Set<TestGroupResultData> findTestGroupResults(Long tagId) throws DataNotFoundException;

    /**
     * Looks up a {@link TagData tag} by it's unique ID and returns all of it's
     * {@link TestGroupResultData test group results}.
     *
     * @param tagId the unique ID of the tag
     * @param filter the filter a test group result has to match in order to be
     * returned by this method
     * @return all test group results of the test
     * @throws DataNotFoundException if no tag for the given ID was found
     */
    Set<TestGroupResultData> findTestGroupResults(Long tagId, Predicate<TestGroupResult> filter)
        throws DataNotFoundException;

    /**
     * Looks up a {@link TagData tag} by it's unique ID and returns all of it's
     * {@link TestResultData test results}.
     *
     * @param tagId the unique ID of the tag
     * @return all test results of the test
     * @throws DataNotFoundException if no tag for the given ID was found
     */
    Set<TestResultData> findTestResults(Long tagId) throws DataNotFoundException;

    /**
     * Looks up a {@link TagData tag} by it's unique ID and returns all of it's
     * {@link TestResultData test results}.
     *
     * @param tagId the unique ID of the tag
     * @param filter the filter a test result has to match in order to be
     * returned by this method
     * @return all test results of the test
     * @throws DataNotFoundException if no tag for the given ID was found
     */
    Set<TestResultData> findTestResults(Long tagId, Predicate<TestResult> filter) throws DataNotFoundException;

}
