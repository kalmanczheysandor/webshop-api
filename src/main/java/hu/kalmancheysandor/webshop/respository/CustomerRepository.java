package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.Customer;

import hu.kalmancheysandor.webshop.domain.Product;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class CustomerRepository {
    @PersistenceContext
    EntityManager entityManager;


    public Customer saveCustomer(Customer customer) {

        //entityManager.persist(customer.getBillingAddress());
        //entityManager.persist(customer.getShippingAddress());
        entityManager.persist(customer);

        return customer;
    }

//    public Product findProductById(Integer productId) {
//        Product product = entityManager.find(Product.class, productId);
//        if( product==null) {
//            throw new RecordNotFoundByIdException(productId);
//        }
//        return product;
//    }
//
//    public List<Product> listAllProduct() {
//
//        List<Product> products = entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
//        return products;
//    }
//
//    public Product updateProduct(Product product) {
//        Product updated = entityManager.merge(product);
//        return updated;
//    }
//
//    public Product deleteProductById(int productId) {
//        Product productToDelete = findProductById(productId);
//        entityManager.remove(productToDelete);
//        return productToDelete;
//    }
//
//    public Product deleteProduct(Product product) {
//        entityManager.remove(product);
//        return product;
//    }
}
