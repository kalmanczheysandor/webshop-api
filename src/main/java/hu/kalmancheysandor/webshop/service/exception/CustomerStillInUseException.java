package hu.kalmancheysandor.webshop.service.exception;

public class CustomerStillInUseException extends RuntimeException {
    private int customerId;

    public CustomerStillInUseException(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }
}
