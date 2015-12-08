package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;

import info.novatec.testit.resultrepository.api.interfaces.Tag;


/**
 * Data transfer object (DTO) implementation of a {@link Tag tag}. This class is
 * used to transfer data between different parts of the ResultRepository as well
 * as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "CPD-START", "serial" })
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((creationTimestamp == null) ? 0 : creationTimestamp.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TagData other = ( TagData ) obj;
        if (creationTimestamp == null) {
            if (other.creationTimestamp != null) {
                return false;
            }
        } else if (!creationTimestamp.equals(other.creationTimestamp)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        } else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TagData [id=" + id + ", value=" + value + ", creationTimestamp=" + creationTimestamp + "]";
    }

}
