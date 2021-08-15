package hu.kalmancheysandor.webshop.controller;


import hu.kalmancheysandor.webshop.dto.customer.CustomerCreateRequest;
import hu.kalmancheysandor.webshop.dto.customer.CustomerResponse;
import hu.kalmancheysandor.webshop.dto.customer.CustomerUpdateRequest;
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
    public CustomerResponse saveCustomer(@Valid @RequestBody CustomerCreateRequest command) {
        try {
            return customerService.saveCustomer(command);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse updateCustomer(@PathVariable("id") int customerId, @Valid @RequestBody CustomerUpdateRequest command) {
        return customerService.updateCustomer(customerId,command);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> listAllCustomer() {
        List<CustomerResponse> customers = customerService.listAllCustomer();
        return customers;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse findCustomerById(@PathVariable("id") int customerId) {
        return customerService.findCustomerById(customerId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomerById(@PathVariable("id") int customerId){
        customerService.deleteCustomerById(customerId);
    }


}
