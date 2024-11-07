package org.example.ch7;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class ExReentrantLock {
    public static void main(String[] args) {
        CryptoCurrencyContainer cryptoCurrencyContainer = new CryptoCurrencyContainer();

        PriceUpdater priceUpdaterThread = new PriceUpdater(cryptoCurrencyContainer);
        PricePrint pricePrint = new PricePrint(cryptoCurrencyContainer);

        priceUpdaterThread.start();
        pricePrint.start();


    }

    /**
     * 읽기 작업에도 ㅣock이 필요한 이유
     * 1. 원자적 연산이 보장되는 않는 타입의 변수의 경우, 값을 업데이트하는 중간에 이를 읽으면 값이 불완전한 상태일 수 있다.
     * 2. 다중 필드를 업데이트 하는 경우, 각각의 필드들은 모두 독립적으로 업데이트되기 때문에 한 필드는 새로운 값이지만 다른 필드는 업데이트 이전 값일 수 있다.
     *    이렇게 되면 가격 간의 일관성이 없는 데이터를 읽게 된다.
     */
    public static class PricePrint extends Thread {
        private final CryptoCurrencyContainer cryptoCurrencyContainer;

        public PricePrint(CryptoCurrencyContainer cryptoCurrencyContainer) {
            this.cryptoCurrencyContainer = cryptoCurrencyContainer;
        }

        @Override
        public void run() {
            while(true){
                if(cryptoCurrencyContainer.getLockObj().tryLock()){
                    try{
                        Thread.sleep(2000);

                        double avalanchePrice = cryptoCurrencyContainer.getAvalanchePrice();
                        System.out.println(Thread.currentThread().getName() + " avalanchePrice = " + avalanchePrice);

                        double bitCoinPrice = cryptoCurrencyContainer.getBitCoinPrice();
                        System.out.println(Thread.currentThread().getName() + " bitCoinPrice = " + bitCoinPrice);

                        double ripplePrice = cryptoCurrencyContainer.getRipplePrice();
                        System.out.println(Thread.currentThread().getName() + " ripplePrice = " + ripplePrice);

                        double sandBoxPrice = cryptoCurrencyContainer.getSandBoxPrice();
                        System.out.println(Thread.currentThread().getName() + " sandBoxPrice = " + sandBoxPrice);
                    } catch (InterruptedException e){
                        System.out.println();
                    } finally {
                        cryptoCurrencyContainer.getLockObj().unlock();
                    }
                }else{
//                    System.out.println("읽기 작업전에 lock을 획득하지 못함");
                }
            }
        }
    }

    public static class PriceUpdater extends Thread {

        private final Random random;
        private final CryptoCurrencyContainer cryptoCurrencyContainer;

        public PriceUpdater(CryptoCurrencyContainer cryptoCurrencyContainer) {
            this.cryptoCurrencyContainer = cryptoCurrencyContainer;
            this.random = new Random();
        }

        @Override
        public void run() {
            while(true){
                if (cryptoCurrencyContainer.getLockObj().tryLock()) {
                    try {
                        Thread.sleep(2500);
                        System.out.println("PriceUpdater Thread= " + Thread.currentThread().getName());
                        cryptoCurrencyContainer.setAvalanchePrice(random.nextInt(20000));
                        cryptoCurrencyContainer.setBitCoinPrice(random.nextInt(100000));
                        cryptoCurrencyContainer.setRipplePrice(random.nextInt(9000));
                        cryptoCurrencyContainer.setSandBoxPrice(random.nextInt(900));

                    } catch (InterruptedException e){

                    } finally {
                        cryptoCurrencyContainer.getLockObj().unlock();
                    }
                }else{
//                    System.out.println("쓰기작업전에 Lock을 획득하지 못함");
                }
            }
        }
    }


    public static class CryptoCurrencyContainer {
        private ReentrantLock lockObj = new ReentrantLock(true);
        private double bitCoinPrice;
        private double avalanchePrice;
        private double sandBoxPrice;
        private double ripplePrice;

        public ReentrantLock getLockObj() {
            return lockObj;
        }

        public void setLockObj(ReentrantLock lockObj) {
            this.lockObj = lockObj;
        }

        public double getBitCoinPrice() {
            return bitCoinPrice;
        }

        public void setBitCoinPrice(double bitCoinPrice) {
            this.bitCoinPrice = bitCoinPrice;
        }

        public double getAvalanchePrice() {
            return avalanchePrice;
        }

        public void setAvalanchePrice(double avalanchePrice) {
            this.avalanchePrice = avalanchePrice;
        }

        public double getSandBoxPrice() {
            return sandBoxPrice;
        }

        public void setSandBoxPrice(double sandBoxPrice) {
            this.sandBoxPrice = sandBoxPrice;
        }

        public double getRipplePrice() {
            return ripplePrice;
        }

        public void setRipplePrice(double ripplePrice) {
            this.ripplePrice = ripplePrice;
        }
    }

}
