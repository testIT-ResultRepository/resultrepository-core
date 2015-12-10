package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import info.novatec.testit.resultrepository.api.interfaces.MetadataValue;


/**
 * Data transfer object (DTO) implementation of a {@link MetadataValue meta data
 * value}. This class is used to transfer data between different parts of the
 * ResultRepository as well as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "serial" })
public class MetadataValueData implements MetadataValue, Serializable {

    private Long id;
    private MetadataKindData metadataKind;
    private String value;
    private Long creationTimestamp;

    public MetadataValueData() {
        // needed for serialization(s)
    }

    /**
     * Constructor to be used in order to create a copy of an existing
     * {@linkplain MetadataValue}. This is generally used transition database
     * {@linkplain MetadataValue} objects to {@linkplain MetadataValueData}
     * objects.
     *
     * @param metadataValue the {@linkplain MetadataValue} to copy as a
     * {@linkplain MetadataValueData} object
     */
    public MetadataValueData(MetadataValue metadataValue) {
        id = metadataValue.getId();
        metadataKind = new MetadataKindData(metadataValue.getMetadataKind());
        value = metadataValue.getValue();
        creationTimestamp = metadataValue.getCreationTimestamp();
    }

    public MetadataValueData setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Long getId() {
        return id;
    }

    public MetadataValueData setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String getValue() {
        return value;
    }

    public MetadataValueData setMetadataKind(MetadataKindData metadataKind) {
        this.metadataKind = metadataKind;
        return this;
    }

    @Override
    public MetadataKindData getMetadataKind() {
        return metadataKind;
    }

    public MetadataValueData setCreationTimestamp(Long timestamp) {
        this.creationTimestamp = timestamp;
        return this;
    }

    @Override
    public Long getCreationTimestamp() {
        return creationTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MetadataValueData that = ( MetadataValueData ) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(metadataKind, that.metadataKind)
            .append(value, that.value)
            .append(creationTimestamp, that.creationTimestamp)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(metadataKind)
            .append(value)
            .append(creationTimestamp)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("metadataKind", metadataKind)
            .append("value", value)
            .append("creationTimestamp", creationTimestamp)
            .toString();
    }

}
