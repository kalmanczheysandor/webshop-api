package hu.kalmancheysandor.webshop.controller;


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
public class CustomerSaveIT {

    @Autowired
    MockMvc mockMvc;

    @Test
    void test_save_whenSuccessful() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isCreated())
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
    void test_save_identifierField_whenMissing() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("identifier")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_save_identifierField_whenTooShort() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("identifier")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 5 and 20")));
    }

    @Test
    void test_save_identifierField_whenTooLong() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("identifier")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 5 and 20")));
    }

    @Test
    void test_save_passwordField_whenMissing() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("password")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_save_passwordField_whenTooShort() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("password")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 5 and 50")));
    }

    @Test
    void test_save_passwordField_whenTooLong() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("password")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 5 and 50")));
    }


    @Test
    void test_save_firstnameField_whenMissing() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("firstname")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be blank")));
    }

    @Test
    void test_save_firstnameField_whenTooLong() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("firstname")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 1 and 255")));
    }

    @Test
    void test_save_lastnameField_whenMissing() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("lastname")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be blank")));
    }

    @Test
    void test_save_lastnameField_whenTooLong() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("lastname")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 1 and 255")));
    }


    @Test
    void test_save_phoneField_whenMissing() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("phone")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_save_phoneField_whenTooShort() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("phone")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 5 and 25")));
    }

    @Test
    void test_save_phoneField_whenTooLong() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("phone")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 5 and 25")));
    }

    @Test
    void test_save_phoneField_whenWrongFormat() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("phone")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must contain only digits")));
    }


    @Test
    void test_save_emailField_whenMissing() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("email")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_save_emailField_whenWrongFormat() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("email")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must be a in e-mail format")));
    }

    @Test
    void test_save_activeField_whenMissing() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("active")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_save_addressField_whenMissing() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be null")));
    }

    @Test
    void test_save_address_countryField_whenMissing() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.country")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be blank")));
    }

    @Test
    void test_save_address_countryField_whenTooLong() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.country")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 1 and 255")));
    }

    @Test
    void test_save_address_cityField_whenMissing() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.city")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be blank")));
    }

    @Test
    void test_save_address_cityField_whenTooLong() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.city")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 1 and 255")));
    }

    @Test
    void test_save_address_streetField_whenMissing() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.street")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be blank")));
    }

    @Test
    void test_save_address_streetField_whenTooLong() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.street")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 1 and 255")));
    }

    @Test
    void test_save_address_postcodeField_whenMissing() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.postcode")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field must not be blank")));
    }

    @Test
    void test_save_address_postcodeField_whenTooLong() throws Exception {
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
        mockMvc.perform(post("/api/admin/customers/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestText))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field", is("address.postcode")))
                .andExpect(jsonPath("$[0].errorMessage", is("Field length must be between 1 and 255")));
    }

}