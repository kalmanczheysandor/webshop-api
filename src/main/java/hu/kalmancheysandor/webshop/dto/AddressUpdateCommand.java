package hu.kalmancheysandor.webshop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@Validated
public class AddressUpdateCommand {
    private String country;
    private String city;
    private String street;
    private String postcode;
}
