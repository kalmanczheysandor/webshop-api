package hu.kalmancheysandor.webshop.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductListAndDeleteIT {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void init() throws Exception {
        // Creating request text in form of json
        String requestText01 = "{\n" +
                "  \"name\": \"Alma\",\n" +
                "  \"priceNet\": 115,\n" +
                "  \"priceVat\": 25,\n" +
                "  \"description\": \"Nagyon finom\",\n" +
                "  \"active\": true\n" +
                "}";

        String requestText02 = "{\n" +
                "  \"name\": \"Körte\",\n" +
                "  \"priceNet\": 1563,\n" +
                "  \"priceVat\": 5,\n" +
                "  \"description\": \"Ez is nagyon finom\",\n" +
                "  \"active\": false\n" +
                "}";

        String requestText03 = "{\n" +
                "  \"name\": \"Banán\",\n" +
                "  \"priceNet\": 25,\n" +
                "  \"priceVat\": 15,\n" +
                "  \"description\": \"\",\n" +
                "  \"active\": false\n" +
                "}";


        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText01));
        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText02));
        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText03));
    }


    @Test
    void test_list_whenSuccessful() throws Exception {

        // Statement(s) of response
        mockMvc.perform(get("/api/admin/products/")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Alma")))
                .andExpect(jsonPath("$[0].priceNet", is(115)))
                .andExpect(jsonPath("$[0].priceVat", is(25.0)))
                .andExpect(jsonPath("$[0].description", is("Nagyon finom")))
                .andExpect(jsonPath("$[0].active", is(true)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Körte")))
                .andExpect(jsonPath("$[1].priceNet", is(1563)))
                .andExpect(jsonPath("$[1].priceVat", is(5.0)))
                .andExpect(jsonPath("$[1].description", is("Ez is nagyon finom")))
                .andExpect(jsonPath("$[1].active", is(false)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("Banán")))
                .andExpect(jsonPath("$[2].priceNet", is(25)))
                .andExpect(jsonPath("$[2].priceVat", is(15.0)))
                .andExpect(jsonPath("$[2].description", is("")))
                .andExpect(jsonPath("$[2].active", is(false)));
    }

    @Test
    void test_delete_whenSuccessful() throws Exception {

        // Statement(s) of response: deleting is successful
        mockMvc.perform(delete("/api/admin/products/2")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        // Statement(s) of response: less item is in list after deleting
        mockMvc.perform(get("/api/admin/products/")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Alma")))
                .andExpect(jsonPath("$[0].priceNet", is(115)))
                .andExpect(jsonPath("$[0].priceVat", is(25.0)))
                .andExpect(jsonPath("$[0].description", is("Nagyon finom")))
                .andExpect(jsonPath("$[0].active", is(true)))

                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].name", is("Banán")))
                .andExpect(jsonPath("$[1].priceNet", is(25)))
                .andExpect(jsonPath("$[1].priceVat", is(15.0)))
                .andExpect(jsonPath("$[1].description", is("")))
                .andExpect(jsonPath("$[1].active", is(false)));
    }

    @Test
    void test_delete_whenItemNotFound() throws Exception {

        // Statement(s) of response: deleting is successful
        mockMvc.perform(delete("/api/admin/products/25")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("productId")))
                .andExpect(jsonPath("$[0].errorMessage", is("Product at id 25 is not found")));
    }

}