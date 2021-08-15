package hu.kalmancheysandor.webshop.domain.order;

import hu.kalmancheysandor.webshop.domain.company.Company;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_order_address")
public class OrderAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String country;
    private String city;
    private String street;
    private String postcode;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Company order;
}
