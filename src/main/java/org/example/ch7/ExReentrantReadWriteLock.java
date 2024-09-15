package org.example.ch7;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * ReentrantLock으로 읽기와 쓰기작업을 모두 동기화 시키는 경우 -> 읽는데 걸리는 시간: 2200ms ~ 2600ms
 * ReentrantReadWriteLock으로 읽기와 쓰기 작업의 동기화를 독립(읽기락은 공유락 개념, 쓰기락은 배탁적 락 개념)적으로 구성한 경우 -> 읽는데 걸리는 시간: 550ms ~ 800ms
 */
public class ExReentrantReadWriteLock {

    public static final int HIGHEST_PRICE = 1000;

    public static void main(String[] args) throws InterruptedException {
        InventoryDatabase inventoryDatabase = new InventoryDatabase();

        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
        }

        Thread writerThread = new Thread(() -> {
            while (true) {
                inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
                inventoryDatabase.removeItem(random.nextInt(HIGHEST_PRICE));

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        writerThread.setDaemon(true);
        writerThread.start();

        int numberOfReaderThreads = 7;
        List<Thread> readerThreads = new ArrayList<>();

        for (int readerIndex = 0; readerIndex < numberOfReaderThreads; readerIndex++) {
            Thread readerThread = new Thread(() -> {
                for (int i = 0; i < 100000; i++) {
                    int upperBoundPrice = random.nextInt(HIGHEST_PRICE);
                    int lowerBoundPrice = upperBoundPrice > 0 ? random.nextInt(upperBoundPrice) : 0;
                    inventoryDatabase.getNumberOfItemsInPriceRange(lowerBoundPrice, upperBoundPrice);
                }
            });

            readerThread.setDaemon(true);
            readerThreads.add(readerThread);
        }

        long startReadingTime = System.currentTimeMillis();
        for(Thread reader : readerThreads){
            reader.start();
        }

        for(Thread reader : readerThreads){
            reader.join();
        }

        long endReadingTime = System.currentTimeMillis();
        System.out.println(String.format("Reading하는데 걸린 시간: %d ms", endReadingTime - startReadingTime));
    }

    public static class InventoryDatabase {
        private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
        private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        private Lock readLock = reentrantReadWriteLock.readLock();
        private Lock writeLock = reentrantReadWriteLock.writeLock();
        private ReentrantLock lockObj = new ReentrantLock();


        public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
            try {
//                this.lockObj.lock();
                this.readLock.lock();

                Integer fromKey = this.priceToCountMap.ceilingKey(lowerBound);
                Integer toKey = this.priceToCountMap.floorKey(upperBound);

                if (fromKey == null || toKey == null) return 0;

                NavigableMap<Integer, Integer> rangeOfPrices = this.priceToCountMap.subMap(fromKey, true, toKey, true);

                int sum = 0;
                for (int numberOfItemsForPrice : rangeOfPrices.values()) {
                    sum += numberOfItemsForPrice;
                }

                return sum;
            } finally {
//                this.lockObj.unlock();
                this.readLock.unlock();
            }
        }

        public void addItem(int price) {
            try {
//                this.lockObj.lock();
                this.writeLock.lock();

                Integer numberOfItemsForPrice = priceToCountMap.get(price);
                if (numberOfItemsForPrice == null) {
                    this.priceToCountMap.put(price, 1);
                } else {
                    this.priceToCountMap.put(price, numberOfItemsForPrice + 1);
                }
            } finally {
//                this.lockObj.unlock();
                this.writeLock.unlock();
            }
        }

        public void removeItem(int price) {
            try {
//                this.lockObj.lock();
                this.writeLock.lock();

                Integer numberOfItemsForPrice = this.priceToCountMap.get(price);
                if (numberOfItemsForPrice == null || numberOfItemsForPrice == 1) {
                    this.priceToCountMap.remove(price);
                } else {
                    this.priceToCountMap.put(price, numberOfItemsForPrice - 1);
                }

            } finally {
//                this.lockObj.unlock();
                this.writeLock.unlock();
            }
        }
    }


}
