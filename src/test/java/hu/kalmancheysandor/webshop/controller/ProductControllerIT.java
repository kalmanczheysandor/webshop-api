package hu.kalmancheysandor.webshop.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import hu.kalmancheysandor.webshop.dto.product.ProductCreateRequest;
import hu.kalmancheysandor.webshop.exceptionhandling.FieldError;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.constraints.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductControllerIT {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = JsonMapper.builder().findAndAddModules().build();

    FieldError nameBlankError = new FieldError("name", "Field must not be blank");
    FieldError nameSizeError = new FieldError("name", "Field length must be between 1 and 255");

    FieldError priceNetPositiveOrZeroError = new FieldError("priceNet", "Field must not be negative");
    FieldError priceNetNullError = new FieldError("priceNet", "Field must not be null");

    FieldError priceVatPositiveOrZeroError = new FieldError("priceVat", "Field must not be negative");
    FieldError priceVatMaxError = new FieldError("priceVat", "Field must not be greater than 100");
    FieldError priceVatMinError = new FieldError("priceVat", "Field must not be greater than 0");

    FieldError descriptionNullError = new FieldError("description", "Field must not be null");
    FieldError descriptionSizeError = new FieldError("description", "Field length must be between 0 and 2000");

    FieldError activeNullError = new FieldError("active", "Field must not be null");

    @Test
    void testSave_whenValid_201Created() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest();
        request.setName("product01");
        request.setPriceNet(200f);
        request.setPriceVat(25);
        request.setDescription("");
        request.setActive(true);

        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test

    void testSave_whenNotValid_allFields() throws Exception {
        ProductCreateRequest request = new ProductCreateRequest();
        request.setName(null);
        request.setPriceNet(null);
        request.setPriceVat(null);
        request.setDescription(null);
        request.setActive(null);

        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }
}