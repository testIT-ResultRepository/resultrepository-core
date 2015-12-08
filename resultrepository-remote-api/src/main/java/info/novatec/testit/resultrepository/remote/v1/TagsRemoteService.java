package info.novatec.testit.resultrepository.remote.v1;

import java.util.Set;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.interfaces.Tag;
import info.novatec.testit.resultrepository.remote.v1.descriptors.TagDescriptor;
import info.novatec.testit.resultrepository.remote.v1.exceptions.RemoteOperationException;


/**
 * Services implementing this interface provide remote operations regarding
 * {@link Tag tags}.
 *
 * @since 2.0.0
 */
public interface TagsRemoteService {

    /**
     * Persists the given {@link TagData tag} if it doesn't already exist. It's
     * existence is determined by its unique value.
     *
     * @param tag the tag to persist
     * @return the persisted tag containing updated information, like it's
     * unique ID, as a new instance
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    TagData persist(TagData tag) throws RemoteOperationException;

    /**
     * Returns all {@link TagData tags} from the repository.
     *
     * @return all tags as a set
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<TagData> findAll() throws RemoteOperationException;

    /**
     * Returns all {@link TagData tags}, which match the given
     * {@link TagDescriptor descriptor}, from the repository.
     *
     * @param descriptor the descriptor to use when evaluating if a tag matches
     * @return all matching tags as a set
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<TagData> findMatching(TagDescriptor descriptor) throws RemoteOperationException;

    /**
     * Looks up a {@link TagData tag} by it's unique ID.
     *
     * @param tagId the unique ID of the tag
     * @return the found tag
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    TagData findById(Long tagId) throws RemoteOperationException;

    /**
     * Looks up a {@link TagData tag} by it's unique ID and returns all of it's
     * {@link BuildData builds}.
     *
     * @param tagId the unique ID of the tag
     * @return all builds of the test
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<BuildData> findBuilds(Long tagId) throws RemoteOperationException;

    /**
     * Looks up a {@link TagData tag} by it's unique ID and returns all of it's
     * {@link TestGroupResultData test group results}.
     *
     * @param tagId the unique ID of the tag
     * @return all test group results of the test
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<TestGroupResultData> findTestGroupResults(Long tagId) throws RemoteOperationException;

    /**
     * Looks up a {@link TagData tag} by it's unique ID and returns all of it's
     * {@link TestResultData test results}.
     *
     * @param tagId the unique ID of the tag
     * @return all test results of the test
     * @throws RemoteOperationException if anything goes wrong while executing
     * the remote operation
     * @since 2.0.0
     */
    Set<TestResultData> findTestResults(Long tagId) throws RemoteOperationException;

}
