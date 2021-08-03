package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.Customer;
import hu.kalmancheysandor.webshop.domain.Order;
import hu.kalmancheysandor.webshop.domain.Product;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class OrderRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Order saveOrder(Order order) {
        entityManager.persist(order);
        return order;
    }

    public Customer findCustomerById(Integer customerId) {
        Customer customer = entityManager.find(Customer.class, customerId);
        if( customer==null) {
            throw new RecordNotFoundByIdException(customerId);
        }
        return customer;
    }
}
