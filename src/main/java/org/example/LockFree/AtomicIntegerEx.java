package org.example.LockFree;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerEx {

    static class SharedResource {
        private final AtomicInteger count;

        public SharedResource(AtomicInteger count) {
            this.count = new AtomicInteger(count.get());
        }

        public void up() {
            this.count.incrementAndGet();
//            this.count++;
        }

        public void down() {
            this.count.decrementAndGet();
//            this.count--;
        }

        public int getCount() {
            return this.count.get();
        }
    }

    static class UpCountThread extends Thread {

        private final SharedResource sharedResource;

        public UpCountThread(String name, SharedResource sharedResource) {
            super(name);
            this.sharedResource = sharedResource;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println("thread name: " + super.getName());
                this.sharedResource.up();
            }
        }
    }

    static class DownCountThread extends Thread {
        private final SharedResource sharedResource;

        public DownCountThread(String name, SharedResource sharedResource) {
            super(name);
            this.sharedResource = sharedResource;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println("thread name: " + super.getName());
                this.sharedResource.down();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SharedResource sharedResource = new SharedResource(new AtomicInteger(0));
        UpCountThread upCountThread = new UpCountThread("Up Thread", sharedResource);
        UpCountThread upCountThread2 = new UpCountThread("Up Thread2", sharedResource);
        DownCountThread downCountThread = new DownCountThread("Down Thread", sharedResource);

        upCountThread.start();
        upCountThread2.start();
        downCountThread.start();

        upCountThread.join();
        upCountThread2.join();
        downCountThread.join();

        System.out.println("count값: " + sharedResource.getCount());
    }

}
