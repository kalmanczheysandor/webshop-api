package hu.kalmancheysandor.webshop.dto.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Tag(name = "Az ügyfél regisztráció és módosítás válasz objektuma")
public class CustomerResponse {
    @Schema(description="Az ügyfél elsődleges kulcsa", example = "1")
    private Integer id;

    @Schema(description="Az ügyfél azonosítója", example = "customer001")
    private String identifier;

    @Schema(description="Az ügyfél jelszava", example = "ABC123456")
    private String password;

    @Schema(description="Az ügyfél keresztneve", example = "John")
    private String firstname;

    @Schema(description="Az ügyfél vezetékneve", example = "Berger")
    private String lastname;

    @Schema(description="Az ügyfél telefonszáma.", example = "004456889897")
    private String phone;

    @Schema(description="Az ügyfél e-mailcíme.", example = "john.berger@gmail.com")
    private String email;

    @Schema(description="Az ügyfél cimének adatai")
    private ResponseAddress address;

    @Schema(description="Az ügyfél használhatja-e a fiókját.", example = "true")
    private Boolean active;

    @Data
    @NoArgsConstructor
    @Tag(name = "Ügyfél címének adatai.")
    public static class ResponseAddress {
        @Schema(description="Elsődleges kulcs", example = "1")
        private Integer id;

        @Schema(description="Az ügyfél lakhelyének ország része", example = "United Kingdom")
        private String country;

        @Schema(description="Az ügyfél lakhelyének város része", example = "Manchester")
        private String city;

        @Schema(description="Az ügyfél lakhelyének utca része", example = "Queen Street 15")
        private String street;

        @Schema(description="Az ügyfél lakhelyének irányítószám része", example = "M24 LU4")
        private String postcode;
    }
}
