package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;

import info.novatec.testit.resultrepository.api.interfaces.Test;


/**
 * Data transfer object (DTO) implementation of a {@link Test test}. This class
 * is used to transfer data between different parts of the ResultRepository as
 * well as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "CPD-START", "serial" })
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((creationTimestamp == null) ? 0 : creationTimestamp.hashCode());
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
        TestData other = ( TestData ) obj;
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
        return "TestData [id=" + id + ", name=" + name + ", creationTimestamp=" + creationTimestamp + "]";
    }

}
