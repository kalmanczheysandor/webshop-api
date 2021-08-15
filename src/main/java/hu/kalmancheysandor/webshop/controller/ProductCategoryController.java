package hu.kalmancheysandor.webshop.controller;


import hu.kalmancheysandor.webshop.dto.product.ProductCategoryCreateRequest;
import hu.kalmancheysandor.webshop.dto.product.ProductCategoryResponse;
import hu.kalmancheysandor.webshop.dto.product.ProductCategoryUpdateRequest;
import hu.kalmancheysandor.webshop.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@Slf4j
public class ProductCategoryController {

    private ProductCategoryService productCategoryService;

    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductCategoryResponse saveCompany(@Valid @RequestBody ProductCategoryCreateRequest command) {
        return productCategoryService.saveProductCategory(command);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductCategoryResponse updateProductCategory(@PathVariable("id") int productCategoryId, @Valid @RequestBody ProductCategoryUpdateRequest command) {
        return productCategoryService.updateProductCategory(productCategoryId,command);
    }



    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductCategoryResponse> listAllProductCategory() {
        return productCategoryService.listAllProductCategory();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductCategoryResponse findProductCategoryById(@PathVariable("id") int productCategoryId) {
        return productCategoryService.findProductCategoryById(productCategoryId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductCategoryById(@PathVariable("id") int productCategoryId) {
        productCategoryService.deleteProductCategoryById(productCategoryId);
    }
}
