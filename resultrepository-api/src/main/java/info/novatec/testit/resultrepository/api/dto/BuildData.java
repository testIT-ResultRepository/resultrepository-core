package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import info.novatec.testit.resultrepository.api.interfaces.Build;
import info.novatec.testit.resultrepository.api.interfaces.Tag;


/**
 * Data transfer object (DTO) implementation of a {@link Build build}. This
 * class is used to transfer data between different parts of the
 * ResultRepository as well as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "serial" })
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuildData buildData = ( BuildData ) o;

        return new EqualsBuilder()
            .append(id, buildData.id)
            .append(buildJob, buildData.buildJob)
            .append(buildNumber, buildData.buildNumber)
            .append(creationTimestamp, buildData.creationTimestamp)
            .append(tags, buildData.tags)
            .append(customProperties, buildData.customProperties)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(buildJob)
            .append(buildNumber)
            .append(creationTimestamp)
            .append(tags)
            .append(customProperties)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("buildJob", buildJob)
            .append("buildNumber", buildNumber)
            .append("creationTimestamp", creationTimestamp)
            .append("tags", tags)
            .append("customProperties", customProperties)
            .toString();
    }

}
