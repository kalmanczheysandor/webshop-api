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
    public ProductInfo saveProduct(@Valid @RequestBody ProductCreateCommand command) {
        log.info("Http request; Invocation-type:POST; URL:/api; body: " + command.toString());

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
    public ProductInfo findProduct(@PathVariable("id") int productId) {
        return productService.findProductById(productId);
    }


    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ProductInfo> deleteProduct(@PathVariable("productId") int productId) throws ProductNotFoundException {
        ProductInfo deletedProduct = productService.deleteProductById(productId);
        System.err.println("TÖRLÉS:" + productId);
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }

    @RequestMapping("/bloggers")
    public String bloggers() {
        return "bloggers";
    }

//    @PostMapping
//    public ResponseEntity<GameEventInfo> save(@Valid @RequestBody GameEventCreateCommand command) {
//        LOGGER.info("Http request, POST /api/gameevent, body: " + command.toString());
//        GameEventInfo savedEvent = gameEventService.save(command);
//        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<GameEventInfo>> findAll() {
//        LOGGER.info("Http request, GET /api/gameevent");
//        List<GameEventInfo> events = gameEventService.findAll();
//        return new ResponseEntity<>(events, HttpStatus.OK);
//    }
}
