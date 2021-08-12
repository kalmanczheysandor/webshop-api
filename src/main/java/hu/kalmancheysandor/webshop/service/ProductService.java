package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.Customer;
import hu.kalmancheysandor.webshop.domain.CustomerAddress;
import hu.kalmancheysandor.webshop.domain.Product;
import hu.kalmancheysandor.webshop.dto.*;
import hu.kalmancheysandor.webshop.respository.ProductRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.service.exception.CustomerNotFoundException;
import hu.kalmancheysandor.webshop.service.exception.ProductNotFoundException;
import org.modelmapper.Conditions;
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


    public ProductInfo updateProduct(int productId, ProductUpdateCommand command) {
        try {

            //Find persistant entities which references each-other
            Product productCurrentState = productRepository.findProductById(productId);

            // Creating overwriting objects
            Product productNewState = modelMapper.map(command, Product.class);

            // Ignore (by null value) overwriting fields to participate in overwrite act
            productNewState.setId(null);

            // The overwrite-act when all field value copied into the persisted object fields. However 'null' values are ignored to be copied!
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            modelMapper.map(productNewState, productCurrentState);

            // Providing persistence update
            Product modifiedProduct = productRepository.updateProduct(productCurrentState);

            return modelMapper.map(modifiedProduct, ProductInfo.class);
        } catch (RecordNotFoundByIdException e) {
            throw new CustomerNotFoundException(e.getId());
        }
    }

    public List<ProductInfo> listAllProduct() {
        List<Product> products = productRepository.listProducts();
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
            Product deletedProduct  = productRepository.deleteProductById(productId);
            return modelMapper.map(deletedProduct,ProductInfo.class);
        } catch (RecordNotFoundByIdException e) {
            throw new ProductNotFoundException(e.getId());
        }
    }
}
