package hu.kalmancheysandor.webshop.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponse {
    private Integer id;
    private String name;
    private Float priceNet;
    private Integer priceVat;
    private String description;
    private Boolean active;
}
