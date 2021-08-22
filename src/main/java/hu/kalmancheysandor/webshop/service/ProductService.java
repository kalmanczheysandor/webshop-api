package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.product.Product;
import hu.kalmancheysandor.webshop.dto.product.ProductCreateRequest;
import hu.kalmancheysandor.webshop.dto.product.ProductResponse;
import hu.kalmancheysandor.webshop.dto.product.ProductUpdateRequest;
import hu.kalmancheysandor.webshop.respository.ProductRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.respository.exception.RecordStillInUseException;
import hu.kalmancheysandor.webshop.service.exception.ProductNotFoundException;
import hu.kalmancheysandor.webshop.service.exception.ProductStillInUseException;
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

            Product productCurrentState = productRepository.findProductById(productId);
            Product productNewState = modelMapper.map(command, Product.class);
            productNewState.setId(null);


            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            modelMapper.map(productNewState, productCurrentState);

            Product modifiedProduct = productRepository.updateProduct(productCurrentState);

            return modelMapper.map(modifiedProduct, ProductResponse.class);
        } catch (RecordNotFoundByIdException e) {
            throw new ProductNotFoundException(e.getId());
        }
    }

    public List<ProductResponse> listAllProduct() {
        List<Product> products = productRepository.listAllProduct();
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

    public void deleteProductById(int productId) {
        try {
            productRepository.deleteProductById(productId);
        } catch (RecordNotFoundByIdException e) {
            throw new ProductNotFoundException(e.getId());
        }
        catch (RecordStillInUseException e){
            throw new ProductStillInUseException(e.getId());
        }
    }
}
