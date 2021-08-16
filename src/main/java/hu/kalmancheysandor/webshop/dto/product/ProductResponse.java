package hu.kalmancheysandor.webshop.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponse {
    private Integer id;
    private String name;
    private Integer priceNet;
    private Float priceVat;
    private String description;
    private Boolean active;
}
