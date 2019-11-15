package main.java.com.mycompany.laboratorinis2;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private int id;
    private String login, password, email;
    boolean isAdmin = false;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    private static int counter = 1;

    private boolean active = true;
    private ArrayList<Project> userProjects = new ArrayList();

    User(String login, String password) {
        this.login = login;
        this.password = password;
        this.id = counter;
        counter++;
    }
    
    User(String login, String password, int id) {
        this.login = login;
        this.password = password;
        this.id = counter;
        this.isAdmin = true;
        counter++;
    }

    public static int getCounter() {
        return counter;
    }

    public void addProject(Project project) {
        userProjects.add(project);
    }

    public ArrayList<Project> getUserProjects() {
        return userProjects;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean isActive) {
        this.active = isActive;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
