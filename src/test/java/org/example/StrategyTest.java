package org.example;


import org.example.service.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrategyTest {

    /**
     * 전략 패턴
     * Context: PurchaseService
     * Strategy: PaymentMethod
     * concreateStrategy: PayApple, PayCard...
     */
    @Test
    void OCP를_지킨_설계_테스트(){
        PurchaseService service1 = new PurchaseService(new PayCard());
        String result = service1.pay();
        assertEquals("카드로 지불", result);

        PurchaseService service2 = new PurchaseService(new PayApple());
        String result2 = service2.pay();
        assertEquals("애플페이로 지불", result2);

        PurchaseService service3 = new PurchaseService(new PayMoney());
        String result3 = service3.pay();
        assertEquals("현금으로 지불", result3);
    }
}
