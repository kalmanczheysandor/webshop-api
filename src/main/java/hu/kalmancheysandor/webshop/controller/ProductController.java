package hu.kalmancheysandor.webshop.controller;


import hu.kalmancheysandor.webshop.dto.product.ProductCreateRequest;
import hu.kalmancheysandor.webshop.dto.product.ProductResponse;
import hu.kalmancheysandor.webshop.dto.product.ProductUpdateRequest;
import hu.kalmancheysandor.webshop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@Slf4j
@Tag(name = "Termék adminisztráció")
public class ProductController {

    @Autowired
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Mentés",
            description = "Új termék felvétele.")
    public ProductResponse saveProduct(@Valid @RequestBody ProductCreateRequest command) {
        log.info("Http request; Method type:POST; URL:/api/admin/products/; Body:" + command.toString());
        return productService.saveProduct(command);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Módosítás",
            description = "Korábban mentett termék id általi elérése és mezőinek felülírása.")
    public ProductResponse updateProduct(@Parameter(description = "Termék id", example = "3")
                                         @PathVariable("id") int productId,
                                         @Valid @RequestBody ProductUpdateRequest command) {
        log.info("Http request; Method type:PUT; URL:/api/admin/product/" + productId + "/; Body:" + command.toString());
        return productService.updateProduct(productId, command);
    }


    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listázás",
            description = "Kilistázza az összes mentett terméket.")
    public List<ProductResponse> listAllProduct() {
        log.info("Http request; Method type:GET; URL:/api/admin/products/");
        List<ProductResponse> products = productService.listAllProduct();
        return products;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lekérés",
            description = "Korábban mentett termék id általi lekérése.")
    public ProductResponse findProductById(@Parameter(description = "Termék id", example = "3")
                                           @PathVariable("id") int productId) {
        log.info("Http request; Method type:GET; URL:/api/admin/products/" + productId + "/");
        return productService.findProductById(productId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Törlés",
            description = "Korábban mentett termék id általi törlése.")
    public void deleteProductById(@Parameter(description = "Termék id", example = "3")
                                  @PathVariable("id") int productId) {
        log.info("Http request; Method type:DELETE; URL:/api/admin/products/" + productId + "/");
        productService.deleteProductById(productId);
    }
}
