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

    //@JoinColumn(name = "billing_address_id")
    @OneToOne(mappedBy = "customer")
    private BillingCustomerAddress billingAddress;



    @Column(name = "billing_name")
    private String billingName;


//    @OneToOne(mappedBy = "customer",cascade = CascadeType.PERSIST)
//    private ShippingCustomerAddress shippingAddress;

    @Column(name = "shipping_name")
    private String shippingName;

    @Column(name = "is_active")
    private Boolean active;
}
