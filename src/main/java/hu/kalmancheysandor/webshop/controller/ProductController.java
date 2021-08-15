package hu.kalmancheysandor.webshop.controller;


import hu.kalmancheysandor.webshop.dto.product.ProductCreateRequest;
import hu.kalmancheysandor.webshop.dto.product.ProductResponse;
import hu.kalmancheysandor.webshop.dto.product.ProductUpdateRequest;
import hu.kalmancheysandor.webshop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse saveCompany(@Valid @RequestBody ProductCreateRequest command) {
        return productService.saveProduct(command);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProduct(@PathVariable("id") int productId, @Valid @RequestBody ProductUpdateRequest command) {
        return productService.updateProduct(productId,command);
    }



    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> listAllProduct() {
        List<ProductResponse> products = productService.listAllProduct();
        return products;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse findProductById(@PathVariable("id") int productId) {
        return productService.findProductById(productId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable("id") int productId) {
        productService.deleteProductById(productId);
    }
}
