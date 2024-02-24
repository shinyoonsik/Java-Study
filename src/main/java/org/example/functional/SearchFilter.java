package org.example.functional;

@FunctionalInterface
public interface SearchFilter {

    boolean isMatched(Customer customer);
}
