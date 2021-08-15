package hu.kalmancheysandor.webshop.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@Validated
public class ProductCategoryUpdateRequest {
    private String name;
    private Boolean active;
}
