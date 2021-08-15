package hu.kalmancheysandor.webshop.dto.company;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyInfo {
    private String name;
    private CustomerAddressResponse address;
}
