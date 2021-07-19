package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.ProductItem;
import hu.kalmancheysandor.webshop.dto.ProductCreateCommand;
import hu.kalmancheysandor.webshop.dto.ProductInfo;
import hu.kalmancheysandor.webshop.respository.ProductRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundException;
import hu.kalmancheysandor.webshop.service.exception.ProductNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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
        ProductItem productItemToSave = modelMapper.map(command, ProductItem.class);
        ProductItem productItemSaved = productRepository.saveProduct(productItemToSave);

        return modelMapper.map(productItemSaved, ProductInfo.class);
    }

    public List<ProductInfo> listAllProduct() {
        List<ProductItem> products = productRepository.listAllProduct();

        return products.stream()
                .map(item -> modelMapper.map(item, ProductInfo.class))
                .collect(Collectors.toList());
    }

    public ProductInfo deleteProductById(int productId) throws ProductNotFoundException {
        try {
            ProductItem deletedProductItem = productRepository.deleteProductById(productId);
            return modelMapper.map(deletedProductItem, ProductInfo.class);
        } catch (RecordNotFoundException exp) {
            throw new ProductNotFoundException(productId);
        }
    }
}
