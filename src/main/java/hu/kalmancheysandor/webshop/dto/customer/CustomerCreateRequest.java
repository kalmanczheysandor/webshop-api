package hu.kalmancheysandor.webshop.dto.customer;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@Validated
public class CustomerCreateRequest {

    @NotNull(message = "Field must not be null")
    @Size(min=5, max=20,message="Field length must be between 5 and 20")
    @Schema(description="Az ügyfél azonosítója", example = "customer001")
    private String identifier;

    @NotNull(message = "Field must not be null")
    @Size(min=5, max=50,message="Field length must be between 5 and 50")
    @Schema(description="Az ügyfél jelszava", example = "ABC123456")
    private String password;

    @NotBlank(message = "Field must not be blank")
    @Size(max=255,message="Field length must be between 1 and 255")
    @Schema(description="Az ügyfél keresztneve", example = "János")
    private String firstname;

    @NotBlank(message = "Field must not be blank")
    @Size(max=255,message="Field length must be between 1 and 255")
    @Schema(description="Az ügyfél vezetékneve", example = "Tóth")
    private String lastname;

    @NotNull(message = "Field must not be null")
    @Size(min=5,max=25,message="Field length must be between 5 and 25")
    @Pattern(regexp = "[0-9]+", message = "Field must contain only digits")
    @Schema(description="Az ügyfél telefonszáma.", example = "004456889897")
    private String phone;

    @NotNull(message = "Field must not be null")
    @Email(message="Field must be a in e-mail format")
    @Schema(description="Az ügyfél e-mailcíme.", example = "janos.toth@gmail.com")
    private String email;

    @NotNull(message = "Field must not be null")
    private Address address;

    @NotNull(message = "Field must not be null")
    @Schema(description="Az ügyfél használhatja-e a fiókját.", example = "true")
    private Boolean active;

    @Data
    @NoArgsConstructor
    @Validated
    @Tag(name = "Ügyfél címe a regisztrációhoz.")
    //@ApiModel()
    public static class Address {
        @NotNull(message = "Field must not be null")
        @Size(max=255,message="Field length must be between 1 and 255")
        @Schema(description="Az ügyfél lakhelyének országrésze", example = "4245")
        private String country;

        @NotNull(message = "Field must not be null")
        @Size(max=255,message="Field length must be between 1 and 255")
        @Schema(description="Az ügyfél lakhelyének országrésze", example = "4245")
        private String city;

        @NotNull(message = "Field must not be null")
        @Size(max=255,message="Field length must be between 1 and 255")
        @Schema(description="Az ügyfél lakhelyénak irányítószáma", example = "4245")
        private String street;

        @NotNull(message = "Field must not be null")
        @Size(max=255,message="Field length must be between 1 and 255")
        @Schema(description="Az ügyfél lakhelyénak irányítószáma", example = "4245")
        private String postcode;
    }
}
