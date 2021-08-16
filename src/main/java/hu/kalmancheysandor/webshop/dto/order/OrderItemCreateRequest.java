package hu.kalmancheysandor.webshop.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Data
@NoArgsConstructor
@Validated
public class OrderItemCreateRequest {
    private Integer productId;
    private Integer quantity;
}
