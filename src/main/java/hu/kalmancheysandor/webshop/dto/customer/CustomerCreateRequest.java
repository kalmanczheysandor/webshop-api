package hu.kalmancheysandor.webshop.dto.customer;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
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
    @Schema(description="Az ügyfél keresztneve", example = "John")
    private String firstname;

    @NotBlank(message = "Field must not be blank")
    @Size(max=255,message="Field length must be between 1 and 255")
    @Schema(description="Az ügyfél vezetékneve", example = "Berger")
    private String lastname;

    @NotNull(message = "Field must not be null")
    @Size(min=5,max=25,message="Field length must be between 5 and 25")
    @Pattern(regexp = "[0-9]+", message = "Field must contain only digits")
    @Schema(description="Az ügyfél telefonszáma.", example = "004456889897")
    private String phone;

    @NotNull(message = "Field must not be null")
    @Email(message="Field must be a in e-mail format")
    @Schema(description="Az ügyfél e-mailcíme.", example = "john.berger@gmail.com")
    private String email;

    @Valid
    @NotNull(message = "Field must not be null")
    private CustomerCreateRequest.CreateRequestAddress address;

    @NotNull(message = "Field must not be null")
    @Schema(description="Az ügyfél használhatja-e a fiókját.", example = "true")
    private Boolean active;

    @Data
    @NoArgsConstructor
    @Validated
    @Tag(name = "Ügyfél címe a regisztrációhoz.")
    public static class CreateRequestAddress {
        @NotBlank(message = "Field must not be blank")
        @Size(max=255,message="Field length must be between 1 and 255")
        @Schema(description="Az ügyfél lakhelyének ország része", example = "United Kingdom")
        private String country;

        @NotBlank(message = "Field must not be blank")
        @Size(max=255,message="Field length must be between 1 and 255")
        @Schema(description="Az ügyfél lakhelyének város része", example = "Manchester")
        private String city;

        @NotBlank(message = "Field must not be blank")
        @Size(max=255,message="Field length must be between 1 and 255")
        @Schema(description="Az ügyfél lakhelyének utca része", example = "Queen Street 15")
        private String street;

        @NotBlank(message = "Field must not be blank")
        @Size(max=255,message="Field length must be between 1 and 255")
        @Schema(description="Az ügyfél lakhelyének irányítószám része", example = "M24 LU4")
        private String postcode;
    }
}
