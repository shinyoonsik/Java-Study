package org.example.functional;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private List<Customer> customers;
    private Gender gender;

    public CustomerService() {
        this.customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    // 요구사항1 동일한 지역에 살고 있는 고객 목록 출력
    public List<Customer> searchCustomerByLocation(String location) {
        if (location == null) throw new RuntimeException("location이 null이다");
        if (location.isEmpty() || location.isBlank()) throw new RuntimeException("location이 비어있거나 빈 공백으로 채워져있다");

        List<Customer> foundCustomers = new ArrayList<>();
        for (Customer customer : this.customers) {
            if (location.equals(customer.getLocation())) {
                foundCustomers.add(customer);
            }
        }

        return foundCustomers;
    }

    // 요구사항2 성별로 검색
    public List<Customer> searchCustomerByGender(Gender gender) {
        if (gender == null) throw new RuntimeException("gender가 null이다");

        List<Customer> foundCustomers = new ArrayList<>();
        for (Customer customer : this.customers) {
            if (gender.equals(customer.getGender())) {
                foundCustomers.add(customer);
            }
        }

        return foundCustomers;
    }

    // 하나의 메소드로 리팩토링
    // 문제: 조건이 추가될 때마다 조건문과 SearchCondition을 계속 수정해주어야 한다
    public List<Customer> searchCustomerBy(SearchCondition condition, String value) {
        if (condition == null) throw new RuntimeException("condition이 null이다");
        if (value == null) throw new RuntimeException("value가 null이다");

        List<Customer> foundCustomers = new ArrayList<>();
        for (Customer customer : this.customers) {
            if(SearchCondition.Location.equals(condition)){
                if (value.equals(customer.getLocation())) {
                    foundCustomers.add(customer);
                }
            }else{
                if (value.equals(customer.getGender().name())) {
                    foundCustomers.add(customer);
                }
            }
        }

        return foundCustomers;
    }

    // 인터페이스에 의존하게 리팩토링
    // 조건이 추가되어도 기존 코드를 수정하지 않아도 된다. 새로운 조건에 맞는 filter만 넣어주면 된다
    public List<Customer> searchCustomerBy(SearchFilter filter) {
        if (filter == null) throw new RuntimeException("filter가 null이다");

        List<Customer> foundCustomers = new ArrayList<>();
        for (Customer customer : this.customers) {
            if(filter.isMatched(customer)){
                foundCustomers.add(customer);
            }
        }

        return foundCustomers;
    }
}
