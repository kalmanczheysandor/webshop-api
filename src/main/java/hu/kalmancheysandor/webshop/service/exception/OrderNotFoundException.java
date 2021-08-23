package hu.kalmancheysandor.webshop.service.exception;

public class OrderNotFoundException extends RuntimeException {
    private int orderId;

    public OrderNotFoundException(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }
}
