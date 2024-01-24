package org.example.proxy;

public class UserAuth implements Aspect{
    @Override
    public void before() {
        System.out.println("사용자 권한 체크");
    }

    @Override
    public void after() {
        System.out.println("사용자 권한 마무리");
    }
}
