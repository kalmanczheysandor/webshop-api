package hu.kalmancheysandor.webshop.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


@Data
@NoArgsConstructor
@Validated
public class OrderItemUpdateRequest {
    private Integer productId;
    private Integer quantity;
}
