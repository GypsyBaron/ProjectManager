package sample;

import com.sun.webkit.graphics.WCPathIterator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.com.mycompany.laboratorinis2.AppManager;
import main.java.com.mycompany.laboratorinis2.Person;
import main.java.com.mycompany.laboratorinis2.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsersController implements Initializable {

    @FXML
    private TableView userTable;
    @FXML
    private VBox userFieldEdit;

    AppManager todo = null;

    public void setAppManager(AppManager t) {
        todo = t;
        fillTable();
    }

    public void updateUser() throws IOException {
        User user = (User)userTable.getSelectionModel().getSelectedItem();
        if (user.getClass().equals(Person.class)) {
            loadPersonInfo(user);
        } else {
            loadCompanyInfo(user);
        }
     }

    private void loadCompanyInfo(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Company.fxml"));
        Node company = loader.load();

        CompanyController col = loader.getController();
        col.setManager(todo);
        col.setController(this);
        col.setCompany(user);

        userFieldEdit.getChildren().clear();
        userFieldEdit.getChildren().add(company);
    }

    public void loadPersonInfo(User user) throws IOException {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("Person.fxml"));
         Node person = loader.load();

         PersonController col = loader.getController();
         col.setManager(todo);
         col.setController(this);
         col.setPerson(user);

         userFieldEdit.getChildren().clear();
         userFieldEdit.getChildren().add(person);
     }

    public void fillTable() {

        if (todo != null) {
            userTable.getItems().clear();
            userTable.getItems().addAll(todo.getAllActiveUsers());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<String, User> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<String, User> column2 = new TableColumn<>("Login name");
        column2.setCellValueFactory(new PropertyValueFactory<>("login"));

//        TableColumn<String, User> column3 = new TableColumn<>("E-mail");
//        column3.setCellValueFactory(new PropertyValueFactory<>("email"));

        userTable.getColumns().clear();
        userTable.getColumns().add(column1);
        userTable.getColumns().add(column2);
        //userTable.getColumns().add(column3);
    }
}
