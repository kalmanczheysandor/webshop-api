package hu.kalmancheysandor.webshop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


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
}
