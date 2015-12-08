package info.novatec.testit.resultrepository.api.dto.serialization;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import javax.xml.bind.JAXBException;

import org.junit.Test;

import utils.TestUtils;

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


public abstract class AbstractSerializationTest {

    @Test
    public void testSerializationOfTagData() throws Exception {
        TagData tag = TestUtils.defaultTag();
        assertThat(biDirectionalSerialization(tag)).isEqualTo(tag);
        assertThat(biDirectionalSerialization(tag)).isEqualToComparingFieldByField(tag);
    }

    @Test
    public void testSerializationOfMetadataKindData() throws Exception {
        MetadataKindData metadataKind = TestUtils.defaultMetadataKind();
        assertThat(biDirectionalSerialization(metadataKind)).isEqualTo(metadataKind);
        assertThat(biDirectionalSerialization(metadataKind)).isEqualToComparingFieldByField(metadataKind);
    }

    @Test
    public void testSerializationOfMetadataValueData() throws Exception {
        MetadataValueData metadataValue = TestUtils.defaultMetadataValue();
        assertThat(biDirectionalSerialization(metadataValue)).isEqualTo(metadataValue);
        assertThat(biDirectionalSerialization(metadataValue)).isEqualToComparingFieldByField(metadataValue);
    }

    @Test
    public void testSerializationOfBuildJobData() throws Exception {
        BuildJobData buildJob = TestUtils.defaultBuildJob();
        assertThat(biDirectionalSerialization(buildJob)).isEqualTo(buildJob);
        assertThat(biDirectionalSerialization(buildJob)).isEqualToComparingFieldByField(buildJob);
    }

    @Test
    public void testSerializationOfBuildData() throws Exception {
        BuildData build = TestUtils.defaultBuild();
        assertThat(biDirectionalSerialization(build)).isEqualTo(build);
        assertThat(biDirectionalSerialization(build)).isEqualToComparingFieldByField(build);
    }

    @Test
    public void testSerializationOfTestGroupData() throws Exception {
        TestGroupData testGroup = TestUtils.defaultTestGroup();
        assertThat(biDirectionalSerialization(testGroup)).isEqualTo(testGroup);
        assertThat(biDirectionalSerialization(testGroup)).isEqualToComparingFieldByField(testGroup);
    }

    @Test
    public void testSerializationOfTestData() throws Exception {
        TestData test = TestUtils.defaultTest();
        assertThat(biDirectionalSerialization(test)).isEqualTo(test);
        assertThat(biDirectionalSerialization(test)).isEqualToComparingFieldByField(test);
    }

    @Test
    public void testSerializationOfTestGroupResultData() throws Exception {
        TestGroupResultData testGroupResult = TestUtils.defaultTestGroupResult();
        assertThat(biDirectionalSerialization(testGroupResult)).isEqualTo(testGroupResult);
        assertThat(biDirectionalSerialization(testGroupResult)).isEqualToComparingFieldByField(testGroupResult);
    }

    @Test
    public void testSerializationOfTestResultData() throws Exception {
        TestResultData testResult = TestUtils.defaultTestResult();
        assertThat(biDirectionalSerialization(testResult)).isEqualTo(testResult);
        assertThat(biDirectionalSerialization(testResult)).isEqualToComparingFieldByField(testResult);
    }

    @Test
    public void testSerializationOfTestResultDetailData() throws Exception {
        TestResultDetailData testResultDetail = TestUtils.defaultTestResultDetail();
        assertThat(biDirectionalSerialization(testResultDetail)).isEqualTo(testResultDetail);
        assertThat(biDirectionalSerialization(testResultDetail)).isEqualToComparingFieldByField(testResultDetail);
    }

    protected abstract <T> T biDirectionalSerialization(T object) throws IOException, JAXBException;

}
