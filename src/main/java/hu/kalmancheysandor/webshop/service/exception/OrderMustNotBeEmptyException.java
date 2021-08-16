package hu.kalmancheysandor.webshop.service.exception;

public class OrderMustNotBeEmptyException extends RuntimeException {
    private int orderId;


    public OrderMustNotBeEmptyException(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }
}
