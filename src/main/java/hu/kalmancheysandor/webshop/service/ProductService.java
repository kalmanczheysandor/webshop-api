package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.Product;
import hu.kalmancheysandor.webshop.dto.ProductCreateCommand;
import hu.kalmancheysandor.webshop.dto.ProductInfo;
import hu.kalmancheysandor.webshop.respository.ProductRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.service.exception.ProductNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public ProductInfo saveProduct(ProductCreateCommand command) {
        Product productToSave = modelMapper.map(command, Product.class);
        Product productSaved = productRepository.saveProduct(productToSave);
        return modelMapper.map(productSaved, ProductInfo.class);
    }

    public List<ProductInfo> listAllProduct() {
        List<Product> products = productRepository.listAllProduct();
        return products.stream()
                .map(item -> modelMapper.map(item, ProductInfo.class))
                .collect(Collectors.toList());
    }



    public ProductInfo findProductById(int productId) {
        try {
            Product product = productRepository.findProductById(productId);
            return modelMapper.map(product, ProductInfo.class);
        } catch (RecordNotFoundByIdException e) {
            throw new ProductNotFoundException(e.getId());
        }
    }

    public ProductInfo deleteProductById(int productId) {
        try {
            Product deletedProduct = productRepository.deleteProductById(productId);
            return modelMapper.map(deletedProduct, ProductInfo.class);
        } catch (RecordNotFoundByIdException e) {
            throw new ProductNotFoundException(e.getId());
        }
    }
}
