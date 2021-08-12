package hu.kalmancheysandor.webshop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@Validated
public class ProductUpdateCommand {
    private String name;
    private Integer priceNet;
    private Float priceVat;
    private String description;
    private Boolean active;
}
