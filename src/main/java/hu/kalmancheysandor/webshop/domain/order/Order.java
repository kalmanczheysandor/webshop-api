package hu.kalmancheysandor.webshop.domain.order;

import hu.kalmancheysandor.webshop.domain.customer.Customer;
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

    @Column(name = "price_total_net")
    private float totalNetPrice;

    @Column(name = "price_total_gross")
    private float totalGrossPrice;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;


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

    public void entityReset(){
        setId(null);

    }
}
