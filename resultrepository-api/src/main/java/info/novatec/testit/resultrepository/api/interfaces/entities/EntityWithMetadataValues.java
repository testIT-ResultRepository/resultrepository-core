package info.novatec.testit.resultrepository.api.interfaces.entities;

import java.util.Set;

import info.novatec.testit.resultrepository.api.interfaces.MetadataValue;


/**
 * This marker interface declares that an entity has {@link MetadataValue meta
 * data values}.
 *
 * @since 2.0.0
 */
public interface EntityWithMetadataValues {

    /**
     * Returns the {@link MetadataValue meta data values} of this entity.
     *
     * @return the meta data values
     * @since 2.0.0
     */
    Set<? extends MetadataValue> getMetadataValues();

}
