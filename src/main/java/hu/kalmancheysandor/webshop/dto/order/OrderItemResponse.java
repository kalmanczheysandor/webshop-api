package hu.kalmancheysandor.webshop.dto.order;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemResponse {
    private Integer id;
    private ItemResponseProduct product;
    private Float totalNetPrice;
    private Float totalGrossPrice;
    private Integer quantity;

    @Data
    @NoArgsConstructor
    public static class ItemResponseProduct {
        private Integer id;
        private String name;
        private Float priceNet;
        private Integer priceVat;
    }
}
