package hu.kalmancheysandor.webshop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductInfo {
    private Integer id;
    private String name;
    private String code;
    private String category;
}
