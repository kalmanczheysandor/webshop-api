package hu.kalmancheysandor.webshop.dto.customer;


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
    public static class Address {
        private String country;
        private String city;
        private String street;
        private String postcode;
    }
    
}
