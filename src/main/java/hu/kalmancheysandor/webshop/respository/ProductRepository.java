package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.ProductItem;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepository {

    private Map<Integer, ProductItem> products = new HashMap<>();
    private int nextId = 0;

    public ProductRepository() {
        saveProduct(new ProductItem("BMW","CFX-VE-663","Vehicle"));
        saveProduct(new ProductItem("Audi","CFX-VE-569","Vehicle"));
        saveProduct(new ProductItem("Trabant","CFX-VE-003","Vehicle"));
        saveProduct(new ProductItem("Suzuki","CFX-VE-002","Vehicle"));
        saveProduct(new ProductItem("Mercedes","CFX-VE-958","Vehicle"));
        saveProduct(new ProductItem("Toyota","CFX-VE-459","Vehicle"));
    }

    public ProductItem saveProduct(ProductItem toSave) {
        toSave.setId(nextId);
        products.put(nextId, toSave);
        nextId++;
        return toSave;
    }

    public List<ProductItem> listAllProduct() {
        return new ArrayList<>(products.values());
    }

    public Optional<ProductItem> findProductById(Integer id) {
        return products.containsKey(id) ? Optional.of(products.get(id)) : Optional.empty();
    }


    public ProductItem deleteProductById(int productId) throws RecordNotFoundException{
        ProductItem productItem=products.remove(productId);
        if(productItem==null) {
            throw new RecordNotFoundException();
        }

        return productItem;
    }
}
