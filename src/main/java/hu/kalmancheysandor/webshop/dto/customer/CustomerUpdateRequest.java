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
public class CustomerUpdateRequest {
   /*private String identifier;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private UpdateRequestAddress updateRequestAddress;
    private Boolean active;*/


    @NotNull(message = "Field must not be null")
    @Size(min=5, max=20,message="Field length must be between 5 and 20")
    @Schema(description="Az ügyfél azonosítója", example = "customer002")
    private String identifier;

    @NotNull(message = "Field must not be null")
    @Size(min=5, max=50,message="Field length must be between 5 and 50")
    @Schema(description="Az ügyfél jelszava", example = "987654321")
    private String password;

    @NotBlank(message = "Field must not be blank")
    @Size(max=255,message="Field length must be between 1 and 255")
    @Schema(description="Az ügyfél keresztneve", example = "Melissa")
    private String firstname;

    @NotBlank(message = "Field must not be blank")
    @Size(max=255,message="Field length must be between 1 and 255")
    @Schema(description="Az ügyfél vezetékneve", example = "Carpenter")
    private String lastname;

    @NotNull(message = "Field must not be null")
    @Size(min=5,max=25,message="Field length must be between 5 and 25")
    @Pattern(regexp = "[0-9]+", message = "Field must contain only digits")
    @Schema(description="Az ügyfél telefonszáma.", example = "00441234567")
    private String phone;

    @NotNull(message = "Field must not be null")
    @Email(message="Field must be a in e-mail format")
    @Schema(description="Az ügyfél e-mailcíme.", example = "melissa.carpenter@othermail.com")
    private String email;

    @Valid
    @NotNull(message = "Field must not be null")
    private CustomerUpdateRequest.UpdateRequestAddress address;

    @NotNull(message = "Field must not be null")
    @Schema(description="Az ügyfél használhatja-e a fiókját.", example = "true")
    private Boolean active;


    @Data
    @NoArgsConstructor
    @Validated
    @Tag(name = "Ügyfél címe a korábbi regisztráció módosításához.")
    public static class UpdateRequestAddress {
        @NotBlank(message = "Field must not be blank")
        @Size(max=255,message="Field length must be between 1 and 255")
        @Schema(description="Az ügyfél lakhelyének országrésze", example = "USA")
        private String country;

        @NotBlank(message = "Field must not be blank")
        @Size(max=255,message="Field length must be between 1 and 255")
        @Schema(description="Az ügyfél lakhelyének országrésze", example = "Huston")
        private String city;

        @NotBlank(message = "Field must not be blank")
        @Size(max=255,message="Field length must be between 1 and 255")
        @Schema(description="Az ügyfél lakhelyének irányítószáma", example = "Apple Avenue 652")
        private String street;

        @NotBlank(message = "Field must not be blank")
        @Size(max=255,message="Field length must be between 1 and 255")
        @Schema(description="Az ügyfél lakhelyének irányítószáma", example = "HUS-GR-232")
        private String postcode;
    }
    
}
