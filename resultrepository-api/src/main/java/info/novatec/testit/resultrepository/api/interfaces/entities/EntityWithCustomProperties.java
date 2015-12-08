package info.novatec.testit.resultrepository.api.interfaces.entities;

import java.util.Map;


/**
 * This marker interface declares that an entity provides a custom property
 * space in which the user can persist key value pairs. This space can only
 * handle primitive objects like String, Integer, Long, Float, Double and
 * Boolean!
 *
 * @since 2.0.0
 */
public interface EntityWithCustomProperties {

    /**
     * The custom properties of this entity as a string key to object value map.
     *
     * @return the custom properties
     * @since 2.0.0
     */
    Map<String, Object> getCustomProperties();

}
