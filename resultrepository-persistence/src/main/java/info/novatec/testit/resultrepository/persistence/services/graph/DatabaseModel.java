package info.novatec.testit.resultrepository.persistence.services.graph;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.PostConstruct;

import org.apache.commons.lang3.mutable.MutableBoolean;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.schema.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import info.novatec.testit.resultrepository.persistence.services.graph.nodes.AbstractNodeWrapper;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildJobNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.BuildNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.MetadataKindNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.MetadataValueNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TagNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestGroupNode;
import info.novatec.testit.resultrepository.persistence.services.graph.nodes.TestNode;


@Component
public class DatabaseModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseModel.class);

    @Autowired
    private GraphDatabaseService graph;

    @PostConstruct
    public void init() {

        LOGGER.info("initializing graph model");

        createIndexIfNotExists(Labels.TAG, TagNode.PROPERTY_VALUE);
        createIndexIfNotExists(Labels.METADATA_KIND, MetadataKindNode.PROPERTY_NAME);
        createIndexIfNotExists(Labels.METADATA_VALUE, MetadataValueNode.PROPERTY_VALUE);
        createIndexIfNotExists(Labels.TEST, TestNode.PROPERTY_NAME);
        createIndexIfNotExists(Labels.TEST, AbstractNodeWrapper.PROPERTY_TIMESTAMP_CREATION);
        createIndexIfNotExists(Labels.TEST_GROUP, TestGroupNode.PROPERTY_NAME);
        createIndexIfNotExists(Labels.TEST_GROUP, AbstractNodeWrapper.PROPERTY_TIMESTAMP_CREATION);
        createIndexIfNotExists(Labels.BUILD, BuildNode.PROPERTY_IDENTIFIER);
        createIndexIfNotExists(Labels.BUILD, AbstractNodeWrapper.PROPERTY_TIMESTAMP_CREATION);
        createIndexIfNotExists(Labels.BUILD_JOB, BuildJobNode.PROPERTY_NAME);

        LOGGER.info("graph model initialized");

    }

    public void createIndexIfNotExists(Label label, String property) {
        try (Transaction tx = graph.beginTx()) {
            Schema schema = graph.schema();
            if (!isPartOfAnyIndexDefinition(schema, label, property)) {
                schema.indexFor(label).on(property).create();
                LOGGER.info("created index for label '{}' and property '{}'", label, property);
            }
            tx.success();
        }
    }

    private boolean isPartOfAnyIndexDefinition(Schema schema, Label label, String property) {
        final MutableBoolean isPartOfAnyIndexDefinition = new MutableBoolean();
        asStream(schema.getIndexes(label)).flatMap(indexDefinition -> asStream(indexDefinition.getPropertyKeys()))
            .filter(indexProperty -> indexProperty.equals(property))
            .findFirst()
            .ifPresent(p -> isPartOfAnyIndexDefinition.setTrue());
        return isPartOfAnyIndexDefinition.booleanValue();
    }

    private <T> Stream<T> asStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

}
