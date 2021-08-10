package hu.kalmancheysandor.webshop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_customer_address")
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String country;
    private String city;
    private String street;
    private String postcode;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
