package hu.kalmancheysandor.webshop.service.exception;

public class OrderItemDuplicationException extends RuntimeException {
    private int productId;

    public OrderItemDuplicationException(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

}
