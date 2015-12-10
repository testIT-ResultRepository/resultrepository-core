package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import info.novatec.testit.resultrepository.api.enumerations.DetailType;
import info.novatec.testit.resultrepository.api.interfaces.TestResultDetail;


/**
 * Data transfer object (DTO) implementation of a {@link TestResultDetail test
 * result detail}. This class is used to transfer data between different parts
 * of the ResultRepository as well as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "serial" })
public class TestResultDetailData implements TestResultDetail, Serializable {

    private Long id;
    private DetailType type = DetailType.INFORMATION;
    private String message;
    private Long creationTimestamp;

    public TestResultDetailData() {
        // needed for serialization(s)
    }

    /**
     * Constructor to be used in order to create a copy of an existing
     * {@linkplain TestResultDetail}. This is generally used transition database
     * {@linkplain TestResultDetail} objects to
     * {@linkplain TestResultDetailData} objects.
     *
     * @param testResultDetail the {@linkplain TestResultDetail} to copy as a
     * {@linkplain TestResultDetailData} object
     */
    public TestResultDetailData(TestResultDetail testResultDetail) {
        id = testResultDetail.getId();
        type = testResultDetail.getType();
        message = testResultDetail.getMessage();
        creationTimestamp = testResultDetail.getCreationTimestamp();
    }

    public TestResultDetailData setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DetailType getType() {
        return type;
    }

    public TestResultDetailData setType(DetailType type) {
        this.type = type;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public TestResultDetailData setMessage(String message) {
        this.message = message;
        return this;
    }

    public TestResultDetailData setCreationTimestamp(Long timestamp) {
        this.creationTimestamp = timestamp;
        return this;
    }

    @Override
    public Long getCreationTimestamp() {
        return creationTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TestResultDetailData that = ( TestResultDetailData ) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(type, that.type)
            .append(message, that.message)
            .append(creationTimestamp, that.creationTimestamp)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(type)
            .append(message)
            .append(creationTimestamp)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("type", type)
            .append("message", message)
            .append("creationTimestamp", creationTimestamp)
            .toString();
    }

}
