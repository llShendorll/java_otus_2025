package ru.otus;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    public TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = map.firstEntry();
        if (entry == null) {
            return null;
        }
        Customer original = entry.getKey();
        Customer copy = new Customer(original.getId(), original.getName(), original.getScores());
        return Map.entry(copy, entry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = map.higherEntry(customer);
        if (entry == null) {
            return null;
        }
        Customer original = entry.getKey();
        Customer copy = new Customer(original.getId(), original.getName(), original.getScores());
        return Map.entry(copy, entry.getValue());
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
