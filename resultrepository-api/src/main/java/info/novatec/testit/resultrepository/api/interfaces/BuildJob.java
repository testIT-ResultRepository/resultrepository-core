package info.novatec.testit.resultrepository.api.interfaces;

import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithID;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithTimestamp;


/**
 * Represents the base build job (i.e. Jenkins Job) who's executions can be
 * tracked as {@link Build build} instances.
 * <p>
 * Instances can be uniquely identified by their name.
 *
 * @since 2.0.0
 */
public interface BuildJob extends EntityWithID, EntityWithTimestamp {

    /**
     * Returns this {@link BuildJob build job's} unique name.
     *
     * @return the name
     * @since 2.0.0
     */
    String getName();

}
