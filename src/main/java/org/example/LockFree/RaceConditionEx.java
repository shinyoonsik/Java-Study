package org.example.LockFree;

public class RaceConditionEx {
    static class SharedResource {
        int count = 0;

        public void increment() {
            count++;
        }

        public void decrement() {
            count--;
        }

        public int getCount() {
            return count;
        }
    }

    static class UpThread extends Thread {
        private final SharedResource sharedResource;

        public UpThread(SharedResource sharedResource) {
            this.sharedResource = sharedResource;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                sharedResource.increment();
            }
        }
    }

    static class DownThread extends Thread {
        private final SharedResource sharedResource;

        public DownThread(SharedResource sharedResource) {
            this.sharedResource = sharedResource;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                sharedResource.decrement();
            }
        }
    }

    public static void practice() throws InterruptedException {
        SharedResource sharedResource = new SharedResource();

        UpThread upThread = new UpThread(sharedResource);
        DownThread downThread = new DownThread(sharedResource);

        upThread.start();
        downThread.start();

        upThread.join();
        downThread.join();

        System.out.println("Final count value: " + sharedResource.getCount());
    }

    public static void main(String[] args) throws InterruptedException {
        for(int i=0; i<100; i++){
            RaceConditionEx.practice();
        }
    }
}
