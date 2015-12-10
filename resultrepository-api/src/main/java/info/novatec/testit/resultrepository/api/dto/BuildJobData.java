package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import info.novatec.testit.resultrepository.api.interfaces.BuildJob;


/**
 * Data transfer object (DTO) implementation of a {@link BuildJob build job}.
 * This class is used to transfer data between different parts of the
 * ResultRepository as well as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "serial" })
public class BuildJobData implements BuildJob, Serializable {

    private Long id;
    private String name;
    private Long creationTimestamp;

    public BuildJobData() {
        // needed for serialization(s)
    }

    /**
     * Constructor to be used in order to create a copy of an existing
     * {@linkplain BuildJob}. This is generally used transition database
     * {@linkplain BuildJob} objects to {@linkplain BuildJobData} objects.
     *
     * @param buildJob the {@linkplain BuildJob} to copy as a
     * {@linkplain BuildJobData} object
     */
    public BuildJobData(BuildJob buildJob) {
        id = buildJob.getId();
        name = buildJob.getName();
        creationTimestamp = buildJob.getCreationTimestamp();
    }

    public BuildJobData setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Long getId() {
        return id;
    }

    public BuildJobData setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public BuildJobData setCreationTimestamp(Long timestamp) {
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
    @SuppressWarnings({ "CPD-START" })
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BuildJobData that = ( BuildJobData ) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(name, that.name)
            .append(creationTimestamp, that.creationTimestamp)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(name)
            .append(creationTimestamp)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("name", name)
            .append("creationTimestamp", creationTimestamp)
            .toString();
    }

}
