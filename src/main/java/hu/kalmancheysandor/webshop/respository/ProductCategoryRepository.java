package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.product.Product;
import hu.kalmancheysandor.webshop.domain.product.ProductCategory;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.respository.exception.RecordStillInUseException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductCategoryRepository {
    @PersistenceContext
    EntityManager entityManager;


    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        entityManager.persist(productCategory);
        return productCategory;
    }


    public ProductCategory findProductCategoryById(int productCategoryId) {
        ProductCategory productCategory = entityManager.find(ProductCategory.class, productCategoryId);
        if (productCategory == null) {
            throw new RecordNotFoundByIdException(productCategoryId);
        }
        return productCategory;
    }

    public List<ProductCategory> listAllProductCategory() {
        return entityManager.createQuery("SELECT c FROM ProductCategory c", ProductCategory.class).getResultList();
    }

    public ProductCategory updateProductCategory(ProductCategory productCategory) {
        return entityManager.merge(productCategory);
    }

    public void deleteProductCategoryById(int productCategoryId) {
        ProductCategory productCategoryToDelete = findProductCategoryById(productCategoryId);

        if(isProductCategoryInUse(productCategoryId)) {
            throw new RecordStillInUseException(productCategoryId);
        }

        deleteProductCategory(productCategoryToDelete);
    }

    public boolean isProductCategoryInUse(int productCategoryId) {
        int count = entityManager.createQuery("SELECT p FROM Product p " +
                "JOIN p.category c " +
                "WHERE c.id=:paramProductCategoryId", Product.class)
                .setParameter("paramProductCategoryId",productCategoryId)
                .getResultList().size();

        return (count > 0 );
    }

    private void deleteProductCategory(ProductCategory productCategory) {
        entityManager.remove(productCategory);
    }


}
