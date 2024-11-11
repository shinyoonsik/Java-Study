package org.example.LockFree;

public class RaceConditionEx {
    static class SharedResource {
        int count = 0;

        public SharedResource(int count) {
            this.count = count;
        }

        public void up() {
            this.count++;
        }

        public void down() {
            this.count--;
        }

        public int getCount() {
            return this.count;
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
        SharedResource sharedResource = new SharedResource(3);
        UpCountThread upCountThread = new UpCountThread("Up Thread", sharedResource);
        DownCountThread downCountThread = new DownCountThread("Down Thread", sharedResource);

        upCountThread.start();
        downCountThread.start();

        upCountThread.join();
        downCountThread.join();

        System.out.println("count값: " + sharedResource.getCount());
    }
}
