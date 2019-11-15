package main.java.com.mycompany.laboratorinis2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class UI {

    static Scanner keyboard = new Scanner(System.in);
    static Rule rule = new Rule();
    static Data data = new Data();
    static IO io = new IO();

    public static void main(String args[]) {

        AppManager todo = new AppManager();
        todo = data.loadData(todo);

        while (true) {
            printStartCommands();
            String input = io.getInput(keyboard);
            chooseStartCommand(input, todo);
        }
    }

    private static void printStartCommands() {
        io.printMessage("Choose a command:\n"
                + "\t Login - 1\n"
                + "\t Register as person - 2\n"
                + "\t Register as company - 3\n"
                + "\t Exit - exit, 0\n");
    }

    private static void chooseStartCommand(String input, AppManager todo) {
        switch (input) {
            case "1":
                if (login(todo)) {
                    printMeniu(todo);
                }
                break;
            case "2":
                addPerson(todo);
                break;
            case "3":
                addCompany(todo);
                break;
            case "0":
            case "exit":
                data.saveData(todo);
                System.exit(0);
            default:
                io.printMessage("Command doesn't exists");
                break;
        }
    }

    private static void printMeniu(AppManager todo) {
        boolean replay = true;

        while (replay) {
            printMeniuCommands(todo);
            String input = io.getInput(keyboard);
            replay = chooseMeniuCommand(input, todo);
        }
    }

    private static void printMeniuCommands(AppManager todo) {
        io.printMessage("Choose meniu command: \n"
                + "\tUser commands - 1\n"
                + "\tProject commands - 2");
        if (todo.getUserOnline().isAdmin) {
            io.printMessage("\tAdmin commands - 3");
        }
        io.printMessage("\tExit - exit, 0\n");
    }

    private static boolean chooseMeniuCommand(String input, AppManager todo) {
        switch (input) {
            case "1":
                printUserSubMeniu(todo);
                break;
            case "2":
                printProjectSubMeniu(todo);
                break;
            case "3":
                if (!todo.getUserOnline().isAdmin) {
                    io.printMessage("Command doesn't exists");
                } else {
                    printAdminSubMeniu(todo);
                }
                break;
            case "0":
                todo.logout();
                return false;
            default:
                io.printMessage("Command doesn't exists");
                break;
        }
        return true;
    }

    private static void printUserSubMeniu(AppManager todo) {
        boolean replay = true;

        while (replay) {
            printUserCommands();
            String input = io.getInput(keyboard);
            replay = chooseUserCommand(input, todo);
        }
    }

    private static void printUserCommands() {
        io.printMessage("Choose user commad:\n"
                + "\tChange login - 1\n"
                + "\tChange password - 2\n"
                + "\tAdd/Change email - 3\n"
                + "\tExit submeniu - exit, 0\n");
    }

    private static boolean chooseUserCommand(String input, AppManager todo) {
        switch (input) {
            case "1":
                todo.changeUserLogin(rule, io.getInputWithMessage(keyboard, "Enter new login: "));
                break;
            case "2":
                todo.changeUserPassword(rule, io.getInputWithMessage(keyboard, "Enter new password: "));
                break;
            case "3":
                todo.changeUserEmail(rule, io.getInputWithMessage(keyboard, "Enter new email: "));
                break;
            case "0":
                return false;
            default:
                io.printMessage("Command doesn't exists");
                break;
        }
        return true;
    }

    private static void printProjectSubMeniu(AppManager todo) {
        boolean replay = true;
        while (replay) {
            printProjectCommands();
            String input = io.getInput(keyboard);
            replay = chooseProjectCommand(input, todo);
        }
    }

    private static void printProjectCommands() {
        io.printMessage("Choose project command:\n"
                + "\tCreate project - 1\n"
                + "\tGet all my projects - 2\n"
                + "\tAdd member to a project - 3\n"
                + "\tGet all project users - 4\n"
                + "\tDelete project - 5\n"
                + "\tAdd task to project - 6\n"
                + "\tGet all project tasks - 7\n"
                + "\tComplete task - 8\n"
                + "\tAdd subtask - 9\n"
                + "\tComplete subtask - 10\n"
                + "\tGet project structure 11\n"
                + "\tExit - 0, exit\n");
    }

    private static boolean chooseProjectCommand(String input, AppManager todo) {
        switch (input) {
            case "1":
                createProject(todo);
                break;
            case "2":
                getAllUserProjects(todo);
                break;
            case "3":
                addMemberToProject(todo);
                break;
            case "4":
                getAllProjectUsers(todo);
                break;
            case "5":
                deleteProject(todo);
                break;
            case "6":
                addTaskToProject(todo);
                break;
            case "7":
                getAllProjectTasks(todo);
                break;
            case "8":
                completeTask(todo);
                break;
            case "9":
                addSubTask(todo);
                break;
            case "10":
                completeSubTask(todo);
                break;
            case "11":
                getProjectStructure(todo);
                break;
            case "0":
            case "exit":
                return false;
            default:
                io.printMessage("Command doesn't exists");
                break;
        }
        return true;
    }

    private static void printAdminSubMeniu(AppManager todo) {
        boolean replay = true;

        while (replay) {
            printAdminCommands();
            String input = io.getInput(keyboard);
            replay = chooseAdminCommand(input, todo);
        }
    }

    private static void printAdminCommands() {
        io.printMessage("Choose admin command:\n"
                + "\tGet all users - 1\n"
                + "\tImport users - 2\n"
                + "\tDeactivate user - 3\n"
                + "\tExit - 0, exit\n");
    }

    private static boolean chooseAdminCommand(String input, AppManager todo) {
        switch (input) {
            case "1":
                getAllUsers(todo);
                break;
            case "2":
                importUsers(todo);
                break;
            case "3":
                deactivateUser(todo);
                break;
            case "0":
            case "exit":
                return false;
            default:
                io.printMessage("Command doesn't exists");
                break;
        }
        return true;
    }

    /*
    g
    g\e
    \e
    \eg
    g
    ge\
    \
    \g
    e\g
    eg
    g
    e
    \g
    e\g
    eg
    g
    \
    eg
    \g
    g
    g\e
    \e
    esg
    ge\g
    \
    sgeg
    e
    eg
    g
    g[wiwga
     */
    private static boolean login(AppManager todo) {
        String login, password;
        int numberOfAttempst = 3;
        boolean logged = false;

        while (numberOfAttempst > 0) {
            login = io.getInputWithMessage(keyboard, "Enter username: ");
            password = io.getInputWithMessage(keyboard, "Enter password: ");
            try {
                logged = todo.loginToWebsite(login, password);
                if (logged) {
                    io.printMessage("Succesfully logged in.");
                } else {
                    io.printMessage("User is deadtivated. You can't login anymore.");
                }
                break;
            } catch (Exception e) {
                io.printMessage(e.getMessage());
                if (numberOfAttempst == 3) {
                    io.printMessage("You can to to login " + (numberOfAttempst - 1) + " more times");
                } else {
                    io.printMessage("You can try to login " + (numberOfAttempst - 1) + " more time");
                }
                numberOfAttempst--;
            }
        }

        if (!logged) {
            io.printMessage("Failed to login.");
            return false;
        } else {
            return true;
        }
    } 

    private static void addPerson(AppManager todo) {
        String login, password, name, surname;

        login = getInput(keyboard, "Enter new user login: ", "Login is already exist or it's too short.", 5);
        if (rule.checkExit(login)) {
            return;
        } else if (!todo.checkLoginIsUnique(login)) {
            login = getInput(keyboard, "Enter new user login: ", "Login is already exist or it's too short.", 5);
        }

        password = getInput(keyboard, "Enter password: ", "Try to enter longer password.", 5);
        if (rule.checkExit(password)) {
            return;
        }

        name = getInput(keyboard, "Enter name: ", "Name is too short.", 3);
        if (rule.checkExit(name)) {
            return;
        }

        surname = getInput(keyboard, "Enter surname: ", "Surname is too short.", 3);
        if (rule.checkExit(name)) {
            return;
        }

        todo.registerPerson(login, password, name, surname);
    } // Done

    private static void importUsers(AppManager todo) {
        Scanner file = null;
        String fileName;
        try {

            fileName = io.getInputWithMessage(keyboard, "Enter file name (without extension txt): ");
            if (rule.checkExit(fileName)) {
                return;
            }
            fileName += ".txt";
            file = new Scanner(new File(fileName));

            while (file.hasNextLine()) {
                String line = file.nextLine();
                String[] data = line.split(";");
                if (data.length == 4) {
                    todo.registerPerson(data[0], data[1], data[2], data[3]);
                } else {
                    todo.registerCompany(data[0], data[1], data[2]);
                }
            }

        } catch (Exception e) {
            io.printMessage("Error reading data.");
        } finally {
            if (file != null) {
                file.close();
            }
        }

    }

    private static void addCompany(AppManager todo) {
        String login, password, title;

        login = getInput(keyboard, "Enter new company login: ", "Login is already exist or it's too short.", 5);
        if (rule.checkExit(login)) {
            return;
        } else if (!todo.checkLoginIsUnique(login)) {
            login = getInput(keyboard, "Enter new company login: ", "Login is already exist or it's too short.", 5);
        }

        password = getInput(keyboard, "Enter password: ", "Try to enter longer password.", 5);
        if (rule.checkExit(password)) {
            return;
        }

        title = getInput(keyboard, "Enter company title: ", "Title is too short.", 3);
        if (rule.checkExit(title)) {
            return;
        }

        todo.registerCompany(login, password, title);
    } // Done

    private static void deactivateUser(AppManager todo) {
        String userLogin = io.getInputWithMessage(keyboard, "Enter user login: ");
        if (rule.checkExit(userLogin)) {
            return;
        }
        try {
            if (todo.makeInactive(userLogin, rule, io, keyboard)) {
                io.printMessage("User was deactivated.");
            } else {
                io.printMessage("User was not deactivated.");
            }
        } catch (Exception e) {
            io.printMessage(e.getMessage());
        }
    }

    private static void createProject(AppManager todo) {
        String projectName;

        projectName = getInput(keyboard, "Enter project title: ", "Project title should be unique and atleast 6 characters length.", 3);
        if (rule.checkExit(projectName)) {
            return;
        } else if (!todo.checkProjectUnique(projectName)) {
            projectName = getInput(keyboard, "Enter project title: ", "Project title should be unique and atleast 6 characters length.", 3);
        }

        todo.createProject(projectName);
        io.printMessage("Project was created.");
    }

    private static void getAllUserProjects(AppManager todo) {
        todo.getAllUserProjects();
    }

    private static void addMemberToProject(AppManager todo) {
        if (todo.userHasProject()) {
            todo.getAllUserProjects();
            int id = getId(keyboard, "Select project id:");
            try {
                Project project = todo.getProjectById(id);
                String input = io.getInputWithMessage(keyboard, "Enter user login: ");
                User user = todo.getUserByLogin(input);
                todo.addMemberToProject(project, user);
                io.printMessage("User was added to project.");
            } catch (Exception e) {
                io.printMessage(e.getMessage());
            }
        }
    }

    private static void getAllProjectUsers(AppManager todo) {
        if (todo.userHasProject()) {
            todo.getAllUserProjects();
            int id = getId(keyboard, "Select project id:");
            try {
                Project project = todo.getProjectById(id);
                todo.getAllProjectUsers(project);
            } catch (Exception e) {
                io.printMessage(e.getMessage());
            }
        }
    }

    private static void deleteProject(AppManager todo) {
        if (todo.userHasProject()) {
            todo.getAllUserProjects();
            int id = getId(keyboard, "Select project id:");
            try {
                Project project = todo.getProjectById(id);
                if (todo.deleteProject(rule, project, keyboard, io)) {
                    io.printMessage("Project succesfully deleted.");
                } else {
                    io.printMessage("Project was NOT delted.");
                }
            } catch (Exception e) {
                io.printMessage(e.getMessage());
            }
        }
    }

    private static String getInput(Scanner keyboard, String startMessage, String badMessage, int length) {
        String result;
        while (true) {
            result = io.getInputWithMessage(keyboard, startMessage);
            if (rule.checkExit(result) || rule.checkLength(result, length)) {
                break;
            }
            io.printMessage(badMessage);
        }
        return result;
    }

    private static void addTaskToProject(AppManager todo) {
        if (todo.userHasProject()) {
            todo.getAllUserProjects();
            int id = getId(keyboard, "Select project id:");
            try {
                Project project = todo.getProjectById(id);
                String newTask = getInput(keyboard, "Enter task title: ", "Task should be atleast 6 characters length.", 5);
                if (rule.checkExit(newTask)) {
                    return;
                }
                todo.addTaskToProject(project, newTask);
                io.printMessage("Task was added succesfully.");
            } catch (Exception e) {
                io.printMessage(e.getMessage());
            }
        }
    }

    private static void getAllProjectTasks(AppManager todo) {
        if (todo.userHasProject()) {
            todo.getAllUserProjects();
            int id = getId(keyboard, "Select project id:");
            try {
                Project project = todo.getProjectById(id);
                if (todo.userProjectHasTasks(project)) {
                    todo.getAllProjectTasks(project);
                }
            } catch (Exception e) {
                io.printMessage(e.getMessage());
            }
        }
    }

    private static void completeTask(AppManager todo) {
        if (todo.userHasProject()) {
            todo.getAllUserProjects();
            int id = getId(keyboard, "Select project id:");
            try {
                Project project = todo.getProjectById(id);
                if (todo.userProjectHasTasks(project)) {
                    todo.getAllProjectTasks(project);
                    int taskId = getId(keyboard, "Select task id:");
                    Task task = todo.getTaskById(project, taskId);
                    if (todo.isAllSubTaskCompleted(task)) {
                        todo.completeTask(project, task);
                        io.printMessage("Task succesfully completed.");
                    } else {
                        io.printMessage("Task cann'ot be completed, subtasks should be completed first.");
                    }
                }
            } catch (Exception e) {
                io.printMessage(e.getMessage());
            }
        }
    }

    private static void addSubTask(AppManager todo) {
        if (todo.userHasProject()) {
            todo.getAllUserProjects();
            int id = getId(keyboard, "Select project id:");
            try {
                Project project = todo.getProjectById(id);
                if (todo.userProjectHasTasks(project)) {
                    todo.getAllProjectTasks(project);
                    int taskId = getId(keyboard, "Select task id:");
                    Task task = todo.getTaskById(project, taskId);

                    String newSubTask = getInput(keyboard, "Enter subTask title: ", "Subtask should be atleast 6 characters length.", 5);
                    if (rule.checkExit(newSubTask)) {
                        return;
                    }
                    todo.addTaskToTask(task, newSubTask);
                    io.printMessage("Subtask was added succesfully.");
                }
            } catch (Exception e) {
                io.printMessage(e.getMessage());
            }
        }
    }

    private static int getId(Scanner keyboard, String message) {
        String result;
        int id;
        while (true) {
            try {
                io.printMessage(message);
                result = keyboard.nextLine().trim();
                if (rule.checkExit(result)) {
                    return 0;
                }
                id = Integer.parseInt(result);

                return id;
            } catch (Exception e) {
                io.printMessage("Invalid id.");
            }
        }
    }

    private static void completeSubTask(AppManager todo) {
        if (todo.userHasProject()) {
            todo.getAllUserProjects();
            int id = getId(keyboard, "Select project id:");
            try {
                Project project = todo.getProjectById(id);
                if (todo.userProjectHasTasks(project)) {
                    todo.getAllProjectTasks(project);
                    int taskId = getId(keyboard, "Select task id:");
                    Task task = todo.getTaskById(project, taskId);
                    if (todo.taskHasSubTasks(task)) {
                        todo.getAllProjectSubTasks(task);
                        int subtaskId = getId(keyboard, "Select subtask id:");
                        todo.completeSubtask(task, subtaskId);
                        io.printMessage("Subtask completed succesfully.");
                    }
                }
            } catch (Exception e) {
                io.printMessage(e.getMessage());
            }
        }
    }

    private static void getProjectStructure(AppManager todo) {
        if (todo.userHasProject()) {
            todo.getAllUserProjects();
            int id = getId(keyboard, "Select project id:");
            try {
                Project project = todo.getProjectById(id);
                todo.printProjectTree(project);
            } catch (Exception e) {
                io.printMessage(e.getMessage());
            }
        }
    }

    private static void getAllUsers(AppManager todo) {
        todo.getAllUsers();
    }
}
