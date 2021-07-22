package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.Product;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepository {

    private Map<Integer, Product> products = new HashMap<>();
    private int nextId = 0;

    public ProductRepository() {
        saveProduct(new Product("BMW","CFX-VE-663",1,"decripto"));
        saveProduct(new Product("Audi","CFX-VE-569",1,"decripto"));
        saveProduct(new Product("Trabant","CFX-VE-003",2,"decripto"));
        saveProduct(new Product("Suzuki","CFX-VE-002",2,"decripto"));
        saveProduct(new Product("Mercedes","CFX-VE-958",3,"decripto"));
        saveProduct(new Product("Toyota","CFX-VE-459",3,"decripto"));
    }

    public Product saveProduct(Product product) {
        product.setId(nextId);
        products.put(nextId, product);
        nextId++;
        return product;
    }


    public Product modifyProductById(int productId, Product newProduct) {
        if (!products.containsKey(productId)) {
            throw new RecordNotFoundByIdException(productId);
        }

        Product oldProduct = products.get(productId);
        newProduct.setId(oldProduct.getId());
        products.put(productId, newProduct);
        return newProduct;
    }

    public List<Product> listAllProduct() {
        return new ArrayList<>(products.values());
    }

    public Product findProductById(Integer productId) {
        if(products.containsKey(productId)) {
            return products.get(productId);
        }
        throw new RecordNotFoundByIdException(productId);
    }


    public Product deleteProductById(int productId) {
        Product product=products.remove(productId);
        if(product==null) {
            throw new RecordNotFoundByIdException(productId);
        }
        return product;
    }
}
