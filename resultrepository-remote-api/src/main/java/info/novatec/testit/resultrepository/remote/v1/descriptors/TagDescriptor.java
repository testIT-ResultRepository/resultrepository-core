package info.novatec.testit.resultrepository.remote.v1.descriptors;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class TagDescriptor {

    private String pattern;
    private boolean useEquals;

    public TagDescriptor() {
        // for serialization
    }

    public TagDescriptor(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }

    public TagDescriptor setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public boolean isUseEquals() {
        return useEquals;
    }

    public TagDescriptor setUseEquals(boolean useEquals) {
        this.useEquals = useEquals;
        return this;
    }

    public TagDescriptor usingEquality() {
        return setUseEquals(true);
    }

    @Override
    public String toString() {
        return "TagDescriptor [pattern=" + pattern + ", useEquals=" + useEquals + "]";
    }

}
