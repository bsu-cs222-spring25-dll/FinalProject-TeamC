package edu.bsu.cs.utility;

public class ErrorPrinter {
    public void printConnectionMessageError(String connectionMessage) {
        if (!connectionMessage.isEmpty()) {
            System.err.println(connectionMessage);
        }


    }

    public void print429Error(String error429Message) {
        if (!error429Message.isEmpty()) {
            System.err.println(error429Message);
        }
    }
}
