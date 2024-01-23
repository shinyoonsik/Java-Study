package org.example.proxy;

public class Printer {
    private String tag;

    public Printer(String tag) {
        this.tag = tag;
    }

    public String print(String message) {
        System.out.println("프린트 시작: " + System.currentTimeMillis());
        String printedMessage = "<" + tag + ">" + message + "</" + tag + ">";
        System.out.println("프린트 종료: " + System.currentTimeMillis());
        return printedMessage;
    }
}
