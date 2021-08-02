package hu.kalmancheysandor.webshop.controller;


import hu.kalmancheysandor.webshop.dto.ProductCreateCommand;
import hu.kalmancheysandor.webshop.dto.ProductInfo;
import hu.kalmancheysandor.webshop.service.ProductService;
import hu.kalmancheysandor.webshop.service.exception.ProductNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductInfo saveCompany(@Valid @RequestBody ProductCreateCommand command) {
        return productService.saveProduct(command);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductInfo> listAllProduct() {
        List<ProductInfo> products = productService.listAllProduct();
        return products;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductInfo findProductById(@PathVariable("id") int productId) {
        return productService.findProductById(productId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductInfo deleteProductById(@PathVariable("id") int productId){
        ProductInfo deletedProduct = productService.deleteProductById(productId);
        return deletedProduct;
    }
}
