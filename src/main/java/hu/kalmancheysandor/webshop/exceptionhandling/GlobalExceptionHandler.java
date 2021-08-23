package hu.kalmancheysandor.webshop.exceptionhandling;

import hu.kalmancheysandor.webshop.service.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldError>> handleValidationException(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new FieldError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        // Send all error to logger
        for (FieldError fieldError : fieldErrors) {
            logAFieldError(fieldError);
        }
        return new ResponseEntity<>(fieldErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<List<FieldError>> handleProductNotFoundException(ProductNotFoundException exception) {
        FieldError fieldError = new FieldError("productId", "Product at id " + exception.getProductId() + " is not found");

        // Send error to logger
        logAFieldError(fieldError);

        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductStillInUseException.class)
    public ResponseEntity<List<FieldError>> handleProductStillInUseException(ProductStillInUseException exception) {
        FieldError fieldError = new FieldError("productId", "Product at id " + exception.getProductId() + " is still in use");
        logAFieldError(fieldError);
        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<List<FieldError>> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        FieldError fieldError = new FieldError("customerId", "Customer at id " + exception.getCustomerId() + " is not found");
        logAFieldError(fieldError);
        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerStillInUseException.class)
    public ResponseEntity<List<FieldError>> handleCustomerStillInUseException(CustomerStillInUseException exception) {
        FieldError fieldError = new FieldError("customerId", "Customer at id " + exception.getCustomerId() + " is still in use");
        logAFieldError(fieldError);
        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<List<FieldError>> handleOrderNotFoundException(OrderNotFoundException exception) {
        FieldError fieldError = new FieldError("orderId", "Order at id " + exception.getOrderId() + " is not found");
        logAFieldError(fieldError);
        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderMustNotBeEmptyException.class)
    public ResponseEntity<List<FieldError>> handleOrderMustNotBeEmptyException(OrderMustNotBeEmptyException exception) {
        FieldError fieldError = new FieldError("orderId", "Order at id " + exception.getOrderId() + " must not be empty");
        logAFieldError(fieldError);
        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<List<FieldError>> handleOrderItemNotFoundException(OrderItemNotFoundException exception) {
        FieldError fieldError = new FieldError("orderItemId", "Order item at id " + exception.getOrderItemId() + " is not found");
        logAFieldError(fieldError);
        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderItemDuplicationException.class)
    public ResponseEntity<List<FieldError>> handleOrderItemDuplicationException(OrderItemDuplicationException exception) {
        FieldError fieldError = new FieldError("productId", "Multiple order item containing same product with id " + exception.getProductId() + "");
        logAFieldError(fieldError);
        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }

    private void logAFieldError(FieldError fieldError) {
        log.error("An error was thrown. Details: field:" + fieldError.getField() + "; message:'" + fieldError.getErrorMessage() + "'");
    }
}
