package hu.kalmancheysandor.webshop.service.exception;

public class OrderItemNotFoundException extends RuntimeException {
    private int orderItemId;

    public OrderItemNotFoundException(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

}
