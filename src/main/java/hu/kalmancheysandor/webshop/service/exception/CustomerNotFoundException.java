package hu.kalmancheysandor.webshop.service.exception;

public class CustomerNotFoundException extends RuntimeException {
    private int customerId;

    public CustomerNotFoundException(int customerId) {
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }
}
