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
@SuppressWarnings({ "serial" })
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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TestGroupResultData that = ( TestGroupResultData ) o;

        return new EqualsBuilder()
            .append(id, that.id)
            .append(build, that.build)
            .append(testGroup, that.testGroup)
            .append(creationTimestamp, that.creationTimestamp)
            .append(tags, that.tags)
            .append(metadataValues, that.metadataValues)
            .append(testResults, that.testResults)
            .append(customProperties, that.customProperties)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(build)
            .append(testGroup)
            .append(creationTimestamp)
            .append(tags)
            .append(metadataValues)
            .append(testResults)
            .append(customProperties)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("build", build)
            .append("testGroup", testGroup)
            .append("creationTimestamp", creationTimestamp)
            .append("tags", tags)
            .append("metadataValues", metadataValues)
            .append("testResults", testResults)
            .append("customProperties", customProperties)
            .toString();
    }

}
