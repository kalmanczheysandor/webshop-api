package hu.kalmancheysandor.webshop.dto.order;


import lombok.Data;
import lombok.NoArgsConstructor;



import java.util.List;

@Data
@NoArgsConstructor
public class OrderResponse {
    private Integer id;
    private float totalNetPrice;
    private float totalGrossPrice;
    private Customer customer;
    private List<Item> items;

    @Data
    @NoArgsConstructor
    public static class Item {
        private Integer id;
        private Product product;
        private float totalNetPrice;
        private float totalGrossPrice;
        private Integer quantity;



    }


    @Data
    @NoArgsConstructor
    public static class Product {
        private Integer id;
        private String name;
        private Integer priceNet;
        private Float priceVat;
    }


    @Data
    @NoArgsConstructor
    public static class Customer {
        private Integer id;
        private String identifier;
        private String firstname;
        private String lastname;
        private String phone;
        private String email;
        private Address addsess;
    }

    @Data
    @NoArgsConstructor
    public static class Address {
        private Integer id;
        private String country;
        private String city;
        private String street;
        private String postcode;
    }

}
