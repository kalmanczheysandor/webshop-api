package hu.kalmancheysandor.webshop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "identifier", nullable = false)
    private String identifier;

    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;

    @Column(name = "address_billing_country")
    private String billingCountry;
    @Column(name = "address_billing_city")
    private String billingCity;
    @Column(name = "address_billing_street")
    private String billingStreet;
    @Column(name = "address_billing_postcode")
    private String billingPostcode;
    @Column(name = "address_billing_name")
    private String billingName;

    @Column(name = "address_shipping_country")
    private String shippingCountry;
    @Column(name = "address_shipping_city")
    private String shippingCity;
    @Column(name = "address_shipping_street")
    private String shippingStreet;
    @Column(name = "address_shipping_postcode")
    private String shippingPostcode;
    @Column(name = "address_shipping_name")
    private String shippingName;

    @Column(name = "is_active")
    private Boolean active;
}
