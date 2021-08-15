package hu.kalmancheysandor.webshop.dto.company;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
public class CustomerAddressResponse {
    private String country;
    private String city;
    private String street;
    private String postcode;
}
