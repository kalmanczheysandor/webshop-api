package hu.kalmancheysandor.webshop.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Tag(name = "Termék regisztráció és módosítás válasz objektuma")
public class ProductResponse {
    @Schema(description="A termék elsődleges kulcsa", example = "1")
    private Integer id;

    @Schema(description="A termék neve", example = "Fogkefe")
    private String name;

    @Schema(description="A termék nettó ára", example = "3655")
    private Float priceNet;

    @Schema(description="A termékre alkalmazott áfa", example = "25")
    private Integer priceVat;

    @Schema(description="A termék rövid leírása", example = "Fogkefe")
    private String description;

    @Schema(description="A termék láthatósaága a vásárlási felületen", example = "true")
    private Boolean active;
}
