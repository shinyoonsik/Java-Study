package org.example.functional;

import java.util.List;

public class MyMain {
    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();
        init(customerService);

        List<Customer> result = customerService.searchCustomerBy(SearchCondition.Gender, "Female");
        for (Customer customer : result) {
            System.out.println("customer = " + customer);
        }

        System.out.println(Gender.Male + " " + Gender.Male.getClass().getName());
        System.out.println(Gender.Male.name() + " " + Gender.Male.name().getClass().getName());
        System.out.println(Gender.Male.equals(Gender.Male.name()));
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
