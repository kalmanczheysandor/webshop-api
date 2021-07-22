package hu.kalmancheysandor.webshop.domain;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Product {
    private Integer id;
    private String name;
    private String code;
    private int categoryId;
    private String description;

    public Product(String name, String code, int categoryId, String description) {
        this.name = name;
        this.code = code;
        this.categoryId = categoryId;
        this.description = description;
    }
}
