package hu.kalmancheysandor.webshop.controller;


import hu.kalmancheysandor.webshop.dto.customer.CustomerCreateRequest;
import hu.kalmancheysandor.webshop.dto.customer.CustomerResponse;
import hu.kalmancheysandor.webshop.dto.customer.CustomerUpdateRequest;
import hu.kalmancheysandor.webshop.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/customers")
@Slf4j
@Tag(name = "Ügyfél adminisztráció")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Mentés",
            description = "Új ügyfél felvétele.")
    public CustomerResponse saveCustomer(@Valid @RequestBody CustomerCreateRequest command) {
        log.info("Http request; Method type:POST; URL:/api/admin/customers/; Body:" + command.toString());
        return customerService.saveCustomer(command);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Módosítás",
            description = "Korábban mentett ügyfél id általi elérése és mezőinek felülírása.")
    public CustomerResponse updateCustomer(@Parameter(description = "Ügyfél id", example = "2")
                                           @PathVariable("id") int customerId,
                                           @Valid @RequestBody CustomerUpdateRequest command) {
        log.info("Http request; Method type:PUT; URL:/api/admin/customers/" + customerId + "/; Body:" + command.toString());
        return customerService.updateCustomer(customerId, command);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listázás",
            description = "Kilistázza az összes mentett ügyfélt.")
    public List<CustomerResponse> listAllCustomer() {
        log.info("Http request; Method type:GET; URL:/api/admin/customers/");
        return customerService.listAllCustomer();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lekérés",
            description = "Korábban mentett ügyfél id általi lekérése.")
    public CustomerResponse findCustomerById(@Parameter(description = "Ügyfél id", example = "2")
                                             @PathVariable("id") int customerId) {
        log.info("Http request; Method type:GET; URL:/api/admin/customers/" + customerId + "/");
        return customerService.findCustomerById(customerId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Törlés",
            description = "Korábban mentett ügyfél id általi törlése.")
    public void deleteCustomerById(@Parameter(description = "Ügyfél id", example = "2")
                                   @PathVariable("id") int customerId) {
        log.info("Http request; Method type:DELETE; URL:/api/admin/customers/" + customerId + "/");
        customerService.deleteCustomerById(customerId);
    }
}
