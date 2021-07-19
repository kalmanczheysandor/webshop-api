package hu.kalmancheysandor.webshop.service.exception;

public class ProductNotFoundException extends Exception {
    private int productId;


    public ProductNotFoundException(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

}
