package org.example.factory;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactoryMethodTest {

    @Test
    void 팩토리_메소드_패턴_테스트(){
        DataFactory<User> userFactory = UserDataFactory.getInstance();
        List<User> resultList = userFactory.createAll();
        User result = userFactory.createOne();

        assertEquals(3, resultList.size());
        assertEquals("유저", result.getName());

        CarDataFactory carFactory = CarDataFactory.getInstance();
        List<Car> resultList2 = carFactory.createAll();
        Car result2 = carFactory.createOne();

        assertEquals(3, resultList2.size());
        assertEquals("myCar", result2.getName());
    }
}
