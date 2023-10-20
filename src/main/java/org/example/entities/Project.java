package org.example.entities;

public class Project {
    private final String name;
    private final int client_id;
    private final String start_date;
    private final String finish_date;

    public Project(String name, int client_id, String start_date, String finish_date) {
        this.name = name;
        this.client_id = client_id;
        this.start_date = start_date;
        this.finish_date = finish_date;
    }

    public String getName() {
        return name;
    }

    public int getClient_id() {
        return client_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getFinish_date() {
        return finish_date;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", client_id=" + client_id +
                ", start_date='" + start_date + '\'' +
                ", finish_date='" + finish_date + '\'' +
                '}';
    }
}
