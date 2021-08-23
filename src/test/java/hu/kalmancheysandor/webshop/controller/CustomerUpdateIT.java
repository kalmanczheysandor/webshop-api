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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CustomerUpdateIT {

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

        // Statement(s) of response
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText01))
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
                .content(requestText02))
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
                .content(requestText03))
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
    void test_update_whenSuccessful() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.identifier", is("user01")))
                .andExpect(jsonPath("$.password", is("123456")))
                .andExpect(jsonPath("$.firstname", is("John")))
                .andExpect(jsonPath("$.lastname", is("Doe")))
                .andExpect(jsonPath("$.email", is("johndoe@gmail.com")))
                .andExpect(jsonPath("$.address.country", is("United Kingdom")))
                .andExpect(jsonPath("$.address.city", is("Manchester")))
                .andExpect(jsonPath("$.address.street", is("Castlerigg Drive 25")))
                .andExpect(jsonPath("$.address.postcode", is("M24 4LW")))
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    void test_update_whenIdNotFound() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response: deleting is successful
        mockMvc.perform(put("/api/admin/customers/25")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("customerId")))
                .andExpect(jsonPath("$[0].errorMessage", is("Customer at id 25 is not found")));
    }

    @Test
    void test_update_identifierField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("identifier")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_update_identifierField_whenTooShort() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"" + "a".repeat(4) + "\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("identifier")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 5 and 20")));
    }

    @Test
    void test_update_identifierField_whenTooLong() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"" + "a".repeat(21) + "\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("identifier")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 5 and 20")));
    }

    @Test
    void test_update_passwordField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("password")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_update_passwordField_whenTooShort() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"" + "a".repeat(4) + "\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("password")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 5 and 50")));
    }

    @Test
    void test_update_passwordField_whenTooLong() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"" + "a".repeat(51) + "\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";


        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("password")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 5 and 50")));
    }

    @Test
    void test_update_firstnameField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("firstname")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be blank")));
    }

    @Test
    void test_update_firstnameField_whenTooLong() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"" + "a".repeat(256) + "\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("firstname")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 1 and 255")));
    }

    @Test
    void test_update_lastnameField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("lastname")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be blank")));
    }

    @Test
    void test_update_lastnameField_whenTooLong() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"" + "a".repeat(256) + "\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("lastname")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 1 and 255")));
    }

    @Test
    void test_update_phoneField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("phone")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_update_phoneField_whenTooShort() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"" + "1".repeat(4) + "\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";


        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("phone")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 5 and 25")));
    }

    @Test
    void test_update_phoneField_whenTooLong() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"" + "1".repeat(26) + "\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("phone")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 5 and 25")));
    }

    @Test
    void test_update_phoneField_whenWrongFormat() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"00AA36123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("phone")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must contain only digits")));
    }

    @Test
    void test_update_emailField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("email")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_update_emailField_whenWrongFormat() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"" + "ma@@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("email")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must be a in e-mail format")));
    }

    @Test
    void test_update_activeField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  }\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("active")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_update_addressField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_update_address_countryField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.country")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be blank")));
    }
    @Test
    void test_update_address_countryField_whenTooLong() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"" + "a".repeat(256) + "\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.country")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 1 and 255")));
    }

    @Test
    void test_update_address_cityField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.city")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be blank")));
    }

    @Test
    void test_update_address_cityField_whenTooLong() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"" + "a".repeat(256) + "\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.city")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 1 and 255")));
    }

    @Test
    void test_update_address_streetField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.street")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be blank")));
    }

    @Test
    void test_update_address_streetField_whenTooLong() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"" + "a".repeat(256) + "\",\n" +
                "    \"postcode\": \"M24 4LW\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.street")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 1 and 255")));
    }

    @Test
    void test_update_address_postcodeField_whenMissing() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.postcode")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be blank")));
    }

    @Test
    void test_update_address_postcodeField_whenTooLong() throws Exception {
        // Creating request text in form of json
        String requestText = "{\n" +
                "  \"identifier\": \"user01\",\n" +
                "  \"password\": \"123456\",\n" +
                "  \"firstname\": \"John\",\n" +
                "  \"lastname\": \"Doe\",\n" +
                "  \"phone\": \"0036123456\",\n" +
                "  \"email\": \"johndoe@gmail.com\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"United Kingdom\",\n" +
                "    \"city\": \"Manchester\",\n" +
                "    \"street\": \"Castlerigg Drive 25\",\n" +
                "    \"postcode\": \"" + "a".repeat(256) + "\"\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        // Statement(s) of response
        mockMvc.perform(put("/api/admin/customers/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.postcode")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 1 and 255")));
    }
}