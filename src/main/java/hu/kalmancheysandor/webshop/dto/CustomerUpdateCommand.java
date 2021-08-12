package hu.kalmancheysandor.webshop.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@Validated
public class CustomerUpdateCommand {
    private String identifier;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private AddressCreateCommand address;
    private Boolean active;
    
}