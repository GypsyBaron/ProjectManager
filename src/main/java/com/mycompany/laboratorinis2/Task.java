package main.java.com.mycompany.laboratorinis2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Task implements Serializable {

    private int id;
    private static int idCounter = 1;
    private String title;
    private Date createdOn, completedOn;
    private User createdBy, completedBy;
    private boolean isFinished = false;
    private ArrayList<Task> subTasks = new ArrayList();
    private Project project;

    public Task(String title, Project project, User createdBy) {
        this.id = idCounter;
        idCounter++;
        this.title = title;
        this.createdOn = new Date();
        this.createdBy = createdBy;
        this.project = project;
    }

    public ArrayList<Task> getSubTasks() {
        if (subTasks != null) {
            return subTasks;
        } else {
            return new ArrayList();
        }
    }

    public void setSubTasks(ArrayList<Task> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public String toString() {
        if (this.isFinished) {
            return this.title + " - Completed";
        }
        return this.title + " - Not completed";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(Date completedOn) {
        this.completedOn = completedOn;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(User completedBy) {
        this.completedBy = completedBy;
    }

    public boolean isIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    
    public void completeTask(User user) {
        this.isFinished = true;
        this.completedBy = user;
        this.completedOn = new Date();
    }

    public void addSubtask(String title, User creator) {
        Task newSubTask = new Task(title, this.project, creator);

        subTasks.add(newSubTask);
    }

    public void completeSubTask(User userOnline, int id) {
        Task subtask = subTasks.get(id - 1);
        subtask.isFinished = true;
        subtask.completedBy = userOnline;
        subtask.completedOn = new Date();
    }
}
