package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.assertj.core.util.Sets;

import info.novatec.testit.resultrepository.api.dto.BuildData;
import info.novatec.testit.resultrepository.api.dto.BuildJobData;
import info.novatec.testit.resultrepository.api.dto.MetadataKindData;
import info.novatec.testit.resultrepository.api.dto.MetadataValueData;
import info.novatec.testit.resultrepository.api.dto.TagData;
import info.novatec.testit.resultrepository.api.dto.TestData;
import info.novatec.testit.resultrepository.api.dto.TestGroupData;
import info.novatec.testit.resultrepository.api.dto.TestGroupResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultData;
import info.novatec.testit.resultrepository.api.dto.TestResultDetailData;
import info.novatec.testit.resultrepository.api.enumerations.DetailType;
import info.novatec.testit.resultrepository.api.enumerations.ResultStatus;


public final class TestUtils {

    public static TagData defaultTag() {
        TagData tag = new TagData();
        tag.setId(42L);
        tag.setValue("tag");
        tag.setCreationTimestamp(300L);
        return tag;
    }

    public static Set<TagData> defaultTags() {
        return Sets.newLinkedHashSet(defaultTag(), defaultTag());
    }

    public static MetadataKindData defaultMetadataKind() {
        MetadataKindData metadataKind = new MetadataKindData();
        metadataKind.setDescription("foo bar");
        metadataKind.setId(42L);
        metadataKind.setName("metadata kind");
        metadataKind.setCreationTimestamp(100L);
        return metadataKind;
    }

    public static MetadataValueData defaultMetadataValue() {
        MetadataValueData metadataValue = new MetadataValueData();
        metadataValue.setId(42L);
        metadataValue.setMetadataKind(defaultMetadataKind());
        metadataValue.setValue("foo:bar");
        metadataValue.setCreationTimestamp(200L);
        return metadataValue;
    }

    public static Set<MetadataValueData> defaultMetadataValues() {
        return Sets.newLinkedHashSet(defaultMetadataValue(), defaultMetadataValue());
    }

    public static BuildJobData defaultBuildJob() {
        BuildJobData buildJob = new BuildJobData();
        buildJob.setId(42L);
        buildJob.setName("build job");
        buildJob.setCreationTimestamp(48L);
        return buildJob;
    }

    public static BuildData defaultBuild() {
        BuildData build = new BuildData();
        build.setId(42L);
        build.setBuildJob(defaultBuildJob());
        build.setBuildNumber(1);
        build.setCreationTimestamp(System.currentTimeMillis());
        build.setTags(defaultTags());
        build.setCustomProperties(defaultCustomProperties());
        return build;
    }

    public static TestGroupData defaultTestGroup() {
        TestGroupData testGroup = new TestGroupData();
        testGroup.setId(42L);
        testGroup.setName("testGroup");
        testGroup.setCreationTimestamp(600L);
        return testGroup;
    }

    public static TestData defaultTest() {
        TestData test = new TestData();
        test.setId(42L);
        test.setName("test");
        test.setCreationTimestamp(400L);
        return test;
    }

    public static TestGroupResultData defaultTestGroupResult() {
        TestGroupResultData testGroupResult = new TestGroupResultData();
        testGroupResult.setBuild(defaultBuild());
        testGroupResult.setCreationTimestamp(System.currentTimeMillis());
        testGroupResult.setCustomProperties(defaultCustomProperties());
        testGroupResult.setMetadataValues(defaultMetadataValues());
        testGroupResult.setTags(defaultTags());
        testGroupResult.setTestGroup(defaultTestGroup());
        testGroupResult.setId(42L);
        testGroupResult.setTestResults(defaultTestResults());
        return testGroupResult;
    }

    public static TestResultData defaultTestResult() {
        TestResultData testResult = new TestResultData();
        testResult.setCreationTimestamp(System.currentTimeMillis());
        testResult.setCustomProperties(defaultCustomProperties());
        testResult.setDuration(100L);
        testResult.setId(42L);
        testResult.setMetadataValues(defaultMetadataValues());
        testResult.setStatus(ResultStatus.PASSED);
        testResult.setTags(defaultTags());
        testResult.setTest(defaultTest());
        testResult.setTestResultDetails(defaultTestResultDetails());
        return testResult;
    }

    public static Set<TestResultData> defaultTestResults() {
        return Sets.newLinkedHashSet(defaultTestResult(), defaultTestResult());
    }

    public static TestResultDetailData defaultTestResultDetail() {
        TestResultDetailData testResultDetail = new TestResultDetailData();
        testResultDetail.setCreationTimestamp(System.currentTimeMillis());
        testResultDetail.setId(42L);
        testResultDetail.setMessage("foo bar");
        testResultDetail.setType(DetailType.SUCCESS);
        return testResultDetail;
    }

    public static List<TestResultDetailData> defaultTestResultDetails() {
        return Arrays.asList(defaultTestResultDetail(), defaultTestResultDetail());
    }

    public static Map<String, Object> defaultCustomProperties() {
        Map<String, Object> customProperties = new HashMap<String, Object>();
        customProperties.put("string", "foo bar");
        customProperties.put("int", 1);
        customProperties.put("long", 2);
        customProperties.put("float", 3.0);
        customProperties.put("double", 4.0);
        customProperties.put("boolean", true);
        return customProperties;
    }

    private TestUtils() {
        // utility constructor
    }

}
