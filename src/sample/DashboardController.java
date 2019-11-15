package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.com.mycompany.laboratorinis2.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashboardController implements Serializable {
    @FXML
    private PieChart userS;
    @FXML
    private AreaChart projectS;
    AppManager todo = null;
    Data data = new Data();

    public void close(ActionEvent as) {
        data.saveData(todo);
        Platform.exit();
    }

    public void openUserManager(ActionEvent df) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Users.fxml"));
        Parent root = loader.load();

        UsersController col = loader.getController();
        col.setAppManager(todo);

        Scene scene = new Scene(root);
        Stage nauja = new Stage();
        nauja.setTitle("User management");
        nauja.setScene(scene);
        nauja.show();
    }

    public void setAppManager(AppManager t) {
        todo = t;
        todo = data.loadData(todo);
        showUserStat();
        showProjectStart();
    }

    public void showUserStat() {
        if (todo != null){ //
            ArrayList<User> users = todo.getAllActiveUsers();
            int [] count = todo.getUserCount();
            System.out.println(count[0]);
            System.out.println(count[1]);
            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data("Persons", count[0]),
                            new PieChart.Data("Companies" , count[1])
                    );
            userS.setTitle("User statistics");
            userS.setData(pieChartData);
        }
    }

    public void showProjectStart() {
        XYChart.Series seriesMay = new XYChart.Series();
        seriesMay.setName("Tasks per project");
        int[][] m = todo.getProjectNumbers();
        for (int [] row : m) {
            seriesMay.getData().add(new XYChart.Data(row[0], row[1]));
        }
        projectS.getData().addAll(seriesMay);
    }

    //@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
