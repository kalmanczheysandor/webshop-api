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
public class CustomerListAndDeleteIT {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void init() throws Exception {
        // Creating request text in form of json
        String requestText01 = "{\n" +
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



        String requestText02 = "{\n" +
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

        String requestText03 = "{\n" +
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

        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText01));
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText02));
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText03));
    }


    @Test
    void test_list_whenSuccessful() throws Exception {

        // Statement(s) of response
        mockMvc.perform(get("/api/admin/customers/")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].identifier", is("customer_user")))
                .andExpect(jsonPath("$[0].password", is("12121212")))
                .andExpect(jsonPath("$[0].firstname", is("Mary")))
                .andExpect(jsonPath("$[0].lastname", is("Doe")))
                .andExpect(jsonPath("$[0].phone", is("0036999999")))
                .andExpect(jsonPath("$[0].email", is("mary.doe@gmail.com")))
                .andExpect(jsonPath("$[0].address.country", is("United Kingdom")))
                .andExpect(jsonPath("$[0].address.city", is("Bury")))
                .andExpect(jsonPath("$[0].address.street", is("Virgins Drive 2")))
                .andExpect(jsonPath("$[0].address.postcode", is("B12 2UA")))
                .andExpect(jsonPath("$[0].active", is(true)))

                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].identifier", is("user02")))
                .andExpect(jsonPath("$[1].password", is("1111111111")))
                .andExpect(jsonPath("$[1].firstname", is("Melissa")))
                .andExpect(jsonPath("$[1].lastname", is("Griffin")))
                .andExpect(jsonPath("$[1].phone", is("0036123456")))
                .andExpect(jsonPath("$[1].email", is("melissa.griffin@gmail.com")))
                .andExpect(jsonPath("$[1].address.country", is("USA")))
                .andExpect(jsonPath("$[1].address.city", is("Texas")))
                .andExpect(jsonPath("$[1].address.street", is("Murderers Road 1356")))
                .andExpect(jsonPath("$[1].address.postcode", is("TXA-HUJ-556")))
                .andExpect(jsonPath("$[1].active", is(true)))

                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].identifier", is("user03")))
                .andExpect(jsonPath("$[2].password", is("222222222")))
                .andExpect(jsonPath("$[2].firstname", is("Peter")))
                .andExpect(jsonPath("$[2].lastname", is("Parker")))
                .andExpect(jsonPath("$[2].phone", is("005244444444")))
                .andExpect(jsonPath("$[2].email", is("peter.parker@gmail.com")))
                .andExpect(jsonPath("$[2].address.country", is("USA")))
                .andExpect(jsonPath("$[2].address.city", is("Dallas")))
                .andExpect(jsonPath("$[2].address.street", is("President Avenue 3B")))
                .andExpect(jsonPath("$[2].address.postcode", is("DA-LX-9")))
                .andExpect(jsonPath("$[2].active", is(false)));
    }

    @Test
    void test_delete_whenSuccessful() throws Exception {

        // Statement(s) of response: deleting is successful
        mockMvc.perform(delete("/api/admin/customers/2")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

        // Statement(s) of response: less item is in list after deleting
        mockMvc.perform(get("/api/admin/customers/")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].identifier", is("customer_user")))
                .andExpect(jsonPath("$[0].password", is("12121212")))
                .andExpect(jsonPath("$[0].firstname", is("Mary")))
                .andExpect(jsonPath("$[0].lastname", is("Doe")))
                .andExpect(jsonPath("$[0].phone", is("0036999999")))
                .andExpect(jsonPath("$[0].email", is("mary.doe@gmail.com")))
                .andExpect(jsonPath("$[0].address.country", is("United Kingdom")))
                .andExpect(jsonPath("$[0].address.city", is("Bury")))
                .andExpect(jsonPath("$[0].address.street", is("Virgins Drive 2")))
                .andExpect(jsonPath("$[0].address.postcode", is("B12 2UA")))
                .andExpect(jsonPath("$[0].active", is(true)))

                .andExpect(jsonPath("$[1].id", is(3)))
                .andExpect(jsonPath("$[1].identifier", is("user03")))
                .andExpect(jsonPath("$[1].password", is("222222222")))
                .andExpect(jsonPath("$[1].firstname", is("Peter")))
                .andExpect(jsonPath("$[1].lastname", is("Parker")))
                .andExpect(jsonPath("$[1].phone", is("005244444444")))
                .andExpect(jsonPath("$[1].email", is("peter.parker@gmail.com")))
                .andExpect(jsonPath("$[1].address.country", is("USA")))
                .andExpect(jsonPath("$[1].address.city", is("Dallas")))
                .andExpect(jsonPath("$[1].address.street", is("President Avenue 3B")))
                .andExpect(jsonPath("$[1].address.postcode", is("DA-LX-9")))
                .andExpect(jsonPath("$[1].active", is(false)));
    }

    @Test
    void test_delete_whenIdNotFound() throws Exception {

        // Statement(s) of response: deleting is successful
        mockMvc.perform(delete("/api/admin/customers/25")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("customerId")))
                .andExpect(jsonPath("$[0].errorMessage", is("Customer at id 25 is not found")));
    }

}