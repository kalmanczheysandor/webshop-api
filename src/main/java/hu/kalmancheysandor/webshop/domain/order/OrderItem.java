package hu.kalmancheysandor.webshop.domain.order;

import hu.kalmancheysandor.webshop.domain.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Objects;


@Data
@NoArgsConstructor
@Entity
@Table(name = "t_order_item")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="price_total_net", nullable = false)
    private float totalNetPrice;

    @Column(name="price_total_gross", nullable = false)
    private float totalGrossPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", totalNetPrice=" + totalNetPrice +
                ", totalGrossPrice=" + totalGrossPrice +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
