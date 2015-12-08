package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;

import info.novatec.testit.resultrepository.api.interfaces.MetadataKind;


/**
 * Data transfer object (DTO) implementation of a {@link MetadataKind meta data
 * kind}. This class is used to transfer data between different parts of the
 * ResultRepository as well as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "CPD-START", "serial" })
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((creationTimestamp == null) ? 0 : creationTimestamp.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        MetadataKindData other = ( MetadataKindData ) obj;
        if (creationTimestamp == null) {
            if (other.creationTimestamp != null) {
                return false;
            }
        } else if (!creationTimestamp.equals(other.creationTimestamp)) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MetadataKindData [id=" + id + ", name=" + name + ", description=" + description + ", creationTimestamp="
            + creationTimestamp + "]";
    }

}
