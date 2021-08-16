package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.customer.Customer;
import hu.kalmancheysandor.webshop.domain.product.Product;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.respository.exception.RecordStillInUseException;
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


    public Customer findCustomerById(int customerId) {
        Customer customer = entityManager.find(Customer.class, customerId);
        if( customer==null) {
            throw new RecordNotFoundByIdException(customerId);
        }
        return customer;
    }

    public List<Customer> listAllCustomer() {
        return entityManager.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    public Customer updateCustomer(Customer customer) {
        return entityManager.merge(customer);
    }

    public void deleteCustomerById(int customerId) {
        Customer customerToDelete = findCustomerById(customerId);
        deleteCustomer(customerToDelete);
    }

    private void deleteCustomer(Customer customer) {
        if(isCustomerStillInUse(customer)) {
            throw new RecordStillInUseException(customer.getId());
        }
        entityManager.remove(customer);
    }

    private boolean isCustomerStillInUse(Customer customer) {
        List<Object> list = entityManager.createQuery("SELECT o FROM Order o " +
                "WHERE o.customer=:paramCustomer")
                .setParameter("paramCustomer",customer)
                .setMaxResults(1)
                .getResultList();

        // Some older JPA implementations returns null instead of empty list
        if(list==null) {
            return false;
        }
        return !list.isEmpty();
    }

}
