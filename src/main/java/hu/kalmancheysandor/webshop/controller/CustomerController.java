package hu.kalmancheysandor.webshop.controller;


import hu.kalmancheysandor.webshop.dto.CompanyInfo;
import hu.kalmancheysandor.webshop.dto.CustomerCreateCommand;
import hu.kalmancheysandor.webshop.dto.CustomerInfo;
import hu.kalmancheysandor.webshop.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/customer")
@Slf4j
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerInfo saveCompany(@Valid @RequestBody CustomerCreateCommand command) {
        return customerService.saveCustomer(command);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerInfo> listAllCustomer() {
        List<CustomerInfo> customers = customerService.listAllCustomer();
        return customers;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerInfo findCustomerById(@PathVariable("id") int customerId) {
        return customerService.findCustomerById(customerId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerInfo deleteCustomerById(@PathVariable("id") int customerId){
        CustomerInfo deletedCustomer = customerService.deleteCustomerById(customerId);
        return deletedCustomer;
    }
}
