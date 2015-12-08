package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import info.novatec.testit.resultrepository.api.enumerations.ResultStatus;
import info.novatec.testit.resultrepository.api.interfaces.MetadataValue;
import info.novatec.testit.resultrepository.api.interfaces.Tag;
import info.novatec.testit.resultrepository.api.interfaces.Test;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;
import info.novatec.testit.resultrepository.api.interfaces.TestResultDetail;


/**
 * Data transfer object (DTO) implementation of a {@link TestResult test result}
 * . This class is used to transfer data between different parts of the
 * ResultRepository as well as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "CPD-START", "serial" })
public class TestResultData implements TestResult, Serializable {

    private Long id;
    private Long duration = 0L;
    private TestData test;
    private Long creationTimestamp;
    private ResultStatus status = ResultStatus.UNKNOWN;

    private Set<TagData> tags = new HashSet<TagData>();
    private Set<MetadataValueData> metadataValues = new HashSet<MetadataValueData>();
    private List<TestResultDetailData> testResultDetails = new LinkedList<TestResultDetailData>();
    private Map<String, Object> customProperties = new HashMap<String, Object>();

    public TestResultData() {
        // needed for serialization(s)
    }

    /**
     * Constructor to be used in order to create a copy of an existing
     * {@linkplain TestResult}. This is generally used transition database
     * {@linkplain TestResult} objects to {@linkplain TestResultData} objects.
     *
     * @param testResult the {@linkplain TestResult} to copy as a
     * {@linkplain TestResultData} object
     */
    public TestResultData(TestResult testResult) {

        id = testResult.getId();
        duration = testResult.getDuration();
        status = testResult.getStatus();
        creationTimestamp = testResult.getCreationTimestamp();

        Test testData = testResult.getTest();
        if (testData != null) {
            test = new TestData(testData);
        }

        for (Tag tag : testResult.getTags()) {
            tags.add(new TagData(tag));
        }

        for (MetadataValue metadataValue : testResult.getMetadataValues()) {
            metadataValues.add(new MetadataValueData(metadataValue));
        }

        for (TestResultDetail testResultDetail : testResult.getTestResultDetails()) {
            testResultDetails.add(new TestResultDetailData(testResultDetail));
        }

        customProperties.putAll(testResult.getCustomProperties());

    }

    public TestResultData setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Long getId() {
        return id;
    }

    public TestResultData setCustomProperties(Map<String, Object> customProperties) {
        this.customProperties = customProperties;
        return this;
    }

    public TestResultData putCustomProperty(String key, Object value) {
        this.customProperties.put(key, value);
        return this;
    }

    public TestResultData putAllCustomProperty(Map<String, Object> properties) {
        this.customProperties.putAll(properties);
        return this;
    }

    @Override
    public Map<String, Object> getCustomProperties() {
        return customProperties;
    }

    public TestResultData setTest(TestData test) {
        this.test = test;
        return this;
    }

    @Override
    public TestData getTest() {
        return test;
    }

    public TestResultData setDuration(Long duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public Long getDuration() {
        return duration;
    }

    public TestResultData setStatus(ResultStatus status) {
        this.status = status;
        return this;
    }

    @Override
    public ResultStatus getStatus() {
        return status;
    }

    public TestResultData setTags(Set<TagData> tags) {
        this.tags = tags;
        return this;
    }

    public TestResultData addTag(TagData tag) {
        this.tags.add(tag);
        return this;
    }

    public TestResultData addTags(TagData... tagVarargs) {
        return addTags(Arrays.asList(tagVarargs));
    }

    public TestResultData addTags(Collection<TagData> tagCollection) {
        this.tags.addAll(tagCollection);
        return this;
    }

    @Override
    public Set<TagData> getTags() {
        return tags;
    }

    public TestResultData setMetadataValues(Set<MetadataValueData> metadataValues) {
        this.metadataValues = metadataValues;
        return this;
    }

    public TestResultData addMetadataValue(MetadataValueData metadataValue) {
        this.metadataValues.add(metadataValue);
        return this;
    }

    public TestResultData addMetadataValues(MetadataValueData... metadataValueVarargs) {
        return addMetadataValues(Arrays.asList(metadataValueVarargs));
    }

    public TestResultData addMetadataValues(Collection<MetadataValueData> metadataValueCollection) {
        this.metadataValues.addAll(metadataValueCollection);
        return this;
    }

    @Override
    public Set<MetadataValueData> getMetadataValues() {
        return metadataValues;
    }

    public TestResultData setTestResultDetails(List<TestResultDetailData> testResultDetails) {
        this.testResultDetails = testResultDetails;
        return this;
    }

    public TestResultData addTestResultDetail(TestResultDetailData testResultDetail) {
        this.testResultDetails.add(testResultDetail);
        return this;
    }

    public TestResultData addTestResultDetails(TestResultDetailData... testResultDetailVarargs) {
        return addTestResultDetails(Arrays.asList(testResultDetailVarargs));
    }

    public TestResultData addTestResultDetails(Collection<TestResultDetailData> testResultDetailCollection) {
        this.testResultDetails.addAll(testResultDetailCollection);
        return this;
    }

    @Override
    public List<TestResultDetailData> getTestResultDetails() {
        return testResultDetails;
    }

    public TestResultData setCreationTimestamp(Long timestamp) {
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
        result = prime * result + ((customProperties == null) ? 0 : customProperties.hashCode());
        result = prime * result + ((duration == null) ? 0 : duration.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((metadataValues == null) ? 0 : metadataValues.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((tags == null) ? 0 : tags.hashCode());
        result = prime * result + ((test == null) ? 0 : test.hashCode());
        result = prime * result + ((testResultDetails == null) ? 0 : testResultDetails.hashCode());
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
        TestResultData other = ( TestResultData ) obj;
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
        if (duration == null) {
            if (other.duration != null) {
                return false;
            }
        } else if (!duration.equals(other.duration)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (metadataValues == null) {
            if (other.metadataValues != null) {
                return false;
            }
        } else if (!metadataValues.equals(other.metadataValues)) {
            return false;
        }
        if (status != other.status) {
            return false;
        }
        if (tags == null) {
            if (other.tags != null) {
                return false;
            }
        } else if (!tags.equals(other.tags)) {
            return false;
        }
        if (test == null) {
            if (other.test != null) {
                return false;
            }
        } else if (!test.equals(other.test)) {
            return false;
        }
        if (testResultDetails == null) {
            if (other.testResultDetails != null) {
                return false;
            }
        } else if (!testResultDetails.equals(other.testResultDetails)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TestResultData [id=" + id + ", duration=" + duration + ", test=" + test + ", creationTimestamp="
            + creationTimestamp + ", status=" + status + ", tags=" + tags + ", metadataValues=" + metadataValues
            + ", testResultDetails=" + testResultDetails + ", customProperties=" + customProperties + "]";
    }

}
