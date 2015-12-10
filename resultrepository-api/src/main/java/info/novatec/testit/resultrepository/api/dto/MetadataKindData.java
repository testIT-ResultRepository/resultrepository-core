package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import info.novatec.testit.resultrepository.api.interfaces.MetadataKind;


/**
 * Data transfer object (DTO) implementation of a {@link MetadataKind meta data
 * kind}. This class is used to transfer data between different parts of the
 * ResultRepository as well as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "serial" })
public class MetadataKindData implements MetadataKind, Serializable {

    private Long id;
    private String name;
    private String description;
    private Long creationTimestamp;

    public MetadataKindData() {
        // needed for serialization(s)
    }

    /**
     * Constructor to be used in order to create a copy of an existing
     * {@linkplain MetadataKind}. This is generally used transition database
     * {@linkplain MetadataKind} objects to {@linkplain MetadataKindData}
     * objects.
     *
     * @param metadataKind the {@linkplain MetadataKind} to copy as a
     * {@linkplain MetadataKindData} object
     */
    public MetadataKindData(MetadataKind metadataKind) {
        id = metadataKind.getId();
        name = metadataKind.getName();
        description = metadataKind.getDescription();
        creationTimestamp = metadataKind.getCreationTimestamp();
    }

    public MetadataKindData setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Long getId() {
        return id;
    }

    public MetadataKindData setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public MetadataKindData setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public MetadataKindData setCreationTimestamp(Long timestamp) {
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

        MetadataKindData that = ( MetadataKindData ) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(name, that.name)
            .append(description, that.description)
            .append(creationTimestamp, that.creationTimestamp)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(name)
            .append(description)
            .append(creationTimestamp)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("name", name)
            .append("description", description)
            .append("creationTimestamp", creationTimestamp)
            .toString();
    }

}
