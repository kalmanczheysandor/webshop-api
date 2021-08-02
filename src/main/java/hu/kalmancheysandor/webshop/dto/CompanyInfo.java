package hu.kalmancheysandor.webshop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CompanyInfo {
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String email;
    //private List<ProductInfo> products;
}
