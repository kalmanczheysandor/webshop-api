package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.order.Order;
import hu.kalmancheysandor.webshop.domain.order.OrderItem;
import hu.kalmancheysandor.webshop.domain.product.Product;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.respository.exception.RecordStillInUseException;
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

    public List<Product> listAllProduct() {
        return entityManager.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public Product updateProduct(Product product) {
        return entityManager.merge(product);
    }



    public void deleteProductById(int productId) {
        Product productToDelete = findProductById(productId);
        deleteProduct(productToDelete);
    }

    private void deleteProduct(Product product) {
        if(isProductStillInUse(product)) {
            throw new RecordStillInUseException(product.getId());
        }
        entityManager.remove(product);
    }


    private boolean isProductStillInUse(Product product) {
        List<Object> list = entityManager.createQuery("SELECT i FROM OrderItem i " +
                "WHERE i.product=:paramProduct")
                .setParameter("paramProduct",product)
                .setMaxResults(1)
                .getResultList();

        // Some older JPA implementations returns null instead of empty list
        if(list==null) {
            return false;
        }
        return !list.isEmpty();
    }





}
