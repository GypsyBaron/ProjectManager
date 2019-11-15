package main.java.com.mycompany.laboratorinis2;

import java.io.*;

public class Data {

    IO io = new IO();

    public void saveData(AppManager todo) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("data.tdl")));
            out.writeObject(todo);
            out.close();
        } catch (Exception e) {
            io.printMessage("Data was not saved.");
        }
    }

    public AppManager loadData(AppManager todo) {
        try {
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("data.tdl")));
            todo = (AppManager) in.readObject();
            in.close();
        } catch (Exception e) {
            io.printMessage("Error in data reading");
            todo.registerPerson("admin", "admin", "admin", "admin", 1);
        }

        return todo;
    }
}
