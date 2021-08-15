package hu.kalmancheysandor.webshop.domain.customer;

import hu.kalmancheysandor.webshop.domain.order.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    @Column(name = "is_active")
    private Boolean active;

    //@OneToOne(mappedBy = "customer",cascade = CascadeType.PERSIST)
    @OneToOne(mappedBy = "customer")
    private CustomerAddress address;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", identifier='" + identifier + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                (address==null?", address='null' ":", address='YYYYY' ")+
                ", active=" + active +
                '}';


    }
}
