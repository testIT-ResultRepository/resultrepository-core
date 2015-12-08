package info.novatec.testit.resultrepository.api.interfaces.entities;

import java.util.Set;

import info.novatec.testit.resultrepository.api.interfaces.Tag;


/**
 * This marker interface declares that an entity has {@link Tag tags}.
 *
 * @since 2.0.0
 */
public interface EntityWithTags {

    /**
     * Returns all of the {@linkplain Tag tags} this entity is linked with.
     *
     * @return the tags of this entity
     * @since 2.0.0
     */
    Set<? extends Tag> getTags();

}
