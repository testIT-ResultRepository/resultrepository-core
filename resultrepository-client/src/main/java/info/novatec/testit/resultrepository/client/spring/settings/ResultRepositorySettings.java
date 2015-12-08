package info.novatec.testit.resultrepository.client.spring.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("resultrepository")
public class ResultRepositorySettings {

    private String url = "http://localhost:8080";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
