package info.novatec.testit.resultrepository.api.utils;

import java.util.Collections;
import java.util.Comparator;

import info.novatec.testit.resultrepository.api.interfaces.Build;
import info.novatec.testit.resultrepository.api.interfaces.entities.EntityWithTimestamp;


/**
 * Utility class containing all kinds of {@link Comparator comparators} for
 * ResultRepository model classes.
 *
 * @since 2.0.0
 */
public final class Comparators {

    /**
     * A {@link Comparator comparator} which can be used to sort any
     * {@link EntityWithTimestamp} ascending according to their timestamp.
     *
     * @since 2.0.0
     */
    public static final Comparator<EntityWithTimestamp> TIMESTAMP_ASC = new Comparator<EntityWithTimestamp>() {

        @Override
        public int compare(EntityWithTimestamp o1, EntityWithTimestamp o2) {
            long x = o1.getCreationTimestamp();
            long y = o2.getCreationTimestamp();
            return Long.compare(x, y);
        }

    };

    /**
     * A {@link Comparator comparator} which can be used to sort any
     * {@link EntityWithTimestamp} descending according to their timestamp.
     *
     * @since 2.0.0
     */
    public static final Comparator<EntityWithTimestamp> TIMESTAMP_DESC = Collections.reverseOrder(TIMESTAMP_ASC);

    /**
     * A {@link Comparator comparator} which can be used to sort any
     * {@link Build build} ascending according to their build number.
     *
     * @since 2.0.0
     */
    public static final Comparator<Build> BUILD_NUMBER_ASC = new Comparator<Build>() {

        @Override
        public int compare(Build o1, Build o2) {
            long x = o1.getBuildNumber();
            long y = o2.getBuildNumber();
            return Long.compare(x, y);
        }

    };

    /**
     * A {@link Comparator comparator} which can be used to sort any
     * {@link Build build} descending according to their build number.
     *
     * @since 2.0.0
     */
    public static final Comparator<Build> BUILD_NUMBER_DESC = Collections.reverseOrder(BUILD_NUMBER_ASC);

    private Comparators() {
        // utility constructor
    }

}
