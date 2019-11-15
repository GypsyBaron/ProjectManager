package main.java.com.mycompany.laboratorinis2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AppManager implements Serializable {

    private ArrayList<User> users = new ArrayList();
    private ArrayList<Project> projects = new ArrayList();
    private User userOnline = null;

    public void registerPerson(String login, String password, String name, String surname, int id) {
        Person newPerson = new Person(name, surname, login, password, id);
        users.add(newPerson);
    }

    public User getUserOnline() {
        return userOnline;
    }

    public void registerPerson(String login, String password, String name, String surname) {
        if (checkLoginIsUnique(login)) {
            Person newPerson = new Person(name, surname, login, password);
            users.add(newPerson);
            if (!login.equals("admin")) {
                System.out.println("Person was registered to the system.");
            }
        } else {
            System.out.println("Person was NOT registered to the system.");
        }
    }

    public void registerCompany(String title, String login, String password) {
        if (checkLoginIsUnique(login)) {
            Company newCompany = new Company(title, login, password);
            users.add(newCompany);
            System.out.println("Company was registered to the system.");
        } else {
            System.out.println("Company was NOT registered to the system.");
        }
    }

    public void getAllUsers() {
        printArrayList(users, "Users:");
    }

    public ArrayList<User> getAllActiveUsers() {
        if (userOnline != null && userOnline.isActive()) {
            System.out.println("Cia klaida buvo");
        } else {
            ArrayList<User> activeUsers = new ArrayList();
            activeUsers = users
                    .stream()
                    .filter(x -> x.isActive())
                    .collect(Collectors.toCollection(ArrayList::new));

            return activeUsers;
        }

        return new ArrayList();
    }

    public User getUserById(int id) throws Exception {
        User user;
        user = users.stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ObjectNotExist("User not found"));

        return user;
    }

    public boolean checkLoginIsUnique(String login) {
        boolean isUnique;

        isUnique = users
                .stream()
                .anyMatch(x -> x.getLogin().equals(login));

        return !isUnique;
    }

    public boolean loginToWebsite(String login, String password) throws Exception {
        User user;
        user = users
                .stream()
                .filter(x -> x.getLogin().equals(login) && x.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new ObjectNotExist("Incorrect login data"));

        if (user.isActive()) {
            userOnline = user;
            return true;
        } else {
            return false;
        }
    }

    public void logout() {
        userOnline = null;
    }

    public void printArrayList(ArrayList<?> arr, String name) {
        System.out.println(name + " list:");
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(("\t" + (i + 1) + " " + arr.get(i)));
        }
    }

    public boolean makeInactive(String userLogin, Rule rule, IO io, Scanner keyboard) throws Exception {
        User user = users
                .stream()
                .filter(x -> x.getLogin().equals(userLogin))
                .findFirst()
                .orElseThrow(() -> new ObjectNotExist("User not found"));

        if (rule.deactivate(keyboard, io)) {
            user.setActive(false);
            return true;
        } else {
            return false;
        }
    }

    public void changeUserLogin(Rule rule, String newLogin) {
        System.out.println("IM HERE");
        if (rule.checkLength(newLogin, 5)) {
            userOnline.setLogin(newLogin);
            System.out.println("Login was changed");
        } else {
            System.out.println("Login wasn't changed.");
        }
    }


    public void changeUserPassword(Rule rule, String newPassword) {
        if (rule.checkLength(newPassword, 5)) {
            userOnline.setLogin(newPassword);
            System.out.println("Password was changed");
        } else {
            System.out.println("Password wasn't changed.");
        }
    }

    public void changeUserEmail(Rule rule, String newEmail) {
        if (rule.checkLength(newEmail, 5)) {
            userOnline.setLogin(newEmail);
            System.out.println("Email was changed");
        } else {
            System.out.println("Email wasn't changed.");
        }
    }

    public void createProject(String title) {
        Project project = new Project(title, userOnline);
        projects.add(project);
        userOnline.addProject(project);
    }

    public void getAllUserProjects() {
        ArrayList<Project> projects = new ArrayList();
        projects = userOnline.getUserProjects();
        if (userHasProject()) {
            printArrayList(projects, "Project");
        }
    }

    public boolean userHasProject() {
        if (!userOnline.getUserProjects().isEmpty()) {
            return true;
        }
        System.out.println("You don't have any projects.");
        return false;
    }

    public boolean userProjectHasTasks(Project project) {
        if (!project.getProjectTasks().isEmpty()) {
            return true;
        }
        System.out.println("You don't have any tasks.");
        return false;
    }

    public boolean taskHasSubTasks(Task task) {
        if (!task.getSubTasks().isEmpty()) {
            return true;
        }
        System.out.println("You don't have any subtasks.");
        return false;
    }

    public Project getUserProjectById(int id) {
        if (userOnline != null && userOnline.isActive()) {
            ArrayList<Project> userProjects;
            Project singleProject;
            userProjects = userOnline.getUserProjects();

            singleProject = userProjects
                    .stream()
                    .filter(x -> x.getId() == id)
                    .findFirst()
                    .orElse(null);

            return singleProject;
        }
        return null;
    }

    public void getAllProjectUsers(Project project) {
        printArrayList(project.getProjectMembers(), "User");
    }

    public Project getProjectById(int id) throws Exception {
        Project project;
        try {
            project = userOnline.getUserProjects().get(id - 1);
            return project;
        } catch (Exception e) {
            throw new ObjectNotExist("Project not found.");
        }
    }

    public void addMemberToProject(Project project, User user) {
        project.addMemberToProject(user);
        user.addProject(project);
    }

    public boolean deleteProject(Rule rule, Project project, Scanner keyboard, IO io) {
        if (userOnline.getId() == project.getCreator().getId()) {
            if (rule.delete(keyboard, io)) {
                projects.remove(project);
                for (User user : project.getProjectMembers()) {
                    user.getUserProjects().remove(project);
                }
                return true;
            }
        }
        return false;
    }

    public ArrayList<Project> getAllProjects() throws Exception {
        if (userOnline != null && userOnline.isActive() && projects.size() != 0) {
            return projects;
        }

        throw new ObjectNotExist("No projects found");
    }

    public void addTaskToProject(Project project, String title) {
        Task newTask = new Task(title, project, userOnline);
        project.addTaskToProject(newTask);
    }

    public void getAllProjectTasks(Project project) {
//        if (userOnline != null && userOnline.isActive()) {
//            Project myProject;
//
//            try {
//                myProject = getProjectById(projectId);
//                ArrayList<Task> projectTasks = myProject.getProjectTasks();
//                if (projectTasks.size() == 0) {
//                    System.out.println("Project doesn't have any tasks");
//                }
//                return projectTasks;
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        return new ArrayList();
        ArrayList<Task> projectTasks = project.getProjectTasks();
        printArrayList(projectTasks, "Task");
    }

    public Task getTaskById(Project project, int taskId) throws Exception {
//        if (userOnline != null && userOnline.isActive()) {
//            Project myProject;
//
//            try {
//                myProject = getProjectById(projectId);
//                if (myProject != null) {
//                    ArrayList<Task> projectTasks = myProject.getProjectTasks();
//
//                    Task myTask = projectTasks
//                            .stream()
//                            .filter(x -> x.getId() == taskId)
//                            .findFirst()
//                            .orElseThrow(() -> new ObjectNotExist("Task not found."));
//
//                    return myTask;
//                }
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//        return null;
        try {
            Task task;
            task = project.getProjectTasks().get(taskId - 1);
            return task;
        } catch (Exception e) {
            throw new ObjectNotExist("Task not found.");
        }
    }

    public void completeTask(Project project, Task task) {
        task.completeTask(userOnline);
    }

    public ArrayList<Task> getAllProjectCompletedTasks(int projectId) {
        if (userOnline != null && userOnline.isActive()) {
            ArrayList<Task> projectTasks, filteredTasks;

            //projectTasks = getAllProjectTasks(projectId);
//            filteredTasks = projectTasks
//                    .stream()
//                    .filter(x -> x.isIsFinished())
//                    .collect(Collectors.toCollection(ArrayList::new));
            //return filteredTasks;
        }

        return new ArrayList();
    }

    public ArrayList<Task> getAllProjectIncompletedTasks(int projectId) {
        if (userOnline != null && userOnline.isActive()) {
            ArrayList<Task> projectTasks, filteredTasks;

            //projectTasks = getAllProjectTasks(projectId);
//
//            filteredTasks = projectTasks
//                    .stream()
//                    .filter(x -> x.isIsFinished() == false)
//                    .collect(Collectors.toCollection(ArrayList::new));
//
//            return filteredTasks;
        }

        return new ArrayList();
    }

    public void addTaskToTask(Task task, String subTaskTitle) {
//        if (userOnline != null && userOnline.isActive()) {
//
//            try {
//                Task task = getTaskById(projectId, taskId);
//
//                if (task != null) {
//                    task.addSubtask(title, userOnline);
//                }
//            } catch (Exception e) {
//                System.out.println("Task was not added");
//            }
//        }
        task.addSubtask(subTaskTitle, userOnline);
    }

    public void getAllProjectSubTasks(Task task) {
        printArrayList(task.getSubTasks(), "Subtask");
//        Project project;
//        ArrayList<Task> allProjectTasks = new ArrayList();
//        ArrayList<Task> allProjectSubTasks = new ArrayList();
//        ArrayList<Task> allSubTasks = new ArrayList();
//
//        try {
//            project = getProjectById(id);
//
//            allProjectTasks = project.getProjectTasks();
//
//            for (Task task : allProjectTasks) {
//                allProjectSubTasks = task.getSubTasks();
//                for (Task subTask : allProjectSubTasks) {
//                    allSubTasks.add(subTask);
//                }
//            }
//
//            return allSubTasks;
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        return null;
    }

    public boolean checkProjectUnique(String projectName) {
        boolean isUnique = false;

        try {
            ArrayList<Project> allProjects = getAllProjects();
            isUnique = allProjects
                    .stream()
                    .anyMatch(x -> x.getTitle().equals(projectName));

            return !isUnique;
        } catch (Exception e) {
            return true;
        }
    }

    public User getUserByLogin(String input) throws Exception {
        User user;

        user = users
                .stream()
                .filter(x -> x.getLogin().equals(input))
                .findFirst()
                .orElseThrow(() -> new ObjectNotExist("User not found"));

        return user;
    }

    void completeSubtask(Task task, int subtaskId) throws Exception {
        try {
            Task subtask = task.getSubTasks().get(subtaskId - 1);
            task.completeSubTask(userOnline, subtaskId);
        } catch (Exception e) {
            throw new ObjectNotExist("Subtask not found.");
        }
    }

    void printProjectTree(Project project) {
        System.out.println("\nProject - " + project.getName());
        for (Task t : project.getProjectTasks()) {
            System.out.println(" - " + t);
            for (Task s : t.getSubTasks()) {
                System.out.println("\t - " + s);
            }
        }
        System.out.println();
    }

    boolean isAllSubTaskCompleted(Task task) {
        boolean isAllSubtaskCompleted = task.getSubTasks()
                .stream()
                .allMatch(x -> x.isIsFinished());
        //.anyMatch(x -> !x.isIsFinished());

        for (Task t : task.getSubTasks()) {
            if (t.isIsFinished()) {
                System.out.println(t + " is completed");
            } else {
                System.out.println(t + " is NOT completed");
            }
        }
        return isAllSubtaskCompleted;
    }

    public int[] getUserCount() {
        int[] num = new int[2];
        for (User u : users) {
            if (u.getClass().equals(Person.class)) {
                num[0]++;
            } else {
                num[1]++;
            }
        }
        return num;
    }

    public int[][] getProjectNumbers() {
        int[][] mas = new int[projects.size()][2];
        int id = 0;
        for (Project p : projects) {
            mas[id][0] = p.getId();
            mas[id][1] = p.getProjectTasks().size();
            id++;
        }
        return mas;
    }

    public void changePersonInfo(int id, String login, String pass, String name, String surname) throws Exception{
        User user = users.stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ObjectNotExist("User not found"));

        Person person = (Person) user;
        person.setLogin(login);
        person.setPassword(pass);
        person.setName(name);
        person.setSurname(surname);
    }

    public void changeCompanyInfo(int id, String login, String pass, String title) throws Exception{
        User user = users.stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ObjectNotExist("User not found"));

        Company company = (Company) user;
        company.setLogin(login);
        company.setPassword(pass);
        company.setTitle(title);
    }
}
