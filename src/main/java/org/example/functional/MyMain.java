package org.example.functional;

import java.util.List;

public class MyMain {
    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();
        init(customerService);

        List<Customer> customers = customerService.searchCustomerByGender(Gender.Female);

        for (Customer customer : customers) {
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
