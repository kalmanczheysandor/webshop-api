package hu.kalmancheysandor.webshop.dto.company;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor
@Validated
public class CompanyCreateCommand {

    private String name;
    private CustomerAddressCreateCommand address;
}
