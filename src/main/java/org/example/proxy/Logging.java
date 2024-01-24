package org.example.proxy;

public class Logging implements Aspect{
    @Override
    public void before() {
        System.out.println("프린트 시작: " + System.currentTimeMillis());
    }

    @Override
    public void after() {
        System.out.println("프린트 종료: " + System.currentTimeMillis());
    }
}
