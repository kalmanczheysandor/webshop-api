package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.product.Product;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductRepositoryIT {

    Product product01;
    Product product02;

    @Autowired
    ProductRepository repository;

    @BeforeEach
    void init() {
        // Setting up product entities
        product01 = new Product();
        product01.setName("Product01");
        product01.setPriceVat(25);
        product01.setPriceNet(150f);
        product01.setDescription("My description");
        product01.setActive(true);

        product02 = new Product();
        product02.setName("Product02");
        product02.setPriceVat(250);
        product02.setPriceNet(1500f);
        product02.setDescription("My description");
        product02.setActive(false);

    }

    @Test
    @Transactional
    void test_saveProduct_oneItem() {
        // Initial assertion(s)
        assertThat(repository.listAllProduct()).isEmpty();

        // Operations
        Product saved = repository.saveProduct(product01);

        // Final assertion(s)
        assertEquals(1, saved.getId());
        assertEquals("Product01", saved.getName());
        assertEquals(25, saved.getPriceVat());
        assertEquals(150f, saved.getPriceNet());
        assertEquals("My description", saved.getDescription());
        assertEquals(true, saved.getActive());
        assertThat(repository.listAllProduct())
                .hasSize(1)
                .containsExactly(saved);
    }

    @Test
    @Transactional
    void test_saveProduct_twoItem() {
        // Initial assertion(s)
        assertThat(repository.listAllProduct()).isEmpty();

        // Operations
        Product saved1 = repository.saveProduct(product01);
        Product saved2 = repository.saveProduct(product02);

        // Final assertion(s)
        assertEquals(1, saved1.getId());
        assertEquals(2, saved2.getId());
        assertThat(repository.listAllProduct())
                .hasSize(2)
                .containsExactly(saved1, saved2);
    }

    @Test
    @Transactional
    void test_findProductById_foundSuccessfully() {
        // Initial assertion(s)
        assertThat(repository.listAllProduct()).isEmpty();

        // Operations
        Product saved1 = repository.saveProduct(product01);
        Product saved2 = repository.saveProduct(product02);
        Product productFound = repository.findProductById(2);

        // Final assertion(s)
        assertEquals(productFound, saved2);
    }

    @Test
    @Transactional
    void test_findProductById_notFound() {
        // Initial assertion(s)
        assertThat(repository.listAllProduct()).isEmpty();

        // Operations
        Product saved1 = repository.saveProduct(product01);
        Product saved2 = repository.saveProduct(product02);

        // Final assertion(s)
        assertThrows(RecordNotFoundByIdException.class, () -> repository.findProductById(5));

    }

    @Test
    @Transactional
    void test_updateProduct_updatedSuccessfully() {
        // Initial assertion(s)
        assertThat(repository.listAllProduct()).isEmpty();

        // Operations
        product02.setActive(true);
        Product saved1 = repository.saveProduct(product01);
        Product saved2 = repository.saveProduct(product02);

        assertEquals(true, saved2.getActive());
        assertThat(repository.listAllProduct())
                .hasSize(2)
                .containsExactly(saved1, saved2);

        saved2.setActive(false);
        repository.updateProduct(saved2);

        // Final assertion(s)
        assertEquals(false, saved2.getActive());
        assertThat(repository.listAllProduct())
                .hasSize(2)
                .containsExactly(saved1, saved2);
    }

    @Test
    @Transactional
    void test_updateProduct_insertInsteadOfUpdate() {
        // Initial assertion(s)
        assertThat(repository.listAllProduct()).isEmpty();

        // Operations
        Product saved1 = repository.saveProduct(product01);
        assertEquals(1, saved1.getId());
        assertThat(repository.listAllProduct())
                .hasSize(1)
                .containsExactly(saved1);

        // Operations
        product02.setId(2);
        repository.updateProduct(product02);

        // Final assertion(s)
        assertThat(repository.listAllProduct()).hasSize(2);
    }

    @Test
    @Transactional
    void test_deleteProduct_deletedSuccessfully() {
        // Initial assertion(s)
        assertThat(repository.listAllProduct()).isEmpty();

        // Operations
        Product saved1 = repository.saveProduct(product01);
        Product saved2 = repository.saveProduct(product02);

        assertThat(repository.listAllProduct())
                .hasSize(2)
                .containsExactly(saved1, saved2);

        // Operations
        repository.deleteProductById(2);

        // Final assertion(s)
        assertThat(repository.listAllProduct())
                .hasSize(1)
                .containsExactly(saved1);
    }

    @Test
    @Transactional
    void test_deleteProduct_deletedUnsuccessfully_idNotExist() {
        // Initial assertion(s)
        assertThat(repository.listAllProduct()).isEmpty();

        // Operations
        Product saved1 = repository.saveProduct(product01);
        Product saved2 = repository.saveProduct(product02);

        // Final assertion(s)
        assertThat(repository.listAllProduct())
                .hasSize(2)
                .containsExactly(saved1, saved2);
        assertThrows(RecordNotFoundByIdException.class, () -> repository.deleteProductById(20));

    }
}