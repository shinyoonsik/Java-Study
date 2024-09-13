package org.example.ch7;

public class InterruptTest {
    private static final Object lockObject = new Object();
    public static void main(String[] args) throws InterruptedException {
        Thread aThread = new Thread(() -> {
            synchronized (lockObject) {
                try {
                    System.out.println("current Thread = " + Thread.currentThread().getName());
                    System.out.println("Thread is waiting...");
                    lockObject.wait(); // aThread가 lockObject의 모니터락을 해제 -> 대기 상태로 돌아간다
                    System.out.println("Thread resumed.");
                } catch (InterruptedException e) {
                    System.out.println("Thread was interrupted.");
                }
            }
        });

        aThread.start();

        Thread.sleep(3000); // main Thread가 너무 빨리 실행되므로 의도적으로 aThread가 먼저 실행되도록 하기위해 sleep()호출

        synchronized (lockObject){
            System.out.println("current Thread = " + Thread.currentThread().getName());
            System.out.println("Main Thread is notifying!");
            lockObject.notify();
        }
    }
}
