package info.novatec.testit.resultrepository.persistence.settings;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "resultrepository.persistence")
public class PersistenceSettings {

    private File dataFolder = new File("data");
    private String keepLogicalLogs = "false";

    public void setDataFolder(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public String getKeepLogicalLogs() {
        return keepLogicalLogs;
    }

    public void setKeepLogicalLogs(String keepLogicalLogs) {
        this.keepLogicalLogs = keepLogicalLogs;
    }

}
