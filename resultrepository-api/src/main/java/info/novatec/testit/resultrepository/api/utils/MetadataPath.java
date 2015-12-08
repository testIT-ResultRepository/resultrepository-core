package info.novatec.testit.resultrepository.api.utils;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import info.novatec.testit.resultrepository.api.exceptions.IllegalMetadataFormatException;
import info.novatec.testit.resultrepository.api.interfaces.MetadataValue;


public class MetadataPath {

    public static final String PATTERN_METADATAKIND = "\\w(\\s|\\w|_|\\.|-)*\\w";
    public static final String PATTERN_METADATAVALUE = "\\w(\\s|\\w|_|\\.|-)*";

    public static final String PATTERN_METADATAPATH =
        "\\[" + PATTERN_METADATAKIND + "\\](" + PATTERN_METADATAVALUE + ")(:(" + PATTERN_METADATAVALUE + "))*";

    private final String metadataKind;
    private final List<String> metadataValueLevels;

    public MetadataPath(String metadataKind, String... metadataValueLevels) {

        assertMetadataKindFormat(metadataKind);
        assertMetadataValueLevelsFormat(metadataValueLevels);

        this.metadataKind = metadataKind.toLowerCase();
        this.metadataValueLevels = new LinkedList<String>();
        for (String metadataValueLevel : metadataValueLevels) {
            this.metadataValueLevels.add(metadataValueLevel.toLowerCase());
        }

    }

    public String getMetadataKind() {
        return metadataKind;
    }

    public void addLevel(String metadataValueLevel) {
        assertMetadataValueFormat(metadataValueLevel);
        metadataValueLevels.add(metadataValueLevel.toLowerCase());
    }

    public List<String> getValueLevels() {
        return Collections.unmodifiableList(metadataValueLevels);
    }

    private void assertMetadataKindFormat(String metadataKindToCheck) {
        if (metadataKindToCheck == null || !metadataKindToCheck.matches(PATTERN_METADATAKIND)) {
            throw new IllegalMetadataFormatException(metadataKindToCheck, PATTERN_METADATAKIND);
        }
    }

    private void assertMetadataValueLevelsFormat(String[] metadataValueLevelsToCheck) {
        for (String metadataValueLevel : metadataValueLevelsToCheck) {
            assertMetadataValueFormat(metadataValueLevel);
        }
    }

    private void assertMetadataValueFormat(String metadataValueLevel) {
        if (metadataValueLevel == null || !metadataValueLevel.matches(PATTERN_METADATAVALUE)) {
            throw new IllegalMetadataFormatException(metadataValueLevel, PATTERN_METADATAVALUE);
        }
    }

    public String toPathString() {
        String metadataPath = MessageFormat.format("[{0}]{1}", metadataKind, StringUtils.join(metadataValueLevels, ':'));
        if (!metadataPath.matches(PATTERN_METADATAPATH)) {
            throw new IllegalMetadataFormatException(metadataPath, PATTERN_METADATAPATH);
        }
        return metadataPath;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((metadataKind == null) ? 0 : metadataKind.hashCode());
        result = prime * result + ((metadataValueLevels == null) ? 0 : metadataValueLevels.hashCode());
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
        MetadataPath other = ( MetadataPath ) obj;
        if (metadataKind == null) {
            if (other.metadataKind != null) {
                return false;
            }
        } else if (!metadataKind.equals(other.metadataKind)) {
            return false;
        }
        if (metadataValueLevels == null) {
            if (other.metadataValueLevels != null) {
                return false;
            }
        } else if (!metadataValueLevels.equals(other.metadataValueLevels)) {
            return false;
        }
        return true;
    }

    // CLASS UTILS

    public static MetadataPath from(MetadataValue metadataValue) {
        String metadataKind = metadataValue.getMetadataKind().getName();
        String[] metadataValueLevels = StringUtils.split(metadataValue.getValue(), ':');
        return new MetadataPath(metadataKind, metadataValueLevels);
    }

    public static String[] toPathStrings(MetadataPath[] metadataPathBuilders) {
        String[] metadataPaths = new String[metadataPathBuilders.length];
        for (int i = 0; i < metadataPathBuilders.length; i++) {
            metadataPaths[i] = metadataPathBuilders[i].toPathString();
        }
        return metadataPaths;
    }

}
