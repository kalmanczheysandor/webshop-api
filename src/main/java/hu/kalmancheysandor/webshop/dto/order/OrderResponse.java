package hu.kalmancheysandor.webshop.dto.order;


import hu.kalmancheysandor.webshop.domain.order.DeliveryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
@Tag(name = "Megrendelés regisztráció és módosítás válasz objektuma")
public class OrderResponse {
    @Schema(description = "A megrendelés elsődleges kulcsa", example = "1")
    private Integer id;

    @Schema(description = "A megrendelés összesített nettó ára", example = "99999")
    private float totalNetPrice;

    @Schema(description = "A megrendelés összesített bruttó ára", example = "158889")
    private float totalGrossPrice;

    @Schema(description = "Az ügyfél adatai")
    private ResponseCustomer customer;

    @Schema(description = "A megrendelésben szereplő tételek listája")
    private List<ResponseItem> items;

    @Schema(description = "A megrendelés állapota", example = "PURCHASED")
    private DeliveryStatus deliveryStatus;

    @Data
    @NoArgsConstructor
    public static class ResponseCustomer {
        @Schema(description = "Az ügyfél elsődleges kulcsa", example = "1")
        private Integer id;

        @Schema(description = "Az ügyfél azonosítója", example = "customer001")
        private String identifier;

        @Schema(description = "Az ügyfél keresztneve", example = "John")
        private String firstname;

        @Schema(description = "Az ügyfél vezetékneve", example = "Berger")
        private String lastname;

        @Schema(description = "Az ügyfél telefonszáma.", example = "004456889897")
        private String phone;

        @Schema(description = "Az ügyfél e-mailcíme.", example = "john.berger@gmail.com")
        private String email;

        @Schema(description = "Az ügyfél cimének adatai")
        private ResponseAddress address;
    }

    @Data
    @NoArgsConstructor
    @Tag(name = "Ügyfél címének adatai.")
    public static class ResponseAddress {
        @Schema(description = "Elsődleges kulcs", example = "1")
        private Integer id;

        @Schema(description = "Az ügyfél lakhelyének ország része", example = "United Kingdom")
        private String country;

        @Schema(description = "Az ügyfél lakhelyének város része", example = "Manchester")
        private String city;

        @Schema(description = "Az ügyfél lakhelyének utca része", example = "Queen Street 15")
        private String street;

        @Schema(description = "Az ügyfél lakhelyének irányítószám része", example = "M24 LU4")
        private String postcode;
    }


    @Data
    @NoArgsConstructor
    @Tag(name = "Megrendelési tétel objektuma.")
    public static class ResponseItem {
        @Schema(description = "Adott megrenelési tétel", example = "1")
        private Integer id;

        @Schema(description = "A termék adatai")
        private ResponseProduct product;

        @Schema(description = "Összesített nettó ár", example = "3655")
        private float totalNetPrice;

        @Schema(description = "Összesített bruttó ár", example = "5899")
        private float totalGrossPrice;

        @Schema(description = "Vásárlási mennyiség", example = "2")
        private Integer quantity;
    }


    @Data
    @NoArgsConstructor
    @Tag(name = "Termék adatai")
    public static class ResponseProduct {
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
