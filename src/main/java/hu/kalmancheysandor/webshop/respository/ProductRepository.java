package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.product.Product;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class ProductRepository {
    @PersistenceContext
    EntityManager entityManager;


    public Product saveProduct(Product product) {
        entityManager.persist(product);
        return product;
    }

    public Product findProductById(int productId) {
        Product product = entityManager.find(Product.class, productId);
        if( product==null) {
            throw new RecordNotFoundByIdException(productId);
        }
        return product;
    }

    public List<Product> listProducts() {

        List<Product> products = entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        return products;
    }

    public Product updateProduct(Product product) {
        Product updated = entityManager.merge(product);
        return updated;
    }

    public Product deleteProductById(int productId) {
        Product productToDelete = findProductById(productId);
        entityManager.remove(productToDelete);
        return productToDelete;
    }

    public Product deleteProduct(Product product) {
        entityManager.remove(product);
        return product;
    }


}
