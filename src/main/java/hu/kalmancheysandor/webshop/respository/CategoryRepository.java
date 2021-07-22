package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.Category;
import hu.kalmancheysandor.webshop.domain.Customer;
import hu.kalmancheysandor.webshop.domain.Product;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CategoryRepository {
    private Map<Integer, Category> categories = new HashMap<>();
    private int nextId = 0;

    public Category saveCustomer(Category category) {
        category.setId(nextId);
        categories.put(nextId, category);
        nextId++;
        return category;
    }

    public Category modifyProductById(int categoryId, Category newCategory) {
        if (!categories.containsKey(categoryId)) {
            throw new RecordNotFoundByIdException(categoryId);
        }

        Category oldCategory = categories.get(categoryId);
        newCategory.setId(oldCategory.getId());
        categories.put(categoryId, newCategory);
        return newCategory;
    }

    public List<Category> listAllCategory() {
        return new ArrayList<>(categories.values());
    }

    public Optional<Category> findCategoryById(int categoryId) {
        if(categories.containsKey(categoryId)) {
            return Optional.of(categories.get(categoryId));
        }
        return Optional.empty();
    }

    public Category deleteCategoryById(int categoryId) {
        Category category=categories.remove(categoryId);
        if(category==null) {
            throw new RecordNotFoundByIdException(categoryId);
        }
        return category;
    }
}
