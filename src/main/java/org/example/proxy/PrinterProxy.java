package org.example.proxy;

public class PrinterProxy implements Printable {
    private String tag;

    public PrinterProxy(String tag) {
        this.tag = tag;
    }

    @Override
    public String print(String message) {
        Printer printer = new Printer(tag);
        System.out.println("프린트 시작: " + System.currentTimeMillis());
        String printedMessage = printer.print(message);
        System.out.println("프린트 종료: " + System.currentTimeMillis());
        return printedMessage;
    }
}
