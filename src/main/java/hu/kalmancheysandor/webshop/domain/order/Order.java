package hu.kalmancheysandor.webshop.domain.order;

import hu.kalmancheysandor.webshop.domain.customer.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "price_total_net", nullable = false)
    private float totalNetPrice;

    @Column(name = "price_total_gross", nullable = false)
    private float totalGrossPrice;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @Column(name = "delivery_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    public OrderItem addOrderItem(OrderItem orderItem) {
        if(!orderItems.contains(orderItem)) {
            orderItems.add(orderItem);
            return orderItem;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalNetPrice=" + totalNetPrice +
                ", totalGrossPrice=" + totalGrossPrice +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
