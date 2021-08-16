package hu.kalmancheysandor.webshop.domain.product;

import hu.kalmancheysandor.webshop.domain.order.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Data
@NoArgsConstructor
@Entity
@Table(name = "t_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name")
    private String name;
    @Column(name = "price_net")
    private Integer priceNet;

    @Column(name = "price_vat")
    private Float priceVat;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active")
    private Boolean active;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
