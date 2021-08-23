package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.product.Product;
import hu.kalmancheysandor.webshop.dto.product.ProductCreateRequest;
import hu.kalmancheysandor.webshop.dto.product.ProductResponse;
import hu.kalmancheysandor.webshop.dto.product.ProductUpdateRequest;
import hu.kalmancheysandor.webshop.respository.ProductRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.respository.exception.RecordStillInUseException;
import hu.kalmancheysandor.webshop.service.exception.ProductNotFoundException;
import hu.kalmancheysandor.webshop.service.exception.ProductStillInUseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    ProductService productService;

    ProductCreateRequest productCreateRequest01, productCreateRequest02;
    ProductUpdateRequest productUpdateRequest01;
    Product productEntity01, productEntity02;
    Product productEntity01Updated;
    ProductResponse productResponse01, productResponse02;
    ProductResponse productResponse01Updated;

    @BeforeEach
    void init() {

        // Generation create requests
        productCreateRequest01 = new ProductCreateRequest();
        productCreateRequest01.setName("Termék 1");
        productCreateRequest01.setPriceNet(1005.0f);
        productCreateRequest01.setPriceVat(15);
        productCreateRequest01.setDescription("Valami");
        productCreateRequest01.setActive(false);

        productCreateRequest02 = new ProductCreateRequest();
        productCreateRequest02.setName("Termék 2");
        productCreateRequest02.setPriceNet(2000.0f);
        productCreateRequest02.setPriceVat(160);
        productCreateRequest02.setDescription("semmi");
        productCreateRequest02.setActive(false);

        // Generation update requests
        productUpdateRequest01 = new ProductUpdateRequest();
        productUpdateRequest01.setName("Termék 1b");
        productUpdateRequest01.setPriceNet(1005.0f);
        productUpdateRequest01.setPriceVat(15);
        productUpdateRequest01.setDescription("Valami");
        productUpdateRequest01.setActive(false);

        // Generation of entities
        productEntity01 = new Product();
        productEntity01.setId(1);
        productEntity01.setName("Termék 1");
        productEntity01.setPriceNet(1005.0f);
        productEntity01.setPriceVat(15);
        productEntity01.setDescription("Valami");
        productEntity01.setActive(false);

        productEntity02 = new Product();
        productEntity02.setId(2);
        productEntity02.setName("Termék 2");
        productEntity02.setPriceNet(2000.0f);
        productEntity02.setPriceVat(160);
        productEntity02.setDescription("semmi");
        productEntity02.setActive(false);

        productEntity01Updated = new Product();
        productEntity01Updated.setId(1);
        productEntity01Updated.setName("Termék 1");
        productEntity01Updated.setPriceNet(1005.0f);
        productEntity01Updated.setPriceVat(15);
        productEntity01Updated.setDescription("Valami");
        productEntity01Updated.setActive(false);

        // Generation of responses
        productResponse01 = new ProductResponse();
        productResponse01.setId(1);
        productResponse01.setName("Termék 1");
        productResponse01.setPriceNet(1005.0f);
        productResponse01.setPriceVat(15);
        productResponse01.setDescription("Valami");
        productResponse01.setActive(false);

        productResponse02 = new ProductResponse();
        productResponse02.setId(2);
        productResponse02.setName("Termék 2");
        productResponse02.setPriceNet(2000.0f);
        productResponse02.setPriceVat(160);
        productResponse02.setDescription("semmi");
        productResponse02.setActive(false);

        productResponse01Updated = new ProductResponse();
        productResponse01Updated.setId(1);
        productResponse01Updated.setName("Termék 1b");
        productResponse01Updated.setPriceNet(1005.0f);
        productResponse01Updated.setPriceVat(15);
        productResponse01Updated.setDescription("Valami");
        productResponse01Updated.setActive(false);

    }

    @Test
    void test_saveProduct() {
        // Mocking of repository method(s)
        when(productRepository.saveProduct(productEntity01)).thenReturn(productEntity01);

        // Mocking from request to entity
        when(modelMapper.map(productCreateRequest01, Product.class)).thenReturn(productEntity01);

        // Mocking from entity to response
        when(modelMapper.map(productEntity01, ProductResponse.class)).thenReturn(productResponse01);

        // Statement(s)
        assertThat(productService.saveProduct(productCreateRequest01))
                .isEqualTo(productResponse01);
        verify(productRepository, times(1)).saveProduct(productEntity01);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void test_updateProduct_whenProductIsFound() {
        // Mocking of repository method(s)
        when(productRepository.findProductById(1)).thenReturn(productEntity01);
        when(productRepository.updateProduct(productEntity01)).thenReturn(productEntity01Updated);

        // Mocking from request to entity
        when(modelMapper.map(productUpdateRequest01, Product.class)).thenReturn(productEntity02);
        doNothing().when(modelMapper).map(productEntity02, productEntity01);

        // Mocking from entity to response
        when(modelMapper.map(productEntity01Updated, ProductResponse.class)).thenReturn(productResponse01Updated);

        //Operation(s)
        ProductResponse response = productService.updateProduct(1, productUpdateRequest01);

        // Statement(s)
        assertEquals(productResponse01Updated, response);
        verify(productRepository, times(1)).findProductById(1);
        verify(productRepository, times(1)).updateProduct(productEntity01);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void test_updateProduct_whenProductIsNotFound() {
        // Mocking of repository method(s)
        when(productRepository.findProductById(1)).thenThrow(new RecordNotFoundByIdException(1));

        // Statement(s)
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1, productUpdateRequest01));
        verify(productRepository, times(1)).findProductById(1);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void test_listAllProduct() {
        // Mocking of repository method(s)
        when(productRepository.listAllProduct()).thenReturn(List.of(productEntity01, productEntity02));

        // Mocking entity to response
        when(modelMapper.map(productEntity01, ProductResponse.class)).thenReturn(productResponse01);
        when(modelMapper.map(productEntity02, ProductResponse.class)).thenReturn(productResponse02);

        // Statement(s)
        assertThat(productService.listAllProduct())
                .hasSize(2)
                .containsExactly(productResponse01, productResponse02);
        verify(productRepository, times(1)).listAllProduct();
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void test_findProductById_whenProductIsFound() {
        // Mocking of repository method(s)
        when(productRepository.findProductById(1)).thenReturn(productEntity01);

        // Mocking from entity to response
        when(modelMapper.map(productEntity01, ProductResponse.class)).thenReturn(productResponse01);

        // Statement(s)
        assertThat(productService.findProductById(1))
                .isEqualTo(productResponse01);
        verify(productRepository, times(1)).findProductById(1);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void test_findProductById_whenProductNotFound() {
        // Mocking of repository method(s)
        when(productRepository.findProductById(1)).thenThrow(new RecordNotFoundByIdException(1));

        // Statement(s)
        assertThrows(ProductNotFoundException.class, () -> productService.findProductById(1));

        verify(productRepository, times(1)).findProductById(1);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void test_deleteProductById_whenProductNotFound() {
        // Mocking of repository method(s)
        doThrow(new RecordNotFoundByIdException(1))
                .when(productRepository)
                .deleteProductById(1);

        // Statement(s)
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(1));
        verify(productRepository, times(1)).deleteProductById(1);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void test_deleteProductById_whenProductStillInUse() {
        // Mocking of repository method(s)
        doThrow(new RecordStillInUseException(1))
                .when(productRepository)
                .deleteProductById(1);

        // Statement(s)
        assertThrows(ProductStillInUseException.class, () -> productService.deleteProductById(1));
        verify(productRepository, times(1)).deleteProductById(1);
        verifyNoMoreInteractions(productRepository);
    }
}