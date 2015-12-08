package info.novatec.testit.resultrepository.persistence.services.graph.nodes;

import java.util.List;
import java.util.Optional;

import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import info.novatec.testit.resultrepository.api.utils.MetadataPath;
import info.novatec.testit.resultrepository.metrics.api.annotations.Measured;
import info.novatec.testit.resultrepository.persistence.api.exceptions.WrongNodeTypeException;
import info.novatec.testit.resultrepository.persistence.services.graph.Labels;
import info.novatec.testit.resultrepository.persistence.services.graph.RelationshipTypes;


@Service
public class MetadataValueNodeFactory extends AbstractNodeFactory {

    @Autowired
    private MetadataKindNodeFactory metadataKindNodeFactory;

    /**
     * Returns the {@linkplain MetadataValueNode meta data value} for the given
     * {@linkplain MetadataPath meta data path}. If no value for that path
     * exists, one will be created.
     *
     * @param metadataPath the {@linkplain MetadataPath meta data path} to us
     * for the lookup
     * @return the found or created {@linkplain MetadataValueNode meta data
     * value}
     */
    @Measured
    public MetadataValueNode getOrCreateFromGraph(MetadataPath metadataPath) {

        MetadataKindNode metadataKind = metadataKindNodeFactory.getOrCreateFromGraph(metadataPath.getMetadataKind());
        List<String> valueLevels = metadataPath.getValueLevels();

        // start from meta data kind with first value level
        String firstMetaValuePart = valueLevels.get(0);
        MetadataValueNode metadataValue = getOrCreateFirstMetadataValueForMetadataKind(metadataKind, firstMetaValuePart);

        if (valueLevels.size() > 1) {
            /* replace meta data value with each additional level until last
             * level is reached */
            StringBuilder metadataValueString = new StringBuilder(firstMetaValuePart);
            for (int i = 1; i < valueLevels.size(); i++) {
                metadataValueString.append(':');
                metadataValueString.append(valueLevels.get(i));
                metadataValue = getOrCreateSpecializedMetadataValue(metadataValue, metadataValueString.toString());
            }
        }

        return metadataValue;

    }

    private MetadataValueNode getOrCreateFirstMetadataValueForMetadataKind(MetadataKindNode metadataKind,
        String firstMetadataValue) {
        return metadataKind.getDirectMetadataValuesStream()
            .filter(metadataValue -> metadataValue.getValue().equals(firstMetadataValue))
            .findFirst()
            .orElseGet(() -> {

                MetadataValueNode metadataValue = new MetadataValueNode(metadataKind.getGraphDatabase(), firstMetadataValue);
                metadataKind.createUniqueRelationshipTo(metadataValue, RelationshipTypes.HAS_METADATA_VALUE);
                return metadataValue;

            });
    }

    private MetadataValueNode getOrCreateSpecializedMetadataValue(final MetadataValueNode metadataValue,
        final String specializedMetadataValue) {
        return tryToGetMetadataValueViaExistingRelationship(metadataValue, specializedMetadataValue).orElseGet(() -> {

            MetadataValueNode returnValue = new MetadataValueNode(graph(), specializedMetadataValue);
            metadataValue.createUniqueRelationshipTo(returnValue, RelationshipTypes.SPECIALIZES_TO);
            return returnValue;

        });
    }

    private Optional<MetadataValueNode> tryToGetMetadataValueViaExistingRelationship(MetadataValueNode metadataValue,
        String metadataValueString) {
        return metadataValue.getOutgoingRelationships(RelationshipTypes.SPECIALIZES_TO)
            .map(rel -> new MetadataValueNode(rel.getEndNode()))
            .filter(node -> metadataValueString.equals(node.getValue()))
            .findFirst();
    }

    /**
     * Wraps the given {@linkplain Node node} as a {@linkplain MetadataValueNode
     * meta data value} .Validates if the given node has the required label
     * {@linkplain Labels#METADATA_VALUE}.
     *
     * @param node the {@linkplain Node node} to wrap as a
     * {@linkplain MetadataValueNode meta data value}
     * @return the {@linkplain MetadataValueNode meta data value}
     * @throws WrongNodeTypeException if the given node is not labeled a
     * {@linkplain Labels#METADATA_VALUE} node
     */
    public static MetadataValueNode wrap(Node node) {
        return new MetadataValueNode(node);
    }

}
