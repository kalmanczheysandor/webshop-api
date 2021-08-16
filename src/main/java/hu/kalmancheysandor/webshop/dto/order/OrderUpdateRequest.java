package hu.kalmancheysandor.webshop.dto.order;

import hu.kalmancheysandor.webshop.domain.order.DeliveryStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Data
@NoArgsConstructor
@Validated
public class OrderUpdateRequest {
    private DeliveryStatus deliveryStatus;
}
