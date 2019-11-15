package main.java.com.mycompany.laboratorinis2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Project implements Serializable {

    private int id;
    private String title;
    private Date createdOn, finishedOn;
    private User creator;
    private ArrayList<User> projectMembers = new ArrayList();
    private ArrayList<Task> projectTasks = new ArrayList();
    private static int counter = 1;

    Project(String title, User creator) {
        this.id = counter;
        this.title = title;
        this.creator = creator;
        this.createdOn = new Date();
        projectMembers.add(creator);
        counter++;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<User> getProjectMembers() {
        return projectMembers;
    }

    public void setProjectMembers(ArrayList<User> projectMembers) {
        this.projectMembers = projectMembers;
    }

    public User getCreator() {
        return creator;
    }

    public ArrayList<Task> getProjectTasks() {
        return projectTasks;
    }

    public void setProjectTasks(ArrayList<Task> projectTasks) {
        this.projectTasks = projectTasks;
    }

    @Override
    public String toString() {
        return "Project - " + this.title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Project.counter = counter;
    }

    public void addMemberToProject(User user) {
        boolean isMemberAlreadyInProject;

        isMemberAlreadyInProject = projectMembers
                .stream()
                .anyMatch(x -> x.getId() == user.getId());

        if (!isMemberAlreadyInProject) {
            projectMembers.add(user);
        }
    }

    public void addTaskToProject(Task task) {
        projectTasks.add(task);
    }
}
