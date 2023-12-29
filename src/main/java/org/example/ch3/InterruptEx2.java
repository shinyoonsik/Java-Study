package org.example.ch3;

import java.math.BigInteger;

public class InterruptEx2 {
    public static void main(String[] args) {
        // 아주 큰 숫자를 넣는다면, 계산이 굉장히 오래걸린다
        // 결국, 계산이 끝날때까지 기다리거나 스레드를 인터럽트해서 앱을 종료해야 한다
        Thread thread = new Thread(new LongComputationTask(new BigInteger("200000"), new BigInteger("10000000")));

        thread.start();

        // 인터럽트를 발생시켜도 인터럽트를 처리하는 코드가 없다면 인터럽트 신호가 무시된다.
        // 따라서, 코드내에서 시간이 오래걸리는 핫스팟을 찾아야 한다.
        thread.interrupt();
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
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("인터럽트 발생!");
                    return BigInteger.ZERO;
                }
                result = result.multiply(base);
            }

            return result;
        }
    }
}
