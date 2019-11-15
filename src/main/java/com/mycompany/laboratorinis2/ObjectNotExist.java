package main.java.com.mycompany.laboratorinis2;

import java.io.Serializable;

public class ObjectNotExist extends Exception implements Serializable {

    public ObjectNotExist(String s) {
        super(s);
    }
}
