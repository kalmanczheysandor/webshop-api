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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderSaveIT {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void init() throws Exception {
        // Creating request text in form of json
        String productRequestText01 = "{\n" +
                "  \"name\": \"Apple\",\n" +
                "  \"priceNet\": 100,\n" +
                "  \"priceVat\": 10,\n" +
                "  \"description\": \"Very delicious\",\n" +
                "  \"active\": true\n" +
                "}";

        String productRequestText02 = "{\n" +
                "  \"name\": \"Peach\",\n" +
                "  \"priceNet\": 200,\n" +
                "  \"priceVat\": 10,\n" +
                "  \"description\": \"It is very delicious\",\n" +
                "  \"active\": false\n" +
                "}";

        String productRequestText03 = "{\n" +
                "  \"name\": \"Orange\",\n" +
                "  \"priceNet\": 300,\n" +
                "  \"priceVat\": 10,\n" +
                "  \"description\": \"\",\n" +
                "  \"active\": false\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(productRequestText01))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Apple")))
                .andExpect(jsonPath("$.priceNet", is(100.0)))
                .andExpect(jsonPath("$.priceVat", is(10)))
                .andExpect(jsonPath("$.description", is("Very delicious")))
                .andExpect(jsonPath("$.active", is(true)));

        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(productRequestText02))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.name", is("Peach")))
                .andExpect(jsonPath("$.priceNet", is(200.0)))
                .andExpect(jsonPath("$.priceVat", is(10)))
                .andExpect(jsonPath("$.description", is("It is very delicious")))
                .andExpect(jsonPath("$.active", is(false)));

        mockMvc.perform(post("/api/admin/products/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(productRequestText03))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("Orange")))
                .andExpect(jsonPath("$.priceNet", is(300.0)))
                .andExpect(jsonPath("$.priceVat", is(10)))
                .andExpect(jsonPath("$.description", is("")))
                .andExpect(jsonPath("$.active", is(false)));




        // Creating request text in form of json
        String customerRequestText01 = "{\n" +
                "  \"identifier\": \"customer_user\",\n" +
                "  \"password\": \"12121212\",\n" +
                "  \"firstname\": \"Mary\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036999999\",\n" +
                "  \"email\": \"mary.doe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Bury\",\n" +
                "    \"street\": \"Virgins Drive 2\",\n" +
                "    \"postcode\": \"B12 2UA\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";



        String customerRequestText02 = "{\n" +
                "  \"identifier\": \"user02\",\n" +
                "  \"password\": \"1111111111\",\n" +
                "  \"firstname\": \"Melissa\",\n" +
                "  \"lastname\": \"Griffin\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"melissa.griffin@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"USA\",\n" +
                "    \"city\": \"Texas\",\n" +
                "    \"street\": \"Murderers Road 1356\",\n" +
                "    \"postcode\": \"TXA-HUJ-556\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        String customerRequestText03 = "{\n" +
                "  \"identifier\": \"user03\",\n" +
                "  \"password\": \"222222222\",\n" +
                "  \"firstname\": \"Peter\",\n" +
                "  \"lastname\": \"Parker\",\n" +
                "  \"phone\": \"005244444444\",\n" +
                "  \"email\": \"peter.parker@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"USA\",\n" +
                "    \"city\": \"Dallas\",\n" +
                "    \"street\": \"President Avenue 3B\",\n" +
                "    \"postcode\": \"DA-LX-9\"\n" +
                "  },\n" +
                "  \"active\": false\n" +
                "}";



        // Statement(s) of response
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(customerRequestText01))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.identifier", is("customer_user")))
                .andExpect(jsonPath("$.password", is("12121212")))
                .andExpect(jsonPath("$.firstname", is("Mary")))
                .andExpect(jsonPath("$.lastname", is("Doe")))
                .andExpect(jsonPath("$.phone", is("0036999999")))
                .andExpect(jsonPath("$.email", is("mary.doe@gmail.com")))
                .andExpect(jsonPath("$.address.country", is("United Kingdom")))
                .andExpect(jsonPath("$.address.city", is("Bury")))
                .andExpect(jsonPath("$.address.street", is("Virgins Drive 2")))
                .andExpect(jsonPath("$.address.postcode", is("B12 2UA")))
                .andExpect(jsonPath("$.active", is(true)));

        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(customerRequestText02))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.identifier", is("user02")))
                .andExpect(jsonPath("$.password", is("1111111111")))
                .andExpect(jsonPath("$.firstname", is("Melissa")))
                .andExpect(jsonPath("$.lastname", is("Griffin")))
                .andExpect(jsonPath("$.phone", is("0036123456")))
                .andExpect(jsonPath("$.email", is("melissa.griffin@gmail.com")))
                .andExpect(jsonPath("$.address.country", is("USA")))
                .andExpect(jsonPath("$.address.city", is("Texas")))
                .andExpect(jsonPath("$.address.street", is("Murderers Road 1356")))
                .andExpect(jsonPath("$.address.postcode", is("TXA-HUJ-556")))
                .andExpect(jsonPath("$.active", is(true)));

        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(customerRequestText03))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.identifier", is("user03")))
                .andExpect(jsonPath("$.password", is("222222222")))
                .andExpect(jsonPath("$.firstname", is("Peter")))
                .andExpect(jsonPath("$.lastname", is("Parker")))
                .andExpect(jsonPath("$.phone", is("005244444444")))
                .andExpect(jsonPath("$.email", is("peter.parker@gmail.com")))
                .andExpect(jsonPath("$.address.country", is("USA")))
                .andExpect(jsonPath("$.address.city", is("Dallas")))
                .andExpect(jsonPath("$.address.street", is("President Avenue 3B")))
                .andExpect(jsonPath("$.address.postcode", is("DA-LX-9")))
                .andExpect(jsonPath("$.active", is(false)));
    }

    @Test
    void test_save_whenSuccessful() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"customerId\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"productId\": 1,\n" +
                "      \"quantity\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"productId\": 2,\n" +
                "      \"quantity\": 1\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/orders/")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestText))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.totalNetPrice", is(300.0)))
                .andExpect(jsonPath("$.customer.id", is(1)))
                .andExpect(jsonPath("$.customer.firstname", is("Mary")))
                .andExpect(jsonPath("$.customer.address.id", is(1)))
                .andExpect(jsonPath("$.customer.address.postcode", is("B12 2UA")))
                .andExpect(jsonPath("$.items[0].product.id", is(1)))
                .andExpect(jsonPath("$.items[0].product.name", is("Apple")))
                .andExpect(jsonPath("$.items[0].totalNetPrice", is(100.0)))
                .andExpect(jsonPath("$.items[0].quantity", is(1)));
    }

    @Test
    void test_save_customerIdField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"productId\": 1,\n" +
                "      \"quantity\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"productId\": 2,\n" +
                "      \"quantity\": 1\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/orders/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("customerId")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_save_customerIdField_whenNegativeValue() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"customerId\": -6,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"productId\": 1,\n" +
                "      \"quantity\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"productId\": 2,\n" +
                "      \"quantity\": 1\n" +
                "    }\n" +
                "  ]\n" +
                "}";


        // Statement(s) of response
        mockMvc.perform(post("/api/admin/orders/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("customerId")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be negative")));
    }

    @Test
    void test_save_itemsField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"customerId\": 1\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/orders/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("items")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be empty")));
    }

    @Test
    void test_save_itemsField_whenEmpty() throws Exception {
        // Creating request text in form of json
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"customerId\": 1,\n" +
                "  \"items\": [\n" +
                "  ]\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/orders/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("items")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be empty")));
    }

    @Test
    void test_save_items_productIdField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"customerId\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"quantity\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"productId\": 2,\n" +
                "      \"quantity\": 1\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/orders/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("items[0].productId")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_save_items_productIdField_whenNegativeValue() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"customerId\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"productId\": -1,\n" +
                "      \"quantity\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"productId\": 2,\n" +
                "      \"quantity\": 1\n" +
                "    }\n" +
                "  ]\n" +
                "}";


        // Statement(s) of response
        mockMvc.perform(post("/api/admin/orders/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("items[0].productId")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be negative")));
    }

    @Test
    void test_save_items_quantityField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"customerId\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"productId\": 1,\n" +
                "      \"quantity\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"productId\": 2\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/orders/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("items[1].quantity")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_save_items_quantityField_whenNegativeValue() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"customerId\": 1,\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"productId\": 1,\n" +
                "      \"quantity\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"productId\": 2,\n" +
                "      \"quantity\": -5\n" +
                "    }\n" +
                "  ]\n" +
                "}";


        // Statement(s) of response
        mockMvc.perform(post("/api/admin/orders/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("items[1].quantity")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be negative")));
    }

}