package info.novatec.testit.resultrepository.api.interfaces;

import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithID;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithTimestamp;


/**
 * Represents a specific value of a given multi-level {@link MetadataKind meta
 * data kind}. I.e. this could be "windows:7:sp1" for the meta data kind
 * "operation system".
 * <p>
 * TODO: link to a more detailed description of the meta data system
 *
 * @since 2.0.0
 */
public interface MetadataValue extends EntityWithID, EntityWithTimestamp {

    /**
     * Returns the {@link MetadataKind meta data kind} this {@link MetadataValue
     * value} belongs to. This may return null in rare instances where there is
     * no meta data kind for a value.
     *
     * @return the meta data kind - may be null
     * @since 2.0.0
     */
    MetadataKind getMetadataKind();

    /**
     * Returns this {@link MetadataValue meta data value's} string value.
     * Depending on its level it might consist of several single values
     * concatenated with ':'. This is a required property and may never return
     * null!
     *
     * @return the value
     * @since 2.0.0
     */
    String getValue();

}
