package org.example.ch3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadJoin {
    public static void main(String[] args) throws InterruptedException {
        // 모든 스레드가 실행을 종료했지만 1000000L을 계산하는 스레드는 오래걸려 종료되지 않는다! terminated되지 않은 스레드가 존재해 앱도 여전히 실행중이다
        // 이럴때, interrupt를 발생시키거나 스레드를 데몬으로 만들어서 엣지 케이스에대해 대처할 수 있다
        List<Long> numbers = Arrays.asList(0L, 35L, 35L, 234L, 254L, 10L);

        long startTime = System.currentTimeMillis();

        List<FactorialThread> threads = new ArrayList<>();
        for (Long number : numbers) {
            threads.add(new FactorialThread(number));
        }

        for (FactorialThread thread : threads) {
            thread.start();
        }

        for (FactorialThread thread : threads) {
            // 2초 기다리고 join하겠다 == thread의 연산이 길어져도 main스레드가 2초는 기다리겠다!
            thread.join(2000);
            if(thread.isAlive()){
                thread.interrupt();
            }
        }

        for (int i = 0; i < numbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);
            if (factorialThread.isFinished()) {
                System.out.println("[" + factorialThread.getName() +"]" + " Factorial of " + numbers.get(i) + " is " + factorialThread.getResult());
            } else {
                System.out.println("The calculation for " + numbers.get(i) + " is still in progress");
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Latency: " + (endTime - startTime) + "ms");
    }


    public static class FactorialThread extends Thread {
        private static int threadCount = 0;

        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
            this.setName(this.getClass().getSimpleName() + "" + threadCount);
            threadCount++;
        }

        @Override
        public void run() {
            this.result = factorial(this.inputNumber);
            this.isFinished = true;
        }

        private BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;
            for (long i = n; i > 0; i--) {
                if(this.isInterrupted()){
                    System.out.println(this.getName() + "스레드가 너무 오래걸려서 강제 종료함");
                    return BigInteger.ZERO;
                }
                tempResult = tempResult.multiply(BigInteger.valueOf(i));
            }
            return tempResult;
        }

        public boolean isFinished() {
            return this.isFinished;
        }

        public BigInteger getResult() {
            return this.result;
        }
    }
}
