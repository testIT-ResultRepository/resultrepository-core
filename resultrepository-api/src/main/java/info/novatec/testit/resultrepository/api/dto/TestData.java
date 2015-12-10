package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import info.novatec.testit.resultrepository.api.interfaces.Test;


/**
 * Data transfer object (DTO) implementation of a {@link Test test}. This class
 * is used to transfer data between different parts of the ResultRepository as
 * well as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "serial" })
public class TestData implements Test, Serializable {

    private Long id;
    private String name;
    private Long creationTimestamp;

    public TestData() {
        // needed for serialization(s)
    }

    /**
     * Constructor to be used in order to create a copy of an existing
     * {@linkplain Test}. This is generally used transition database
     * {@linkplain Test} objects to {@linkplain TestData} objects.
     *
     * @param test the {@linkplain Test} to copy as a {@linkplain TestData}
     * object
     */
    public TestData(Test test) {
        id = test.getId();
        name = test.getName();
        creationTimestamp = test.getCreationTimestamp();
    }

    public TestData setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Long getId() {
        return id;
    }

    public TestData setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public TestData setCreationTimestamp(Long timestamp) {
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

        TestData testData = ( TestData ) o;

        return new EqualsBuilder()
            .append(id, testData.id)
            .append(name, testData.name)
            .append(creationTimestamp, testData.creationTimestamp)
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
