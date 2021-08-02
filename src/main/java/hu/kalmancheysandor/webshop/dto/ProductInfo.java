package hu.kalmancheysandor.webshop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class ProductInfo {
    private Integer id;
    private String name;
    private Integer priceNet;
    private Float priceVat;
    private String description;
    private Boolean active;
}
