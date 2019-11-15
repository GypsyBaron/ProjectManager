package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.com.mycompany.laboratorinis2.AppManager;
import main.java.com.mycompany.laboratorinis2.IO;

import java.io.Serializable;

public class Start extends Application implements Serializable {
    AppManager todo = new AppManager();
    IO io = new IO();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent root = loader.load();

        DashboardController col = loader.getController();
        col.setAppManager(todo);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Admin Interface");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
}
