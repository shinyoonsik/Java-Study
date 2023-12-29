package org.example.ch3;

import java.math.BigInteger;

public class InterruptEx2 {
    public static void main(String[] args) {
        Thread thread = new Thread(new LongComputationTask(new BigInteger("200"), new BigInteger("10000")));

        // thread의 연산이 오래걸림에도 불구하고 데몬스레드로 실행했기때문에 main 스레드(비데몬)가 종료되면 thread도 종료된다.
        thread.setDaemon(true);

        thread.start();
    }

    private static class LongComputationTask implements Runnable {
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(this.base + "^" + this.power + " = " + pow(this.base, this.power));
        }

        private BigInteger pow(BigInteger base, BigInteger power){
            BigInteger result = BigInteger.ONE;
            for(BigInteger i = BigInteger.ZERO; !i.equals(power); i = i.add(BigInteger.ONE)){
                System.out.println(result);
                result = result.multiply(base);
            }

            return result;
        }
    }
}