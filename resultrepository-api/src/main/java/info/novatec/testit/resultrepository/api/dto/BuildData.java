package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import info.novatec.testit.resultrepository.api.interfaces.Build;
import info.novatec.testit.resultrepository.api.interfaces.Tag;


/**
 * Data transfer object (DTO) implementation of a {@link Build build}. This
 * class is used to transfer data between different parts of the
 * ResultRepository as well as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "CPD-START", "serial" })
public class BuildData implements Build, Serializable {

    private Long id;
    private BuildJobData buildJob;
    private Integer buildNumber;
    private Long creationTimestamp;

    private Set<TagData> tags = new HashSet<TagData>();
    private Map<String, Object> customProperties = new HashMap<String, Object>();

    public BuildData() {
        // needed for serialization(s)
    }

    /**
     * Constructor to be used in order to create a copy of an existing
     * {@linkplain Build}. This is generally used transition database
     * {@linkplain Build} objects to {@linkplain BuildData} objects.
     *
     * @param build the {@linkplain Build} to copy as a {@linkplain BuildData}
     * object
     */
    public BuildData(Build build) {

        id = build.getId();
        buildJob = new BuildJobData(build.getBuildJob());
        buildNumber = build.getBuildNumber();
        creationTimestamp = build.getCreationTimestamp();

        for (Tag tag : build.getTags()) {
            tags.add(new TagData(tag));
        }

        customProperties.putAll(build.getCustomProperties());

    }

    public BuildData setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Long getId() {
        return id;
    }

    public BuildData setCustomProperties(Map<String, Object> customProperties) {
        this.customProperties = customProperties;
        return this;
    }

    public BuildData putCustomProperty(String key, Object value) {
        this.customProperties.put(key, value);
        return this;
    }

    public BuildData putAllCustomProperty(Map<String, Object> properties) {
        this.customProperties.putAll(properties);
        return this;
    }

    @Override
    public Map<String, Object> getCustomProperties() {
        return customProperties;
    }

    public BuildData setTags(Set<TagData> tags) {
        this.tags = tags;
        return this;
    }

    public BuildData addTag(TagData tag) {
        this.tags.add(tag);
        return this;
    }

    public BuildData addTags(TagData... tagVarargs) {
        return addTags(Arrays.asList(tagVarargs));
    }

    public BuildData addTags(Collection<TagData> tagCollection) {
        this.tags.addAll(tagCollection);
        return this;
    }

    @Override
    public Set<TagData> getTags() {
        return tags;
    }

    public BuildData setBuildJob(BuildJobData buildJob) {
        this.buildJob = buildJob;
        return this;
    }

    @Override
    public BuildJobData getBuildJob() {
        return buildJob;
    }

    public BuildData setBuildNumber(Integer buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }

    @Override
    public Integer getBuildNumber() {
        return buildNumber;
    }

    public BuildData setCreationTimestamp(Long timestamp) {
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
        result = prime * result + ((buildJob == null) ? 0 : buildJob.hashCode());
        result = prime * result + ((buildNumber == null) ? 0 : buildNumber.hashCode());
        result = prime * result + ((creationTimestamp == null) ? 0 : creationTimestamp.hashCode());
        result = prime * result + ((customProperties == null) ? 0 : customProperties.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((tags == null) ? 0 : tags.hashCode());
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
        BuildData other = ( BuildData ) obj;
        if (buildJob == null) {
            if (other.buildJob != null) {
                return false;
            }
        } else if (!buildJob.equals(other.buildJob)) {
            return false;
        }
        if (buildNumber == null) {
            if (other.buildNumber != null) {
                return false;
            }
        } else if (!buildNumber.equals(other.buildNumber)) {
            return false;
        }
        if (creationTimestamp == null) {
            if (other.creationTimestamp != null) {
                return false;
            }
        } else if (!creationTimestamp.equals(other.creationTimestamp)) {
            return false;
        }
        if (customProperties == null) {
            if (other.customProperties != null) {
                return false;
            }
        } else if (!customProperties.equals(other.customProperties)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (tags == null) {
            if (other.tags != null) {
                return false;
            }
        } else if (!tags.equals(other.tags)) {
            return false;
        }
        return true;
    }

    @Override
    @SuppressWarnings("CPD-END")
    public String toString() {
        return "BuildData [id=" + id + ", buildJob=" + buildJob + ", buildNumber=" + buildNumber + ", creationTimestamp="
            + creationTimestamp + ", tags=" + tags + ", customProperties=" + customProperties + "]";
    }

}
