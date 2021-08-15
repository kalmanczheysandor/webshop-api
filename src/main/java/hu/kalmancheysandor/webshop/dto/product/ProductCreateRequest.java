package hu.kalmancheysandor.webshop.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Validated
public class ProductCreateRequest {
    private String name;
    private Integer priceNet;
    private Float priceVat;
    private String description;
    private Boolean active;
    private Integer categoryId;
}
