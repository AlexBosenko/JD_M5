package org.example.db;

import org.example.properties.PropertyReader;
import org.example.entities.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    public List<MaxSalaryWorker> findMaxSalaryWorker(Database database) {
        List<MaxSalaryWorker> mswList = new ArrayList<>();

        try {
            String findMaxSalaryWorkerFileName = PropertyReader.getInstance().getFindMaxSalaryWorkerQueryFilePath();
            String sql = String.join(
                    "\n",
                    Files.readAllLines(Paths.get(findMaxSalaryWorkerFileName))
            );

            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                mswList.add(new MaxSalaryWorker(resultSet.getString("name"), resultSet.getInt("salary")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.printf("Can`t run query. Reason: %s%n", e.getMessage());
            throw new RuntimeException(e);
        }

        return mswList;
    }

    public List<MaxProjectCountClient> findMaxProjectsClient(Database database) {
        List<MaxProjectCountClient> mpcList = new ArrayList<>();

        try {
            String findMaxProjectsClientFileName = PropertyReader.getInstance().getFindMaxProjectsClientQueryFilePath();
            String sql = String.join(
                    "\n",
                    Files.readAllLines(Paths.get(findMaxProjectsClientFileName))
            );

            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                mpcList.add(new MaxProjectCountClient(resultSet.getString("name"), resultSet.getInt("project_count")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.printf("Can`t run query. Reason: %s%n", e.getMessage());
            throw new RuntimeException(e);
        }

        return mpcList;
    }

    public List<LongestProject> findLongestProject(Database database) {
        List<LongestProject> lpList = new ArrayList<>();

        try {
            String findLongestProjectFileName = PropertyReader.getInstance().getFindLongestProjectQueryFilePath();
            String sql = String.join(
                    "\n",
                    Files.readAllLines(Paths.get(findLongestProjectFileName))
            );

            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                lpList.add(new LongestProject(resultSet.getString("name"), resultSet.getInt("month_count")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.printf("Can`t run query. Reason: %s%n", e.getMessage());
            throw new RuntimeException(e);
        }

        return lpList;
    }

    public List<YoungestEldestWorkers> findYoungestEldestWorkers(Database database) {
        List<YoungestEldestWorkers> yewList = new ArrayList<>();

        try {
            String findYoungestEldestWorkersFileName = PropertyReader.getInstance().getFindYoungestEldestWorkersQueryFilePath();
            String sql = String.join(
                    "\n",
                    Files.readAllLines(Paths.get(findYoungestEldestWorkersFileName))
            );

            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                yewList.add(new YoungestEldestWorkers(
                        resultSet.getString("type"),
                        resultSet.getString("name"),
                        LocalDate.parse(resultSet.getString("birthday"))
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.printf("Can`t run query. Reason: %s%n", e.getMessage());
            throw new RuntimeException(e);
        }

        return yewList;
    }

    public List<ProjectPrices> printProjectPrices(Database database) {
        List<ProjectPrices> ppList = new ArrayList<>();

        try {
            String printProjectPricesFileName = PropertyReader.getInstance().getPrintProjectPricesQueryFilePath();
            String sql = String.join(
                    "\n",
                    Files.readAllLines(Paths.get(printProjectPricesFileName))
            );

            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ppList.add(new ProjectPrices(
                        resultSet.getString("name"),
                        resultSet.getLong("price")
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.printf("Can`t run query. Reason: %s%n", e.getMessage());
            throw new RuntimeException(e);
        }

        return ppList;
    }

    public static void main(String[] args) {
        Database database = Database.getInstance();

        System.out.println("Workers with MAX salary:");
        List<MaxSalaryWorker> maxSalaryWorkers = new DatabaseQueryService().findMaxSalaryWorker(database);
        for (MaxSalaryWorker maxSalaryWorker : maxSalaryWorkers) {
            System.out.println(maxSalaryWorker);
        }

        System.out.println("----------------------");

        System.out.println("Clients project count:");
        List<MaxProjectCountClient> maxProjectCountClient = new DatabaseQueryService().findMaxProjectsClient(database);
        for (MaxProjectCountClient projectCountClient : maxProjectCountClient) {
            System.out.println(projectCountClient);
        }

        System.out.println("----------------------");

        System.out.println("Longest project:");
        List<LongestProject> longestProjects = new DatabaseQueryService().findLongestProject(database);
        for (LongestProject longestProject : longestProjects) {
            System.out.println(longestProject);
        }

        System.out.println("----------------------");

        System.out.println("Youngest eldest workers:");
        List<YoungestEldestWorkers> youngestEldestWorkers = new DatabaseQueryService().findYoungestEldestWorkers(database);
        for (YoungestEldestWorkers youngestEldestWorker : youngestEldestWorkers) {
            System.out.println(youngestEldestWorker);
        }

        System.out.println("----------------------");

        System.out.println("Print project prices:");
        List<ProjectPrices> projectPrices = new DatabaseQueryService().printProjectPrices(database);
        for (ProjectPrices projectPrice : projectPrices) {
            System.out.println(projectPrice);
        }

        database.closeConnection();
    }
}