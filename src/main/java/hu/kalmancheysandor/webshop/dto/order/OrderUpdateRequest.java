package hu.kalmancheysandor.webshop.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Data
@NoArgsConstructor
@Validated
public class OrderUpdateRequest {

    private Integer customerId;
    private List<Item> items;

    @Data
    @NoArgsConstructor
    @Validated
    public static class Item {
        private Integer productId;
        private Integer quantity;
    }
}
