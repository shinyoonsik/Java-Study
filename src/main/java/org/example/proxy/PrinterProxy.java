package org.example.proxy;

import java.util.List;

public class PrinterProxy implements Printable {
    private String tag;
    private List<Aspect> aspects;

    public PrinterProxy(String tag, Aspect... aspects) {
        this.tag = tag;
        this.aspects = List.of(aspects);
    }

    @Override
    public String print(String message) {
        // 선행 AOP
        aspects.stream().forEach(Aspect::before);

        Printer printer = new Printer(tag);
        String printedMessage = printer.print(message);

        // 후행 AOP
        for (int i = aspects.size() - 1; i >= 0; i--) {
            aspects.get(i).after();
        }

        return printedMessage;
    }
}
