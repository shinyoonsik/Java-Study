package org.example.ch6;

public class DeadLock {

    static class Train implements Runnable {

        private String type;
        private Intersection intersection;


        public Train(Intersection intersection, String type) {
            this.intersection = intersection;
            this.type = type;
        }

        @Override
        public void run() {
            // A -> Seoul에서 Busan
            // B -> Busan에서 Seoul
            if(type.equals("A"))
                intersection.goFromEastToWest();
            else if(type.equals("B"))
                intersection.goFromNorthToSouth();
            else
                throw new RuntimeException("일치하는 type이 없습니다.");
        }
    }

    static class Intersection {
        private Object roadA = new Object();
        private Object roadB = new Object();

        public void goFromEastToWest(){
            while (true){
                synchronized (roadA){
                    System.out.println("roadA monitorLock 획득! " + "| current-Thread: " + Thread.currentThread().getName());

                    synchronized (roadB){
                        System.out.println("roadB monitorLock 획득! " + "| current-Thread: " + Thread.currentThread().getName());
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        public void goFromNorthToSouth(){
            while (true){
                synchronized (roadB){
                    System.out.println("roadB monitorLock 획득! " + "| current-Thread: " + Thread.currentThread().getName());

                    synchronized (roadA){
                        System.out.println("roadA monitorLock 획득! " + "| current-Thread: " + Thread.currentThread().getName());
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        Thread threadA = new Thread(new Train(intersection, "A"));
        Thread threadB = new Thread(new Train(intersection, "B"));

        threadA.start();
        threadB.start();
    }

}
