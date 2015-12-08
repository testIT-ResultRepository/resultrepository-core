package info.novatec.testit.resultrepository.api.utils;

import org.junit.Assert;
import org.junit.Test;

import info.novatec.testit.resultrepository.api.dto.MetadataKindData;
import info.novatec.testit.resultrepository.api.dto.MetadataValueData;
import info.novatec.testit.resultrepository.api.exceptions.IllegalMetadataFormatException;


public class MetadataPathTest {

    @Test
    public final void testMetadataPathBuilder_ConstructionWithStrings_OneMetadataValueLevel_PathCreated() {
        MetadataPath builder = new MetadataPath("os", "windows");
        Assert.assertEquals("[os]windows", builder.toPathString());
    }

    @Test
    public final void testMetadataPathBuilder_ConstructionWithStrings_TwoMetadataValueLevel_PathCreated() {
        MetadataPath builder = new MetadataPath("os", "windows", "7");
        Assert.assertEquals("[os]windows:7", builder.toPathString());
    }

    @Test
    public final void testMetadataPathBuilder_ConstructionWithStrings_ThreeMetadataValueLevel_PathCreated() {
        MetadataPath builder = new MetadataPath("os", "windows", "7", "sp1");
        Assert.assertEquals("[os]windows:7:sp1", builder.toPathString());
    }

    @Test
    public final void testMetadataPathBuilder_ConstructionWithStrings_UpperCaseKindAndValues_KindAndValuesLowerCasedAndPathCreated() {
        MetadataPath builder = new MetadataPath("OS", "WINDOWS", "7", "SP1");
        Assert.assertEquals("[os]windows:7:sp1", builder.toPathString());
    }

    @Test(expected = IllegalMetadataFormatException.class)
    public final void testMetadataPathBuilder_ConstructionWithStrings_NoValues_Exception() {
        new MetadataPath("os").toPathString();
    }

    @Test(expected = IllegalMetadataFormatException.class)
    public final void testMetadataPathBuilder_ConstructionWithStrings_EmptyStringKind_Exception() {
        new MetadataPath("").toPathString();
    }

    @Test(expected = IllegalMetadataFormatException.class)
    public final void testMetadataPathBuilder_ConstructionWithStrings_EmptyStringValue_Exception() {
        new MetadataPath("os", "").toPathString();
    }

    @Test(expected = IllegalMetadataFormatException.class)
    public final void testMetadataPathBuilder_ConstructionWithStrings_NullValues_Exception() {
        new MetadataPath("os", ( String ) null).toPathString();
    }

    @Test
    public final void testMetadataPathBuilder_ConstructionWithMetadataValueObject_OneMetadataValueLevel_PathCreated() {

        MetadataValueData mvd = metadata("os", "windows");

        MetadataPath builder = MetadataPath.from(mvd);
        Assert.assertEquals("[os]windows", builder.toPathString());

    }

    @Test
    public final void testMetadataPathBuilder_ConstructionWithMetadataValueObject_TwoMetadataValueLevel_PathCreated() {

        MetadataValueData mvd = metadata("os", "windows:7");

        MetadataPath builder = MetadataPath.from(mvd);
        Assert.assertEquals("[os]windows:7", builder.toPathString());

    }

    @Test
    public final void testMetadataPathBuilder_ConstructionWithMetadataValueObject_ThreeMetadataValueLevel_PathCreated() {

        MetadataValueData mvd = metadata("os", "windows:7:sp1");

        MetadataPath builder = MetadataPath.from(mvd);
        Assert.assertEquals("[os]windows:7:sp1", builder.toPathString());

    }

    @Test
    public final void testMetadataPathBuilder_ConstructionWithMetadataValueObject_UpperCaseKindAndValues_KindAndValuesLowerCasedAndPathCreated() {

        MetadataValueData mvd = metadata("OS", "WINDOWS:7:SP1");

        MetadataPath builder = MetadataPath.from(mvd);
        Assert.assertEquals("[os]windows:7:sp1", builder.toPathString());

    }

    @Test
    public final void testAddLevel_NoValuesFromConstruction_addOne_PathCreated() {
        MetadataPath builder = new MetadataPath("os");
        builder.addLevel("windows");
        Assert.assertEquals("[os]windows", builder.toPathString());
    }

    @Test
    public final void testAddLevel_NoValuesFromConstruction_addTwo_PathCreated() {
        MetadataPath builder = new MetadataPath("os");
        builder.addLevel("windows");
        builder.addLevel("7");
        Assert.assertEquals("[os]windows:7", builder.toPathString());
    }

    @Test
    public final void testAddLevel_ValueFromConstruction_addOne_PathCreated() {
        MetadataPath builder = new MetadataPath("os", "windows");
        builder.addLevel("7");
        Assert.assertEquals("[os]windows:7", builder.toPathString());
    }

    /* static toString */

    @Test
    public final void testStaticToString_EmptyArray_NoPathStringsCreated() {

        String[] expectedArray = new String[0];

        MetadataPath[] inputArray = new MetadataPath[0];
        String[] actualArray = MetadataPath.toPathStrings(inputArray);
        Assert.assertArrayEquals(expectedArray, actualArray);

    }

    @Test
    public final void testStaticToString_ArrayWithMetadataPathBuilders_PathStringsCreated() {

        String[] expectedArray = new String[] { "[os]windows", "[os]windows:7:sp1" };

        MetadataPath builder1 = new MetadataPath("os", "windows");
        MetadataPath builder2 = new MetadataPath("os", "windows", "7", "sp1");

        MetadataPath[] inputArray = new MetadataPath[] { builder1, builder2 };
        String[] actualArray = MetadataPath.toPathStrings(inputArray);
        Assert.assertArrayEquals(expectedArray, actualArray);

    }

    private static MetadataValueData metadata(String name, String value) {
        return new MetadataValueData().setMetadataKind(new MetadataKindData().setName(name)).setValue(value);
    }

}
