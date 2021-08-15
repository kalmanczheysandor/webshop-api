package hu.kalmancheysandor.webshop.domain.product;

import hu.kalmancheysandor.webshop.domain.order.OrderItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


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

    @ManyToOne
    @JoinColumn(name="category_id")
    private ProductCategory category;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
}
