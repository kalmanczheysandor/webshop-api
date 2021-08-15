package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.product.ProductCategory;
import hu.kalmancheysandor.webshop.dto.product.ProductCategoryCreateRequest;
import hu.kalmancheysandor.webshop.dto.product.ProductCategoryResponse;
import hu.kalmancheysandor.webshop.dto.product.ProductCategoryUpdateRequest;
import hu.kalmancheysandor.webshop.respository.ProductCategoryRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.respository.exception.RecordStillInUseException;
import hu.kalmancheysandor.webshop.service.exception.ProductCategoryNotFoundException;
import hu.kalmancheysandor.webshop.service.exception.ProductCategoryStillInUseException;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductCategoryService(ProductCategoryRepository productCategoryRepository, ModelMapper modelMapper) {
        this.productCategoryRepository = productCategoryRepository;
        this.modelMapper = modelMapper;
    }

    public ProductCategoryResponse saveProductCategory(ProductCategoryCreateRequest command) {

        // Convert dto objects to entities
        ProductCategory productCategoryToSave = modelMapper.map(command, ProductCategory.class);

        //Persist all entities
        ProductCategory savedProductCategory = productCategoryRepository.saveProductCategory(productCategoryToSave);

        return modelMapper.map(savedProductCategory, ProductCategoryResponse.class);
    }

    public ProductCategoryResponse updateProductCategory(int productCategoryId, ProductCategoryUpdateRequest command) {
        try {

            //Find persistent entities which referencing each-other
            ProductCategory productCategoryCurrentState = productCategoryRepository.findProductCategoryById(productCategoryId);

            // Creating overwriting objects
            ProductCategory productCategoryNewState = modelMapper.map(command, ProductCategory.class);

            // Ignore (by null value) overwriting fields to participate in overwrite act
            productCategoryNewState.setId(null);

            // The overwrite-act when all field value copied into the persisted object fields. However 'null' values are ignored to be copied!
            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            modelMapper.map(productCategoryNewState, productCategoryCurrentState);

            // Providing persistence update
            ProductCategory modifiedProductCategory = productCategoryRepository.updateProductCategory(productCategoryCurrentState);

            return modelMapper.map(modifiedProductCategory, ProductCategoryResponse.class);
        } catch (RecordNotFoundByIdException e) {
            throw new ProductCategoryNotFoundException(e.getId());
        }
    }



    public List<ProductCategoryResponse> listAllProductCategory() {
        List<ProductCategory> productCategories = productCategoryRepository.listAllProductCategory();
        return productCategories.stream()
                .map(item -> modelMapper.map(item, ProductCategoryResponse.class))
                .collect(Collectors.toList());
    }


    public ProductCategoryResponse findProductCategoryById(int productCategoryId) {
        try {
            ProductCategory productCategory = productCategoryRepository.findProductCategoryById(productCategoryId);
            return modelMapper.map(productCategory, ProductCategoryResponse.class);
        } catch (RecordNotFoundByIdException e) {
            throw new ProductCategoryNotFoundException(e.getId());
        }
    }

    public void deleteProductCategoryById(int productCategoryId) {
        try {
            productCategoryRepository.deleteProductCategoryById(productCategoryId);
        } catch (RecordNotFoundByIdException e) {
            throw new ProductCategoryNotFoundException(e.getId());
        } catch (RecordStillInUseException e) {
            throw new ProductCategoryStillInUseException(e.getId());
        }
    }
}
