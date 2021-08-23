package hu.kalmancheysandor.webshop.dto.order;

import hu.kalmancheysandor.webshop.domain.order.DeliveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@NoArgsConstructor
@Validated
@Tag(name = "A megrendelés státuszának a módosítása")
public class OrderUpdateRequest {
    @NotNull(message = "Field must not be null")
    @Schema(description="A korábban mentet megrendelés új státusza", example = "DELIVERED")
    private DeliveryStatus deliveryStatus;
}
