package hu.kalmancheysandor.webshop.dto.customer;

import hu.kalmancheysandor.webshop.dto.company.CustomerAddressResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
public class CustomerResponse {
    private Integer id;
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
    public static class Address {
        private Integer id;
        private String country;
        private String city;
        private String street;
        private String postcode;
    }
}
