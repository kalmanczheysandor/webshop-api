package hu.kalmancheysandor.webshop.service.exception;

public class ProductCategoryStillInUseException extends RuntimeException {
    private int productCategoryId;


    public ProductCategoryStillInUseException(int productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public int getProductCategoryId() {
        return productCategoryId;
    }

}
