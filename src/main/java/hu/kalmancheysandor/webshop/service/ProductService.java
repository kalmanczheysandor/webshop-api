package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.product.Product;
import hu.kalmancheysandor.webshop.dto.product.ProductCreateRequest;
import hu.kalmancheysandor.webshop.dto.product.ProductResponse;
import hu.kalmancheysandor.webshop.dto.product.ProductUpdateRequest;
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

    public ProductResponse saveProduct(ProductCreateRequest command) {
        Product productToSave = modelMapper.map(command, Product.class);
        Product productSaved = productRepository.saveProduct(productToSave);
        return modelMapper.map(productSaved, ProductResponse.class);
    }


    public ProductResponse updateProduct(int productId, ProductUpdateRequest command) {
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

            return modelMapper.map(modifiedProduct, ProductResponse.class);
        } catch (RecordNotFoundByIdException e) {
            throw new CustomerNotFoundException(e.getId());
        }
    }

    public List<ProductResponse> listAllProduct() {
        List<Product> products = productRepository.listProducts();
        return products.stream()
                .map(item -> modelMapper.map(item, ProductResponse.class))
                .collect(Collectors.toList());
    }



    public ProductResponse findProductById(int productId) {
        try {
            Product product = productRepository.findProductById(productId);
            return modelMapper.map(product, ProductResponse.class);
        } catch (RecordNotFoundByIdException e) {
            throw new ProductNotFoundException(e.getId());
        }
    }

    public ProductResponse deleteProductById(int productId) {
        try {
            Product deletedProduct  = productRepository.deleteProductById(productId);
            return modelMapper.map(deletedProduct, ProductResponse.class);
        } catch (RecordNotFoundByIdException e) {
            throw new ProductNotFoundException(e.getId());
        }
    }
}
