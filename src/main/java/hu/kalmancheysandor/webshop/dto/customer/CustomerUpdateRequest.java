package hu.kalmancheysandor.webshop.dto.customer;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@Validated
public class CustomerUpdateRequest {
    private String identifier;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private Address address;
    private Boolean active;

    @Data
    @NoArgsConstructor
    @Validated
    @Tag(name = "Ügyfél címe a korábbi regisztráció módosításához.")
    public static class Address {
        private String country;
        private String city;
        private String street;
        private String postcode;
    }
    
}
