package edu.bsu.cs.view;
import edu.bsu.cs.view.ConsoleMenu;

import java.io.FileNotFoundException;

public class ConsoleMain {
    public static void main(String[] args) throws FileNotFoundException {
        ConsoleMenu menu = new ConsoleMenu();
        menu.runMenu();
    }
}
