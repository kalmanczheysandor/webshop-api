package hu.kalmancheysandor.webshop.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import hu.kalmancheysandor.webshop.dto.product.ProductCreateRequest;
import hu.kalmancheysandor.webshop.exceptionhandling.FieldError;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductSaveIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void test_save_whenSuccessful() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"name\": \"Fogkefe\",\n" +
                "  \"priceNet\": 3655,\n" +
                "  \"priceVat\": 25,\n" +
                "  \"description\": \"Ez egy jó fogkefe\",\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/products/")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestText))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Fogkefe")))
                .andExpect(jsonPath("$.priceNet", is(3655)))
                .andExpect(jsonPath("$.priceVat", is(25.0)))
                .andExpect(jsonPath("$.description", is("Ez egy jó fogkefe")))
                .andExpect(jsonPath("$.active", is(true)));
    }



    @Test
    void test_save_nameField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"priceNet\": 3655,\n" +
                "  \"priceVat\": 25,\n" +
                "  \"description\": \"Ez egy jó fogkefe\",\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("name")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be blank")));
    }

    @Test
    void test_save_nameField_whenEmpty() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"name\": \"\",\n" +
                "  \"priceNet\": 3655,\n" +
                "  \"priceVat\": 25,\n" +
                "  \"description\": \"Ez egy jó fogkefe\",\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("name")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be blank")));
    }


    @Test
    void test_save_nameField_whenTooLong() throws Exception {

        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"name\": \""+"a".repeat(256)+"\",\n" +
                "  \"priceNet\": 3655,\n" +
                "  \"priceVat\": 25,\n" +
                "  \"description\": \"Ez egy jó fogkefe\",\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("name")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 0 and 255")));
    }



    @Test
    void test_save_priceNetField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"name\": \"Fogkefe\",\n" +
                "  \"priceVat\": 25,\n" +
                "  \"description\": \"Ez egy jó fogkefe\",\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("priceNet")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_save_priceNetField_whenNotPositiveOrZero() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"name\": \"Fogkefe\",\n" +
                "  \"priceNet\": -1,\n" +
                "  \"priceVat\": 25,\n" +
                "  \"description\": \"Ez egy jó fogkefe\",\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("priceNet")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be negative")));
    }





    @Test
    void test_save_priceVatField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"name\": \"Fogkefe\",\n" +
                "  \"priceNet\": 3655,\n" +
                "  \"description\": \"Ez egy jó fogkefe\",\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("priceVat")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_save_priceVatField_whenOutOfMinimumValue() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"name\": \"Fogkefe\",\n" +
                "  \"priceNet\": 3655,\n" +
                "  \"priceVat\": -1,\n" +
                "  \"description\": \"Ez egy jó fogkefe\",\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("priceVat")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be less than 0")));
    }

    @Test
    void test_save_priceVatField_whenOutOfMaximumValue100() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"name\": \"Fogkefe\",\n" +
                "  \"priceNet\": 3655,\n" +
                "  \"priceVat\": 101,\n" +
                "  \"description\": \"Ez egy jó fogkefe\",\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("priceVat")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be greater than 100")));
    }








    @Test
    void test_save_descriptionField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"name\": \"Fogkefe\",\n" +
                "  \"priceNet\": 3655,\n" +
                "  \"priceVat\": 25,\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("description")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }




    @Test
    void test_save_descriptionField_whenTooLong() throws Exception {

        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"name\": \"Fogkefe\",\n" +
                "  \"priceNet\": 3655,\n" +
                "  \"priceVat\": 25,\n" +
                "  \"description\": \""+"a".repeat(2001)+"\",\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("description")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 0 and 2000")));
    }

    @Test
    void test_save_activeField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"name\": \"Fogkefe\",\n" +
                "  \"priceNet\": 3655,\n" +
                "  \"priceVat\": 25,\n" +
                "  \"description\": \"Ez egy jó fogkefe\"\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("active")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }


//    @Test
//    void test_save_priceNetField_whenMissing() throws Exception {
//        // Creating request text in form of json
//        String requestText = "{\n" +
//                "  \"name\": \"Fogkefe\",\n" +
//                "  \"priceNet\": 3655,\n" +
//                "  \"priceVat\": 25,\n" +
//                "  \"description\": \"Ez egy jó fogkefe\",\n" +
//                "  \"active\": true\n" +
//                "}";
//
//        // Statement(s) of response
//        mockMvc.perform(post("/api/admin/products/")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(requestText))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$[0].field", is("priceNet")))
//                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be blank")));
//    }



//    @Test
//
//    void testSave_whenNotValid_allFields() throws Exception {
//        ProductCreateRequest request = new ProductCreateRequest();
//        request.setName(null);
//        request.setPriceNet(null);
//        request.setPriceVat(null);
//        request.setDescription(null);
//        request.setActive(null);
//
//        mockMvc.perform(post("/api/admin/products/")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest());
//
//    }
}