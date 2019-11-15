package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.java.com.mycompany.laboratorinis2.AppManager;
import main.java.com.mycompany.laboratorinis2.Company;
import main.java.com.mycompany.laboratorinis2.Person;
import main.java.com.mycompany.laboratorinis2.User;

public class CompanyController {
    @FXML
    private TextField companyLogin, companyPass, companyTitle;
    private UsersController col;
    private AppManager todo;
    private Company company;

    public void setCompany(User user) {
        company = (Company) user;
        companyLogin.setText((company.getLogin()));
        companyPass.setText((company.getPassword()));
        companyTitle.setText((company.getTitle()));
    }

    public void changeCompanyInfo(){
        String login = companyLogin.getText();
        String pass = companyPass.getText();
        String title = companyTitle.getText();

        try {
            todo.changeCompanyInfo(company.getId(), login, pass, title);
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
