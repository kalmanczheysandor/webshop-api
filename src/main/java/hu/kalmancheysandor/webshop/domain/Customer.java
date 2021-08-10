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

    @OneToOne(mappedBy = "customer")
    private CustomerAddress address;

    @Column(name = "is_active")
    private Boolean active;
}
