package org.example.proxy;

import java.util.List;

public class PrinterProxy implements Printable {
    private String tag;
    private List<Aspect> aspects;
    private Printer printer;

    public PrinterProxy(String tag, Aspect... aspects) {
        this.tag = tag;
        this.aspects = List.of(aspects);
    }

    private Printer getPrinter(){ // 객체 생성을 필요한 순간까지 지연시킨다.
        if(printer == null){
            return new Printer(tag);
        }
        return printer;
    }

    @Override
    public String print(String message) {
        // 선행 AOP
        aspects.stream().forEach(Aspect::before);

        Printer printer = getPrinter();
        String printedMessage = printer.print(message);

        // 후행 AOP
        for (int i = aspects.size() - 1; i >= 0; i--) {
            aspects.get(i).after();
        }

        return printedMessage;
    }
}
