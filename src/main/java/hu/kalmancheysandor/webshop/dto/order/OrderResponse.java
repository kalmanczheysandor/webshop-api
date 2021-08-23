package hu.kalmancheysandor.webshop.dto.order;


import hu.kalmancheysandor.webshop.domain.order.DeliveryStatus;
import lombok.Data;
import lombok.NoArgsConstructor;



import java.util.List;

@Data
@NoArgsConstructor
public class OrderResponse {
    private Integer id;
    private float totalNetPrice;
    private float totalGrossPrice;
    private ResponseCustomer customer;
    private List<ResponseItem> items;
    private DeliveryStatus deliveryStatus;

    @Data
    @NoArgsConstructor
    public static class ResponseCustomer {
        private Integer id;
        private String identifier;
        private String firstname;
        private String lastname;
        private String phone;
        private String email;
        private ResponseAddress address;
    }

    @Data
    @NoArgsConstructor
    public static class ResponseAddress {
        private Integer id;
        private String country;
        private String city;
        private String street;
        private String postcode;
    }


    @Data
    @NoArgsConstructor
    public static class ResponseItem {
        private Integer id;
        private ResponseProduct product;
        private float totalNetPrice;
        private float totalGrossPrice;
        private Integer quantity;
    }


    @Data
    @NoArgsConstructor
    public static class ResponseProduct {
        private Integer id;
        private String name;
        private Integer priceNet;
        private Float priceVat;
    }

}
