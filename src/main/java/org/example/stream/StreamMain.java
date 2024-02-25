package org.example.stream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMain {
    public static void main(String[] args) {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Song", 45));
        customers.add(new Customer("Kim", 33));
        customers.add(new Customer("Park", 21));
        customers.add(new Customer("Lee", 67));
        customers.add(new Customer("Choi", 19));

        /**
         * 1. age > 30
         * 2. 오름차순
         * 3. 이름 리스트로 출력
         */

        // stream 적용전
        List<Customer> list = new ArrayList<>();
        for (Customer customer : customers) {
            if(customer.getAge() > 30){
                list.add(customer);
            }
        }

        Collections.sort(list);

        List<String> results = new ArrayList<>();
        for (Customer customer : list) {
            results.add(customer.getName());
        }

        for (String result : results) {
            System.out.println("name = " + result);
        }

        // stream 적용후
        List<String> list2 = customers.stream()
                .filter(customer -> customer.getAge() >= 30)
                .sorted()
                .map(Customer::getName)
                .toList();

        list2.forEach(System.out::println);


    }
}
