package org.example.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.properties.PropertyReader;
import org.example.entities.Client;
import org.example.entities.Project;
import org.example.entities.ProjectWorker;
import org.example.entities.Worker;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabasePopulateService {
    private static final String INSERT_WORKER = "INSERT INTO worker (name, birthday, level, salary) VALUES(?, ?, ?, ?)";
    private static final String INSERT_CLIENT = "INSERT INTO client (name) VALUES(?)";
    private static final String INSERT_PROJECT = "INSERT INTO project (name, client_id, start_date, finish_date) VALUES(?, ?, ?, ?)";
    private static final String INSERT_PROJECT_WORKER = "INSERT INTO project_worker (project_id, worker_id) VALUES(?, ?)";

    public void populateDb(Database database) {
        try {
            String populateDbFileName = PropertyReader.getInstance().getPopulateDbFilePath();
            String sql = String.join(
                    "\n",
                    Files.readAllLines(Paths.get(populateDbFileName))
            );
            database.executeUpdate(sql);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Worker> getWorkers() {
        try {
            String dataWorkerFileName = PropertyReader.getInstance().getDataWorkerFilePath();
            String dataWorker = String.join(
                    "\n",
                    Files.readAllLines(Paths.get(dataWorkerFileName))
            );
            Gson gson = new Gson();
            Type workerListType = new TypeToken<ArrayList<Worker>>() {
            }.getType();
            return gson.fromJson(dataWorker, workerListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Client> getClients() {
        try {
            String dataClientFileName = PropertyReader.getInstance().getDataClientFilePath();
            String dataClient = String.join(
                    "\n",
                    Files.readAllLines(Paths.get(dataClientFileName))
            );
            Gson gson = new Gson();
            Type clientListType = new TypeToken<ArrayList<Client>>() {
            }.getType();
            return gson.fromJson(dataClient, clientListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Project> getProjects() {
        try {
            String dataProjectFileName = PropertyReader.getInstance().getDataProjectFilePath();
            String dataProject = String.join(
                    "\n",
                    Files.readAllLines(Paths.get(dataProjectFileName))
            );
            Gson gson = new Gson();
            Type projectListType = new TypeToken<ArrayList<Project>>() {
            }.getType();
            return gson.fromJson(dataProject, projectListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<ProjectWorker> getProjectsWorkers() {
        try {
            String dataProjectWorkerFileName = PropertyReader.getInstance().getDataProjectWorkerFilePath();
            String dataProjectWorker = String.join(
                    "\n",
                    Files.readAllLines(Paths.get(dataProjectWorkerFileName))
            );
            Gson gson = new Gson();
            Type projectWorkerListType = new TypeToken<ArrayList<ProjectWorker>>() {
            }.getType();
            return gson.fromJson(dataProjectWorker, projectWorkerListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void populateWorker(Database database) {
        try {
            ArrayList<Worker> workers = getWorkers();
            if (workers != null) {
                Connection connection = database.getConnection();
                PreparedStatement insertWorkerStatement = connection.prepareStatement(INSERT_WORKER);
                for (Worker worker : workers) {
                    insertWorkerStatement.setString(1, worker.getName());
                    insertWorkerStatement.setDate(2, Date.valueOf(worker.getBirthday()));
                    insertWorkerStatement.setString(3, worker.getLevel());
                    insertWorkerStatement.setInt(4, worker.getSalary());
                    insertWorkerStatement.addBatch();
                }
                insertWorkerStatement.executeBatch();
            }
        } catch (SQLException e) {
            System.out.println("Can`t create statement. Reason: " + e.getMessage());
        }
    }

    public void populateClient(Database database) {
        try {
            ArrayList<Client> clients = getClients();
            if (clients != null) {
                Connection connection = database.getConnection();
                PreparedStatement insertClientStatement = connection.prepareStatement(INSERT_CLIENT);
                for (Client client : clients) {
                    insertClientStatement.setString(1, client.getName());
                    insertClientStatement.addBatch();
                }
                insertClientStatement.executeBatch();
            }
        } catch (SQLException e) {
            System.out.println("Can`t create statement. Reason: " + e.getMessage());
        }
    }

    public void populateProject(Database database) {
        try {
            ArrayList<Project> projects = getProjects();
            if (projects != null) {
                Connection connection = database.getConnection();
                PreparedStatement insertProjectStatement = connection.prepareStatement(INSERT_PROJECT);
                for (Project project : projects) {
                    insertProjectStatement.setString(1, project.getName());
                    insertProjectStatement.setInt(2, project.getClient_id());
                    insertProjectStatement.setDate(3, Date.valueOf(project.getStart_date()));
                    insertProjectStatement.setDate(4, Date.valueOf(project.getFinish_date()));
                    insertProjectStatement.addBatch();
                }
                insertProjectStatement.executeBatch();
            }
        } catch (SQLException e) {
            System.out.println("Can`t create statement. Reason: " + e.getMessage());
        }
    }

    public void populateProjectWorker(Database database) {
        try {
            ArrayList<ProjectWorker> projectsWorkers = getProjectsWorkers();
            if (projectsWorkers != null) {
                Connection connection = database.getConnection();
                PreparedStatement insertProjectWorkerStatement = connection.prepareStatement(INSERT_PROJECT_WORKER);
                for (ProjectWorker projectWorker : projectsWorkers) {
                    insertProjectWorkerStatement.setInt(1, projectWorker.getProject_id());
                    insertProjectWorkerStatement.setInt(2, projectWorker.getWorker_id());
                    insertProjectWorkerStatement.addBatch();
                }
                insertProjectWorkerStatement.executeBatch();
            }
        } catch (SQLException e) {
            System.out.println("Can`t create statement. Reason: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Database database = Database.getInstance();

        DatabasePopulateService dps = new DatabasePopulateService();
        //simple statement
        //dps.populateDb(database);

        //prepared statement
        dps.populateWorker(database);
        dps.populateClient(database);
        dps.populateProject(database);
        dps.populateProjectWorker(database);

        database.closeConnection();
    }
}
