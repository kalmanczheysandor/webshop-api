package hu.kalmancheysandor.webshop.exceptionhandling;

import hu.kalmancheysandor.webshop.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldError>> handleValidationException(MethodArgumentNotValidException exception) {

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new FieldError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(fieldErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<List<FieldError>> handleProductNotFoundException(ProductNotFoundException exception) {
        FieldError fieldError = new FieldError("productId","Product at id " + exception.getProductId() + " is not found");
        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductStillInUseException.class)
    public ResponseEntity<List<FieldError>> handleProductStillInUseException(ProductStillInUseException exception) {
        FieldError fieldError = new FieldError("productId","Product at id " + exception.getProductId() + " is still in use");
        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<List<FieldError>> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        FieldError fieldError = new FieldError("customerId","Customer at id " + exception.getCustomerId() + " is not found");
        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerStillInUseException.class)
    public ResponseEntity<List<FieldError>> handleCustomerStillInUseException(CustomerStillInUseException exception) {
        FieldError fieldError = new FieldError("customerId","Customer at id " + exception.getCustomerId() + " is still in use");
        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<List<FieldError>> handleOrderNotFoundException(OrderNotFoundException exception) {
        FieldError fieldError = new FieldError("orderId","Order at id " + exception.getOrderId() + " is not found");
        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderItemNotFoundException.class)
    public ResponseEntity<List<FieldError>> handleOrderItemNotFoundException(OrderItemNotFoundException exception) {

        FieldError fieldError = new FieldError("orderItemId","Order item at id " + exception.getOrderItemId() + " is not found");
        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderItemDuplicationException.class)
    public ResponseEntity<List<FieldError>> handleOrderItemDuplicationException(OrderItemDuplicationException exception) {
        FieldError fieldError = new FieldError("productId","Multiple order item containing same product with id " + exception.getProductId() + "");
        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }


}
