package hu.kalmancheysandor.webshop.exceptionhandling;

import hu.kalmancheysandor.webshop.service.exception.ProductNotFoundException;
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
        FieldError fieldError = new FieldError("productId","Product with id " + exception.getProductId() + " is not found");
        return new ResponseEntity<>(List.of(fieldError), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(GameEventNotFound.class)
//    public ResponseEntity<List<hu.progmasters.boardgamepub.exceptionhandling.ValidationError>> handleGameEventNotFound(GameEventNotFound exception) {
//        hu.progmasters.boardgamepub.exceptionhandling.ValidationError validationError = new hu.progmasters.boardgamepub.exceptionhandling.ValidationError("gameEventId",
//                "game event with id " + exception.getIdNotFound() + " is not found");
//        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
//    }

}
