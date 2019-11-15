package main.java.com.mycompany.laboratorinis2;

import java.io.Serializable;

public class Person extends User implements Serializable {

    private String name, surname;

    Person(String name, String surname, String login, String password) {
        super(login, password);
        this.name = name;
        this.surname = surname;
    }
    
    Person(String name, String surname, String login, String password, int id) {
        super(login, password, id);
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        String firstName = this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
        String lastName = this.surname.substring(0, 1).toUpperCase() + this.surname.substring(1);
        return getLogin() + " " + firstName + " " + lastName ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
