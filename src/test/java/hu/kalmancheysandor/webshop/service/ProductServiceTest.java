package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.product.Product;
import hu.kalmancheysandor.webshop.dto.product.ProductCreateRequest;
import hu.kalmancheysandor.webshop.dto.product.ProductResponse;
import hu.kalmancheysandor.webshop.respository.ProductRepository;
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
import static org.mockito.ArgumentMatchers.any;
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

    ProductCreateRequest request01, request02, request03;
    Product product01, product02, product03;
    ProductResponse response01, response02, response03;

    @BeforeEach
    void init() {
        // Generation requests
        request01 = new ProductCreateRequest();
        request01.setName("Termék 1");
        request01.setPriceNet(1005.0f);
        request01.setPriceVat(15);
        request01.setDescription("Valami");
        request01.setActive(false);

        request02 = new ProductCreateRequest();
        request02.setName("Termék 2");
        request02.setPriceNet(2000.0f);
        request02.setPriceVat(160);
        request02.setDescription("semmi");
        request02.setActive(false);

        request03 = new ProductCreateRequest();
        request03.setName("Termék 3");
        request03.setPriceNet(356.0f);
        request03.setPriceVat(28);
        request03.setDescription("vagy valami?");
        request03.setActive(true);

        // Generation of products
        product01 = new Product();
        product01.setId(1);
        product01.setName("Termék 1");
        product01.setPriceNet(1005.0f);
        product01.setPriceVat(15);
        product01.setDescription("Valami");
        product01.setActive(false);

        product02 = new Product();
        product02.setId(2);
        product02.setName("Termék 2");
        product02.setPriceNet(2000.0f);
        product02.setPriceVat(160);
        product02.setDescription("semmi");
        product02.setActive(false);


        product03 = new Product();
        product03.setId(3);
        product03.setName("Termék 3");
        product03.setPriceNet(356.0f);
        product03.setPriceVat(28);
        product03.setDescription("vagy valami?");
        product03.setActive(true);


        response01 = new ProductResponse();
        response01.setId(1);
        response01.setName("Termék 1");
        response01.setPriceNet(1005.0f);
        response01.setPriceVat(15);
        response01.setDescription("Valami");
        response01.setActive(false);

        response02 = new ProductResponse();
        response02.setId(2);
        response02.setName("Termék 2");
        response02.setPriceNet(2000.0f);
        response02.setPriceVat(160);
        response02.setDescription("semmi");
        response02.setActive(false);

        response03 = new ProductResponse();
        response03.setId(3);
        response03.setName("Termék 3");
        response03.setPriceNet(356.0f);
        response03.setPriceVat(28);
        response03.setDescription("vagy valami?");
        response03.setActive(true);


    }


    @Test
    void testGetDogs_twoDogsInRepository_twoDogInfosReturned() {
        when(productRepository.listAllProduct()).thenReturn(List.of(product01, product02));

        when(modelMapper.map(product01, ProductResponse.class)).thenReturn(response01);
        when(modelMapper.map(product02, ProductResponse.class)).thenReturn(response02);
        assertThat(productService.listAllProduct())
                .hasSize(2)
                .containsExactly(response01, response02);
        verify(productRepository, times(1)).listAllProduct();
        verifyNoMoreInteractions(productRepository);
    }


    @Test
    void testSaveDog_fifiInCommand_successfulSave() {
        when(productRepository.saveProduct(any())).thenReturn(product01);

        when(modelMapper.map(request01,Product.class)).thenReturn(product01);
        when(modelMapper.map(product01, ProductResponse.class)).thenReturn(response01);

        ProductResponse prductSaved = productService.saveProduct(request01);
        assertEquals(response01, prductSaved);
    }
}