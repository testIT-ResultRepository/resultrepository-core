package info.novatec.testit.resultrepository.persistence.services.graph;

import org.neo4j.graphdb.Label;


public enum Labels implements Label {

    TAG,
    TEST,
    TEST_GROUP,

    TEST_GROUP_RESULT,
    TEST_RESULT,
    TEST_RESULT_DETAIL,

    METADATA_KIND,
    METADATA_VALUE,

    BUILD_JOB,
    BUILD;

}
