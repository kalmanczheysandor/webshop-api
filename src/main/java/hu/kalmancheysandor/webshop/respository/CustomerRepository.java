package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.Address;
import hu.kalmancheysandor.webshop.domain.Customer;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomerRepository {
    @PersistenceContext
    EntityManager entityManager;


    public Customer saveCustomer(Customer customer) {
        entityManager.persist(customer);
        return customer;
    }


    public Customer findCustomerById(Integer customerId) {
        Customer customer = entityManager.find(Customer.class, customerId);
        if( customer==null) {
            throw new RecordNotFoundByIdException(customerId);
        }
        return customer;
    }

    public List<Customer> listAllCustomer() {
        List<Customer> customers = entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        return customers;
    }

    public Customer updateCustomer(Customer customer) {
        Customer updated = entityManager.merge(customer);
        return updated;
    }

    public void deleteCustomerById(int customerId) {
        Customer customerToDelete = findCustomerById(customerId);
        deleteCustomer(customerToDelete);
    }

    private void deleteCustomer(Customer customer) {
        entityManager.remove(customer);
    }


}
