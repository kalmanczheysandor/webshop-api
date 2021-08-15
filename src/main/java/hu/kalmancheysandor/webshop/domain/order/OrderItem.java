package hu.kalmancheysandor.webshop.domain.order;

import hu.kalmancheysandor.webshop.domain.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;


@Data
@NoArgsConstructor
@Entity
@Table(name = "t_order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Formula("product_id.")
    //@Formula("product_id.")
    @Column(name="price_total_net")
    private int totalNetPrice;

    @Column(name="price_total_gross")
    private int totalGrossPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
