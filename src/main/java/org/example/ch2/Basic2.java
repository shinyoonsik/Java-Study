package org.example.ch2;

public class Basic2 {
    public static void main(String[] args) {
        System.out.println("current Thread Working0: " + Thread.currentThread().getName());

        Thread thread = new Thread(() -> {
            throw new RuntimeException("occur critical Exception!");
        });
        thread.setName("Misbehaving thread");

        System.out.println("current Thread Working1: " + Thread.currentThread().getName());

        // unchecked Exception에 대해 예외처리를 하지 못하고 빠져나온 예외들을 잡아주는 handler
        thread.setUncaughtExceptionHandler((Thread t, Throwable e) -> {
            System.out.println("놓친 Exception을 실행시킨 스레드: " + t.getName() + "\nthe error is " + e.getMessage());
        });
        thread.start();

        for(int i=0; i< 100; i++){
            System.out.println("current Thread Working2: " + Thread.currentThread().getName());
        }
    }
}
