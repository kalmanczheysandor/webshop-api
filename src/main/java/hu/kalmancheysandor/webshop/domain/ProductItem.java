package hu.kalmancheysandor.webshop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class ProductItem {
    private Integer id;
    private String name;
    private String code;
    private String category;

    public ProductItem(String name, String code, String category) {
        this.name = name;
        this.code = code;
        this.category = category;
    }
}
