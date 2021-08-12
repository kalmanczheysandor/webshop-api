package hu.kalmancheysandor.webshop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "buyer_firstname")
    private String buyerFirstname;
    @Column(name = "buyer_lastname")
    private String buyerLastname;

    @Column(name = "price_total_net")
    private float totalNetPrice;

    @Column(name = "price_total_gross")
    private float totalGrossPrice;

    @OneToOne(mappedBy = "order")
    private OrderAddress address;

    @OneToMany(mappedBy = "order")
    List<OrderItem> items;

}
