package hu.kalmancheysandor.webshop.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@Validated
public class ProductCreateRequest {
    @NotBlank(message = "Field must not be blank")
    @Size(min = 1,max=255,message="Field length must be between 1 and 255")
    private String name;

    @PositiveOrZero(message="Field must not be negative")
    @NotNull(message = "Field must not be null")
    private Float priceNet;

    @PositiveOrZero(message="Field must not be negative")
    @Max(value = 100,message = "Field must not be greater than 100")
    @Min(value = 0,message = "Field must not be less than 100")
    private Integer priceVat;

    @NotNull(message = "Field must not be null")
    @Size(min = 0,max=2000,message="Field length must be between 0 and 2000")
    private String description;

    @NotNull(message = "Field must not be null")
    private Boolean active;
}
