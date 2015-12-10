package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import info.novatec.testit.resultrepository.api.interfaces.TestGroup;


/**
 * Data transfer object (DTO) implementation of a {@link TestGroupData test
 * group}. This class is used to transfer data between different parts of the
 * ResultRepository as well as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "serial" })
public class TestGroupData implements TestGroup, Serializable {

    private Long id;
    private String name;
    private Long creationTimestamp;

    public TestGroupData() {
        // needed for serialization(s)
    }

    /**
     * Constructor to be used in order to create a copy of an existing
     * {@linkplain TestGroup}. This is generally used transition database
     * {@linkplain TestGroup} objects to {@linkplain TestGroupData} objects.
     *
     * @param testGroup the {@linkplain TestGroup} to copy as a
     * {@linkplain TestGroupData} object
     */
    public TestGroupData(TestGroup testGroup) {
        id = testGroup.getId();
        name = testGroup.getName();
        creationTimestamp = testGroup.getCreationTimestamp();
    }

    public TestGroupData setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Long getId() {
        return id;
    }

    public TestGroupData setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public TestGroupData setCreationTimestamp(Long timestamp) {
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

        TestGroupData that = ( TestGroupData ) o;

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
