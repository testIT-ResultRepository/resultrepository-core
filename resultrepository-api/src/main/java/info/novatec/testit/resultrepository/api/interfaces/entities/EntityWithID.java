package info.novatec.testit.resultrepository.api.interfaces.entities;

/**
 * This marker interface declares that an entity has an ID by which it can be
 * uniquely identified within the system.
 *
 * @since 2.0.0
 */
public interface EntityWithID {

    /**
     * The globally unique ID of this entity.
     *
     * @return the ID
     * @since 2.0.0
     */
    Long getId();

}
