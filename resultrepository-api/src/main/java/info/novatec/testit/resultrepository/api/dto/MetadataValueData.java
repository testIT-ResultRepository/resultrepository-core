package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;

import info.novatec.testit.resultrepository.api.interfaces.MetadataValue;


/**
 * Data transfer object (DTO) implementation of a {@link MetadataValue meta data
 * value}. This class is used to transfer data between different parts of the
 * ResultRepository as well as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "CPD-START", "serial" })
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((creationTimestamp == null) ? 0 : creationTimestamp.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((metadataKind == null) ? 0 : metadataKind.hashCode());
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
        MetadataValueData other = ( MetadataValueData ) obj;
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
        if (metadataKind == null) {
            if (other.metadataKind != null) {
                return false;
            }
        } else if (!metadataKind.equals(other.metadataKind)) {
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
        return "MetadataValueData [id=" + id + ", metadataKind=" + metadataKind + ", value=" + value + ", creationTimestamp="
            + creationTimestamp + "]";
    }

}
