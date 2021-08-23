package hu.kalmancheysandor.webshop.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Data
@NoArgsConstructor
@Validated
public class OrderCreateRequest {

    private Integer customerId;

    @NotEmpty(message = "Field must not be empty")
    private List<Item> items;

    @Data
    @NoArgsConstructor
    @Validated
    public static class Item {
        private Integer productId;
        private Integer quantity;
    }
}
