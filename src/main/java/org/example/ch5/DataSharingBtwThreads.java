package org.example.ch5;

public class DataSharingBtwThreads {
    public static void main(String[] args) throws InterruptedException {

        PointCounter pointCounter = new PointCounter();
        IncrementingThread incrementingThread = new IncrementingThread(pointCounter);
        DecrementingThread decrementingThread = new DecrementingThread(pointCounter);

        incrementingThread.start();
//        incrementingThread.join();

        decrementingThread.start();
//        decrementingThread.join();

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

        public void increment() {
            point++;
        }

        public void decrement() {
            point--;
        }

        public int getPoints() {
            return point;
        }
    }


}
