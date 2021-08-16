package hu.kalmancheysandor.webshop.service.exception;

public class ProductStillInUseException extends RuntimeException {
    private int productId;

    public ProductStillInUseException(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

}
