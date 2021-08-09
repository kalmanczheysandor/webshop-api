package hu.kalmancheysandor.webshop.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@Validated
public class CompanyCreateCommand {

    private String name;
    private AddressCreateCommand address;
}
