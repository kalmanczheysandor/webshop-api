package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.Customer;

import hu.kalmancheysandor.webshop.domain.Product;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CustomerRepository {
    private Map<Integer, Customer> customers = new HashMap<>();
    private int nextId = 0;


    public Customer saveCustomer(Customer customer) {
        customer.setId(nextId);
        customers.put(nextId, customer);
        nextId++;
        return customer;
    }

    public Customer modifyCustomerById(int customerId, Customer newCustomer) {
        if (!customers.containsKey(customerId)) {
            throw new RecordNotFoundByIdException(customerId);
        }

        Customer oldCustomer = customers.get(customerId);
        newCustomer.setId(oldCustomer.getId());
        customers.put(customerId, newCustomer);
        return newCustomer;
    }


    public List<Customer> listAllCustomer() {
        return new ArrayList<>(customers.values());
    }

    public Customer findCustomerById(Integer customerId) {
        if (customers.containsKey(customerId)) {
            return customers.get(customerId);
        }
        throw new RecordNotFoundByIdException(customerId);
    }

    public Customer deleteCustomerById(int customerId) {
        Customer customer = customers.remove(customerId);
        if (customer == null) {
            throw new RecordNotFoundByIdException(customerId);
        }
        return customer;
    }
}
