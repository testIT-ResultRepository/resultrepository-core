package info.novatec.testit.resultrepository.persistence.services.graph;

import static org.neo4j.graphdb.factory.GraphDatabaseSettings.keep_logical_logs;

import java.io.File;
import java.util.function.Consumer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import info.novatec.testit.resultrepository.persistence.settings.PersistenceSettings;


@Component
public class GraphDatabaseServiceFactoryBean implements FactoryBean<GraphDatabaseService> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphDatabaseServiceFactoryBean.class);
    private static final String DATABASE_NAME = "resultrepository";

    @Autowired
    private PersistenceSettings settings;

    private GraphDatabaseService bean;

    @PostConstruct
    public void init() {

        LOGGER.info("initializing graph database");

        bean = buildAndConfigureDatabaseService(databaseBuilder -> {
            databaseBuilder.setConfig(keep_logical_logs, settings.getKeepLogicalLogs());
        });

        LOGGER.info("graph database initialized");

    }

    private GraphDatabaseService buildAndConfigureDatabaseService(Consumer<GraphDatabaseBuilder> configurator) {
        File databaseFolder = new File(settings.getDataFolder(), DATABASE_NAME);
        GraphDatabaseBuilder databaseBuilder = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(databaseFolder);
        configurator.accept(databaseBuilder);
        return databaseBuilder.newGraphDatabase();
    }

    @Override
    public GraphDatabaseService getObject() {
        return bean;
    }

    @Override
    public Class<GraphDatabaseService> getObjectType() {
        return GraphDatabaseService.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @PreDestroy
    public void shutdown() {
        bean.shutdown();
        LOGGER.info("graph database was shut down");
    }

}
