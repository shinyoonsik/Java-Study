package org.example.proxy;

public class Printer implements Printable{
    private String tag;

    public Printer(String tag) {
        this.tag = tag;
    }

    public String print(String message) {
        String printedMessage = "<" + tag + ">" + message + "</" + tag + ">";
        return printedMessage;
    }
}
