package info.novatec.testit.resultrepository.api.interfaces;

import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithCustomProperties;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithID;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithTags;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithTimestamp;


/**
 * Represents an execution of a {@link BuildJob build job} (i.e. Jenkins Job).
 * <p>
 * Instances can be uniquely identified by their build job's name and the
 * build's number.
 *
 * @since 2.0.0
 */
public interface Build extends EntityWithTags, EntityWithID, EntityWithTimestamp, EntityWithCustomProperties {

    /**
     * Returns this {@link Build build's} {@link BuildJob job}. This may return
     * null in rare instances where there is no job for a build.
     *
     * @return the build job - may be null
     * @since 2.0.0
     */
    BuildJob getBuildJob();

    /**
     * Returns this {@link Build build's} unique number in relation to it's
     * {@link BuildJob job}. This is a required property and may never return
     * null!
     *
     * @return the build number
     * @since 2.0.0
     */
    Integer getBuildNumber();

}
