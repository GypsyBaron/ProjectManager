package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.java.com.mycompany.laboratorinis2.AppManager;
import main.java.com.mycompany.laboratorinis2.Person;
import main.java.com.mycompany.laboratorinis2.Rule;
import main.java.com.mycompany.laboratorinis2.User;

public class PersonController {
    @FXML
    private TextField userLogin, userPass, userName, userSurname;

    private UsersController col;
    private AppManager todo;
    private Person person;

    Rule rule = new Rule();
    public void setPerson(User user) {
        person = (Person) user;
        userLogin.setText((person.getLogin()));
        userPass.setText((person.getPassword()));
        userName.setText((person.getName()));
        userSurname.setText((person.getSurname()));

    }
    public void changePersonInfo(){
        String login = userLogin.getText();
        String pass = userPass.getText();
        String name = userName.getText();
        String surname = userSurname.getText();

        try {
            todo.changePersonInfo(person.getId(), login, pass, name, surname);
        } catch (Exception e) {
            System.out.println("ERROR");
        }

        col.fillTable();
    }

    public void setController(UsersController usersController) {
        col = usersController;
    }

    public void setManager(AppManager todo) {
        this.todo = todo;
    }
}
