package hu.kalmancheysandor.webshop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerInfo {
    private Integer id;
    private String identifier;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private AddressInfo address;
    private Boolean active;
}
