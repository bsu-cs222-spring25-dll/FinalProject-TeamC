package edu.bsu.cs;
import java.io.FileNotFoundException;

public class ConsoleMain {
    public static void main(String[] args) throws FileNotFoundException {
        ConsoleMenu menu = new ConsoleMenu();
        menu.runMenu();
    }
}
