package main.java.com.mycompany.laboratorinis2;

import java.util.Scanner;

public class IO {
    
    public String getInputWithMessage(Scanner keyboard, String message) {
        String result;
        
        printMessage(message);
        result = keyboard.nextLine().trim();
        
        return result;
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
    
    public String getInput(Scanner keyboard) {
        String result;
        
        result = keyboard.nextLine().trim();
        
        return result;
    }
}
