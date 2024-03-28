package org.example.ch6;

/**
 * Data Race발생 코드
 *
 * 상황 설명
 * x++; y++;이 독립적인 연산이므로 CPU의 비순차적 명령어 처리기능을 통해, increment()의
 * 연산이 순차적으로 발생하지 않는다! 또한 volatile이 없다면(적절한 동기화 메커니즘이 없다면)
 * y > x 같은 상황을 다른 스레드에서 관측할 수 있다. 이는 결과적으로 예측 불가능한 상황이 초래
 * 할 수 있어 Data Race의 예제인 것이다
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
        private volatile int x = 0;
        private volatile int y = 0;

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
