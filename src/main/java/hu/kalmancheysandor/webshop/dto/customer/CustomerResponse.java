package hu.kalmancheysandor.webshop.dto.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

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
    private ResponseAddress address;
    private Boolean active;

    @Data
    @NoArgsConstructor
    public static class ResponseAddress {
        private Integer id;
        private String country;
        private String city;
        private String street;
        private String postcode;
    }
}
