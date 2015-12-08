package info.novatec.testit.resultrepository.persistence.services.graph;

import org.neo4j.graphdb.RelationshipType;


public enum RelationshipTypes implements RelationshipType {

    CONTAINS,

    HAS_METADATA_VALUE,
    SPECIALIZES_TO,
    IS_METADATA_VALUE_OF_TEST_GROUP_RESULT,
    IS_METADATA_VALUE_OF_TEST_RESULT,

    IS_USED_BY_BUILD,
    IS_USED_BY_TEST_GROUP_RESULT,
    IS_USED_BY_TEST_RESULT,

    IS_RESULT_OF_TEST,
    IS_RESULT_OF_TEST_GROUP,

    FIRST_BUILD,
    NEXT_BUILD,
    LAST_BUILD,

    IS_TEST_GROUP_RESULT_OF_BUILD;

}
