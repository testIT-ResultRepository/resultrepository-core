package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;

import info.novatec.testit.resultrepository.api.enumerations.DetailType;
import info.novatec.testit.resultrepository.api.interfaces.TestResultDetail;


/**
 * Data transfer object (DTO) implementation of a {@link TestResultDetail test
 * result detail}. This class is used to transfer data between different parts
 * of the ResultRepository as well as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "CPD-START", "serial" })
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((creationTimestamp == null) ? 0 : creationTimestamp.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        TestResultDetailData other = ( TestResultDetailData ) obj;
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
        if (message == null) {
            if (other.message != null) {
                return false;
            }
        } else if (!message.equals(other.message)) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TestResultDetailData [id=" + id + ", type=" + type + ", message=" + message + ", creationTimestamp="
            + creationTimestamp + "]";
    }

}
