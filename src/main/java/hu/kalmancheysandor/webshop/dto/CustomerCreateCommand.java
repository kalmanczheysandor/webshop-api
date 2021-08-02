package hu.kalmancheysandor.webshop.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Validated
public class CustomerCreateCommand {
    private String identifier;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private Address billingAddress;
    private String billingName;
    private Address shippingAddress;
    private String shippingName;
    private Boolean active;
    
}
