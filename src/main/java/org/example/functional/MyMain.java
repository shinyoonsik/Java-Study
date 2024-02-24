package org.example.functional;

import java.util.List;


/**
 * 함수형 인터페이스를 통한 FP(Functional Programming)실습
 *
 * <DESC>
 * 함수형 인터페이스는 단 하나의 추상 메서드를 가지는 인터페이스로, 이를 통해 람다 표현식이나 메서드 참조를 사용하여 간결하고 명확한 코드를 작성할 수 있다.
 * 이러한 인터페이스를 사용함으로써, 개발자는 데이터를 변형하는 함수를 일급 객체로 취급하고, 이를 다른 함수에 인자로 전달하거나, 함수에서 반환할 수 있게 됩니다.
 * 이는 함수형 프로그래밍의 핵심 개념 중 하나인 일급 함수(first-class function)를 자바 언어에서 구현하는 방법이다
 */
public class MyMain {

    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();
        init(customerService);

        SearchFilter seoulFilter = customer -> "seoul".equals(customer.getLocation());
        List<Customer> customers = customerService.searchCustomerBy(seoulFilter);
        for (Customer customer : customers) {
            System.out.println("customer = " + customer);
        }

        System.out.println();
        SearchFilter busanFilter = customer -> "busan".equals(customer.getLocation());
        List<Customer> customers2 = customerService.searchCustomerBy(busanFilter);
        for (Customer customer : customers2) {
            System.out.println("customer = " + customer);
        }
    }

    private static void init(CustomerService customerService) {
        customerService.addCustomer(new Customer("1", "seoul", Gender.Male, 30));
        customerService.addCustomer(new Customer("2", "busan", Gender.Female, 30));
        customerService.addCustomer(new Customer("3", "seoul", Gender.Male, 30));
        customerService.addCustomer(new Customer("4", "Gwangju", Gender.Male, 30));
        customerService.addCustomer(new Customer("5", "incheon", Gender.Female, 30));
        customerService.addCustomer(new Customer("6", "seoul", Gender.Male, 30));
        customerService.addCustomer(new Customer("7", "seoul", Gender.Female, 30));
        customerService.addCustomer(new Customer("8", "seoul", Gender.Male, 30));
        customerService.addCustomer(new Customer("9", "busan", Gender.Male, 30));
        customerService.addCustomer(new Customer("10", "incheon", Gender.Female, 30));

    }
}
