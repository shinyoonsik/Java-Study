package org.example.ch6;

/**
 * Data Race발생 코드
 */
public class DataRace {
    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.checkForDataRace();
            }
        });

        thread1.setName("thread1");
        thread1.start();

        thread2.setName("thread2");
        thread2.start();
    }

    static class SharedClass{
        private int x = 0;
        private int y = 0;

        public void increment(){
            x++;
            y++;
        }

        public void checkForDataRace(){
            if(y > x){
                System.out.println(Thread.currentThread());
                System.out.println("y > x - Data Race is detected!");
                System.out.println("current X: "+ x +"  " + "current Y: " + y);
            }
        }
    }
}
