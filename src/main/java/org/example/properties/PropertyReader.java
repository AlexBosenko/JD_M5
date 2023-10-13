package org.example.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static final PropertyReader INSTANCE = new PropertyReader();
    private static Properties properties;

    private PropertyReader() {
        try (InputStream inputStream = PropertyReader.class.getClassLoader().getResourceAsStream("app.properties")) {
            if (inputStream == null) {
                System.out.println("Unable to find app.properties");
                properties = null;
            } else {
                properties = new Properties();
                properties.load(inputStream);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static PropertyReader getInstance() {
        return INSTANCE;
    }

    public String getConnectionUrl() {
        if (properties == null) {
            return null;
        }

        return new StringBuilder("jdbc:postgresql://")
                .append(properties.getProperty("postgres.db.host"))
                .append(":")
                .append(properties.getProperty("postgres.db.port"))
                .append("/")
                .append(properties.getProperty("postgres.db.database"))
                .append("?currentschema=public")
                .toString();
    }

    public String getUser() {
        if (properties == null) {
            return null;
        }

        return properties.getProperty("postgres.db.username");
    }

    public String getPassword() {
        if (properties == null) {
            return null;
        }

        return properties.getProperty("postgres.db.password");
    }

    public String getInitDbFilePath() {
        if (properties == null) {
            return null;
        }

        return properties.getProperty("init_db.sql");
    }

    public String getPopulateDbFilePath() {
        if (properties == null) {
            return null;
        }

        return properties.getProperty("populate_db.sql");
    }

    public String getFindMaxSalaryWorkerQueryFilePath() {
        if (properties == null) {
            return null;
        }

        return properties.getProperty("find_max_salary_worker.sql");
    }

    public String getFindMaxProjectsClientQueryFilePath() {
        if (properties == null) {
            return null;
        }

        return properties.getProperty("find_max_projects_client.sql");
    }

    public String getFindLongestProjectQueryFilePath() {
        if (properties == null) {
            return null;
        }

        return properties.getProperty("find_longest_project.sql");
    }

    public String getFindYoungestEldestWorkersQueryFilePath() {
        if (properties == null) {
            return null;
        }

        return properties.getProperty("find_youngest_eldest_workers.sql");
    }

    public String getPrintProjectPricesQueryFilePath() {
        if (properties == null) {
            return null;
        }

        return properties.getProperty("print_project_prices.sql");
    }

    public String getDataWorkerFilePath() {
        if (properties == null) {
            return null;
        }

        return properties.getProperty("data_worker");
    }

    public String getDataClientFilePath() {
        if (properties == null) {
            return null;
        }

        return properties.getProperty("data_client");
    }

    public String getDataProjectFilePath() {
        if (properties == null) {
            return null;
        }

        return properties.getProperty("data_project");
    }

    public String getDataProjectWorkerFilePath() {
        if (properties == null) {
            return null;
        }

        return properties.getProperty("data_project_worker");
    }
}
