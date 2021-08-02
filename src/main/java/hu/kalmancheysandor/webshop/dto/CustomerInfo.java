package hu.kalmancheysandor.webshop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerInfo {
    private Integer id;
    private String identifier;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;

    private String billingCountry;
    private String billingCity;
    private String billingStreet;
    private String billingPostcode;
    private String billingName;

    private String shippingCountry;
    private String shippingCity;
    private String shippingStreet;
    private String shippingPostcode;
    private String shippingName;

    private Boolean active;

}
