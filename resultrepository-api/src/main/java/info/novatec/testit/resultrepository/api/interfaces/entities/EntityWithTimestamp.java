package info.novatec.testit.resultrepository.api.interfaces.entities;

/**
 * This marker interface declares that an entity has timestamp information.
 *
 * @since 2.0.0
 */
public interface EntityWithTimestamp {

    /**
     * The timestamp of when this entity was created.
     *
     * @return the difference, measured in milliseconds, between the current
     * time and midnight, January 1, 1970 UTC.
     * @since 2.0.0
     */
    Long getCreationTimestamp();

}
