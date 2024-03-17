package org.example.ch5;

/**
 * 상황: 서로 다른 스레드에서 각각 point변수의 상태를 10000번 더하고 10000번 뺀 상황이다
 * 결과: 원하는 결과는 0이 나와야 한다
 */
public class DataSharingBtwThreads {
    public static void main(String[] args) throws InterruptedException {

        PointCounter pointCounter = new PointCounter();
        IncrementingThread incrementingThread = new IncrementingThread(pointCounter);
        DecrementingThread decrementingThread = new DecrementingThread(pointCounter);

        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("현재 Point: " + pointCounter.getPoints());
    }

    private static class IncrementingThread extends Thread {
        private PointCounter pointCounter;

        public IncrementingThread(PointCounter pointCounter) {
            this.pointCounter = pointCounter;
        }

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < 10000; i++) {
                pointCounter.increment();
            }
            System.out.println("IncrementingThread:" + this.pointCounter.getPoints());
        }
    }

    private static class DecrementingThread extends Thread {
        private PointCounter pointCounter;

        public DecrementingThread(PointCounter pointCounter) {
            this.pointCounter = pointCounter;
        }

        @Override
        public void run() {
            super.run();
            for (int i = 0; i < 10000; i++) {
                this.pointCounter.decrement();
            }
            System.out.println("DecrementingThread:" + this.pointCounter.getPoints());
        }
    }


    private static class PointCounter {
        private int point = 0;

        Object lock = new Object();

        public synchronized void increment() {
            synchronized (this.lock) {
                point++;
            }
        }

        public synchronized void decrement() {
            synchronized (this.lock) {
                point--;
            }
        }

        public synchronized int getPoints() {
            return point;
        }
    }


}
