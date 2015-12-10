package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import info.novatec.testit.resultrepository.api.interfaces.Tag;


/**
 * Data transfer object (DTO) implementation of a {@link Tag tag}. This class is
 * used to transfer data between different parts of the ResultRepository as well
 * as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "serial" })
public class TagData implements Tag, Serializable {

    private Long id;
    private String value;
    private Long creationTimestamp;

    public TagData() {
        // needed for serialization(s)
    }

    /**
     * Constructor to be used in order to create a copy of an existing
     * {@linkplain Tag}. This is generally used transition database
     * {@linkplain Tag} objects to {@linkplain TagData} objects.
     *
     * @param tag the {@linkplain Tag} to copy as a {@linkplain TagData} object
     */
    public TagData(Tag tag) {
        id = tag.getId();
        value = tag.getValue();
        creationTimestamp = tag.getCreationTimestamp();
    }

    public TagData setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Long getId() {
        return id;
    }

    public TagData setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String getValue() {
        return value;
    }

    public TagData setCreationTimestamp(Long timestamp) {
        this.creationTimestamp = timestamp;
        return this;
    }

    @Override
    public Long getCreationTimestamp() {
        return creationTimestamp;
    }

    public boolean hasCreationTimestamp() {
        return getCreationTimestamp() != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TagData tagData = ( TagData ) o;

        return new EqualsBuilder()
            .append(id, tagData.id)
            .append(value, tagData.value)
            .append(creationTimestamp, tagData.creationTimestamp)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(value)
            .append(creationTimestamp)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("value", value)
            .append("creationTimestamp", creationTimestamp)
            .toString();
    }

}
