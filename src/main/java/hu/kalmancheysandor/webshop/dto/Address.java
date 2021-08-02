package hu.kalmancheysandor.webshop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
@Data
@NoArgsConstructor
@Validated
public class Address {
    private String country;
    private String city;
    private String street;
    private String postcode;
}
