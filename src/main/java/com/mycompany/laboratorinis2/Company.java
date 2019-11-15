package main.java.com.mycompany.laboratorinis2;

import java.io.Serializable;

public class Company extends User implements Serializable {

    private String title;

    Company(String login, String password, String title) {
        super(login, password);
        this.title = title;
    }

    @Override
    public String toString() {
        return getId() + " " + title + " " + getLogin() + " " + getPassword();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
