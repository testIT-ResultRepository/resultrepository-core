package info.novatec.testit.resultrepository.api.interfaces;

import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithID;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithTimestamp;


/**
 * Represents a kind of multi-level meta data. I.e. this could be
 * "operation system" or "browser".
 * <p>
 * TODO: describe the meta data system in more detail
 *
 * @since 2.0.0
 */
public interface MetadataKind extends EntityWithID, EntityWithTimestamp {

    /**
     * Returns the {@link MetadataKind meta data kind's} unique name. This is a
     * required property and may never return null!
     *
     * @return the name of the meta data kind
     * @since 2.0.0
     */
    String getName();

    /**
     * Returns the {@link MetadataKind meta data kind's} description - a more
     * detailed explanation what this meta data kind represents. This is a
     * optional property and may return null!
     *
     * @return the description of the meta data kind
     * @since 2.0.0
     */
    String getDescription();

}
