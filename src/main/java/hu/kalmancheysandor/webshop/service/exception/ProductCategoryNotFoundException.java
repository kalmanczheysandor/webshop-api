package hu.kalmancheysandor.webshop.service.exception;

public class ProductCategoryNotFoundException extends RuntimeException {
    private int productCategoryId;


    public ProductCategoryNotFoundException(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

}
