package hu.kalmancheysandor.webshop.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;


@Data
@NoArgsConstructor
@Validated
@Tag(name = "A megrendelési kosár tételei")
public class OrderItemUpdateRequest {
    @NotNull(message = "Field must not be null")
    @PositiveOrZero(message="Field must not be negative")
    @Schema(description="Már mentet termék elsődleges kulcsa", example = "1")
    private Integer productId;

    @NotNull(message = "Field must not be null")
    @PositiveOrZero(message="Field must not be negative")
    @Schema(description="A termékből vásárolt mennyiség", example = "3")
    private Integer quantity;
}
