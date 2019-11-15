package main.java.com.mycompany.laboratorinis2;

import java.util.Scanner;

public class Rule {

    public boolean checkExit(String input) {
        if (input.toLowerCase().equals("exit") || (input.equals("0"))) {
            return true;
        }

        return false;
    }

    public boolean checkLength(String input, int length) {
        if (input.length() >= length) {
            return true;
        }

        return false;
    }

    public boolean checkIdExit(int id) {
        if (id == 0) {
            return true;
        }
        
        return false;
    }

    public boolean delete(Scanner keyboard, IO io) {
        String result = io.getInputWithMessage(keyboard, "If you want to delete project, type - DELETE");
        if (result.toLowerCase().equals("delete")) {
            return true;
        } 
        return false;
    }   
    
    public boolean deactivate(Scanner keyboard, IO io) {
        String result = io.getInputWithMessage(keyboard, "If you want to deactivate user, type - DELETE");
        if (result.toLowerCase().equals("delete")) {
            return true;
        } 
        return false;
    }
}