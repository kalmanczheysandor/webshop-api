package hu.kalmancheysandor.webshop.dto.order;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Tag(name = "Megrendelési tétel objektuma.")
public class OrderItemResponse {
    @Schema(description = "Adott megrenelési tétel", example = "1")
    private Integer id;

    @Schema(description = "A megrendelési tételben levő termék")
    private ItemResponseProduct product;

    @Schema(description = "Összesített nettó ár", example = "3655")
    private Float totalNetPrice;

    @Schema(description = "Összesített bruttó ár", example = "5899")
    private Float totalGrossPrice;

    @Schema(description = "Vásárlási mennyiség", example = "2")
    private Integer quantity;

    @Data
    @NoArgsConstructor
    @Tag(name = "Termék adatai")
    public static class ItemResponseProduct {
        @Schema(description = "A termék elsődleges kulcsa", example = "1")
        private Integer id;

        @Schema(description = "A termék neve", example = "Fogkefe")
        private String name;

        @Schema(description = "A termék nettó ára", example = "3655")
        private Float priceNet;

        @Schema(description = "A termékre alkalmazott áfa", example = "25")
        private Integer priceVat;
    }
}
