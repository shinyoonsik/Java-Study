package org.example.ch2;

public class Basic {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("we are in thread: [" + Thread.currentThread().getName() + "]");
                System.out.println("current thread priority is " + Thread.currentThread().getPriority());
            }
        });

        thread.setName("New Worker Thread");
        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("we are in thread: [" + Thread.currentThread().getName() + "] before starting a new thread");
        thread.start();
        System.out.println("we are in thread: [" + Thread.currentThread().getName() + "] after starting a new thread");
    }
}
