package hu.kalmancheysandor.webshop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@Validated
public class ProductCreateCommand {
    @NotBlank
    private String name;
    @NotBlank
    private String code;
    @NotBlank
    private String category;
}
