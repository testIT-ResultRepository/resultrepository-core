package info.novatec.testit.resultrepository.api.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import info.novatec.testit.resultrepository.api.interfaces.Build;
import info.novatec.testit.resultrepository.api.interfaces.MetadataValue;
import info.novatec.testit.resultrepository.api.interfaces.Tag;
import info.novatec.testit.resultrepository.api.interfaces.TestGroup;
import info.novatec.testit.resultrepository.api.interfaces.TestGroupResult;
import info.novatec.testit.resultrepository.api.interfaces.TestResult;


/**
 * Data transfer object (DTO) implementation of a {@link TestGroupResult test
 * group result}. This class is used to transfer data between different parts of
 * the ResultRepository as well as from / to different application.
 *
 * @since 2.0.0
 */
@SuppressWarnings({ "CPD-START", "serial" })
public class TestGroupResultData implements TestGroupResult, Serializable {

    private Long id;
    private BuildData build;
    private TestGroupData testGroup;
    private Long creationTimestamp;

    private Set<TagData> tags = new HashSet<TagData>();
    private Set<MetadataValueData> metadataValues = new HashSet<MetadataValueData>();
    private Set<TestResultData> testResults = new HashSet<TestResultData>();
    private Map<String, Object> customProperties = new HashMap<String, Object>();

    public TestGroupResultData() {
        // needed for serialization(s)
    }

    /**
     * Constructor to be used in order to create a copy of an existing
     * {@linkplain TestGroupResult}. This is generally used transition database
     * {@linkplain TestGroupResult} objects to {@linkplain TestGroupResultData}
     * objects.
     *
     * @param testGroupResult the {@linkplain TestGroupResult} to copy as a
     * {@linkplain TestGroupResultData} object
     */
    public TestGroupResultData(TestGroupResult testGroupResult) {

        id = testGroupResult.getId();
        creationTimestamp = testGroupResult.getCreationTimestamp();

        TestGroup testGroupData = testGroupResult.getTestGroup();
        if (testGroupData != null) {
            testGroup = new TestGroupData(testGroupData);
        }

        Build buildData = testGroupResult.getBuild();
        if (buildData != null) {
            build = new BuildData(buildData);
        }

        for (Tag tag : testGroupResult.getTags()) {
            tags.add(new TagData(tag));
        }

        for (MetadataValue metadataValue : testGroupResult.getMetadataValues()) {
            metadataValues.add(new MetadataValueData(metadataValue));
        }

        for (TestResult testResult : testGroupResult.getTestResults()) {
            testResults.add(new TestResultData(testResult));
        }

        customProperties.putAll(testGroupResult.getCustomProperties());

    }

    public TestGroupResultData setId(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Long getId() {
        return id;
    }

    public TestGroupResultData setTestGroup(TestGroupData testGroup) {
        this.testGroup = testGroup;
        return this;
    }

    @Override
    public TestGroupData getTestGroup() {
        return testGroup;
    }

    public TestGroupResultData setCreationTimestamp(Long timestamp) {
        this.creationTimestamp = timestamp;
        return this;
    }

    @Override
    public Long getCreationTimestamp() {
        return creationTimestamp;
    }

    public TestGroupResultData setBuild(BuildData build) {
        this.build = build;
        return this;
    }

    @Override
    public BuildData getBuild() {
        return build;
    }

    public TestGroupResultData setCustomProperties(Map<String, Object> customProperties) {
        this.customProperties = customProperties;
        return this;
    }

    public TestGroupResultData putCustomProperty(String key, Object value) {
        this.customProperties.put(key, value);
        return this;
    }

    public TestGroupResultData putAllCustomProperty(Map<String, Object> properties) {
        this.customProperties.putAll(properties);
        return this;
    }

    @Override
    public Map<String, Object> getCustomProperties() {
        return customProperties;
    }

    public TestGroupResultData setTags(Set<TagData> tags) {
        this.tags = tags;
        return this;
    }

    public TestGroupResultData addTag(TagData tag) {
        this.tags.add(tag);
        return this;
    }

    public TestGroupResultData addTags(TagData... tagVarargs) {
        return addTags(Arrays.asList(tagVarargs));
    }

    public TestGroupResultData addTags(Collection<TagData> tagCollection) {
        this.tags.addAll(tagCollection);
        return this;
    }

    @Override
    public Set<TagData> getTags() {
        return tags;
    }

    public TestGroupResultData setMetadataValues(Set<MetadataValueData> metadataValues) {
        this.metadataValues = metadataValues;
        return this;
    }

    public TestGroupResultData addMetadataValue(MetadataValueData metadataValue) {
        this.metadataValues.add(metadataValue);
        return this;
    }

    public TestGroupResultData addMetadataValues(MetadataValueData... metadataValueVarargs) {
        return addMetadataValues(Arrays.asList(metadataValueVarargs));
    }

    public TestGroupResultData addMetadataValues(Collection<MetadataValueData> metadataValueCollection) {
        this.metadataValues.addAll(metadataValueCollection);
        return this;
    }

    @Override
    public Set<MetadataValueData> getMetadataValues() {
        return metadataValues;
    }

    public TestGroupResultData setTestResults(Set<TestResultData> testResults) {
        this.testResults = testResults;
        return this;
    }

    public TestGroupResultData addTestResult(TestResultData testResult) {
        this.testResults.add(testResult);
        return this;
    }

    public TestGroupResultData addTestResults(TestResultData... testResultVarargs) {
        return addTestResults(Arrays.asList(testResultVarargs));
    }

    public TestGroupResultData addTestResults(Collection<TestResultData> testResultCollection) {
        this.testResults.addAll(testResultCollection);
        return this;
    }

    @Override
    public Set<TestResultData> getTestResults() {
        return testResults;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((build == null) ? 0 : build.hashCode());
        result = prime * result + ((creationTimestamp == null) ? 0 : creationTimestamp.hashCode());
        result = prime * result + ((customProperties == null) ? 0 : customProperties.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((metadataValues == null) ? 0 : metadataValues.hashCode());
        result = prime * result + ((tags == null) ? 0 : tags.hashCode());
        result = prime * result + ((testGroup == null) ? 0 : testGroup.hashCode());
        result = prime * result + ((testResults == null) ? 0 : testResults.hashCode());
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
        TestGroupResultData other = ( TestGroupResultData ) obj;
        if (build == null) {
            if (other.build != null) {
                return false;
            }
        } else if (!build.equals(other.build)) {
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
        if (metadataValues == null) {
            if (other.metadataValues != null) {
                return false;
            }
        } else if (!metadataValues.equals(other.metadataValues)) {
            return false;
        }
        if (tags == null) {
            if (other.tags != null) {
                return false;
            }
        } else if (!tags.equals(other.tags)) {
            return false;
        }
        if (testGroup == null) {
            if (other.testGroup != null) {
                return false;
            }
        } else if (!testGroup.equals(other.testGroup)) {
            return false;
        }
        if (testResults == null) {
            if (other.testResults != null) {
                return false;
            }
        } else if (!testResults.equals(other.testResults)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TestGroupResultData [id=" + id + ", build=" + build + ", testGroup=" + testGroup + ", creationTimestamp="
            + creationTimestamp + ", tags=" + tags + ", metadataValues=" + metadataValues + ", testResults=" + testResults
            + ", customProperties=" + customProperties + "]";
    }

}
