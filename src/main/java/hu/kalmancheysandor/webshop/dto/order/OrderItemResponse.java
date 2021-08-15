package hu.kalmancheysandor.webshop.dto.order;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OrderItemResponse {
    private Integer id;
    private OrderItemResponse.Product product;
    private float totalNetPrice;
    private float totalGrossPrice;
    private Integer quantity;

    @Data
    @NoArgsConstructor
    public static class Product {
        private Integer id;
        private String name;
        private Integer priceNet;
        private Float priceVat;
    }
}
