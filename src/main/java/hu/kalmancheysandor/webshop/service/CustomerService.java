package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.Customer;
import hu.kalmancheysandor.webshop.domain.Product;
import hu.kalmancheysandor.webshop.dto.CustomerCreateCommand;
import hu.kalmancheysandor.webshop.dto.ProductCreateCommand;
import hu.kalmancheysandor.webshop.dto.CustomerInfo;
import hu.kalmancheysandor.webshop.respository.CustomerRepository;
import hu.kalmancheysandor.webshop.respository.ProductRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.service.exception.ProductNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }


    public CustomerInfo saveCustomer(CustomerCreateCommand command) {
        Customer customerToSave = modelMapper.map(command, Customer.class);
        System.out.println("Hello:"+customerToSave.getBillingAddress());

        Customer customerSaved = customerRepository.saveCustomer(customerToSave);
        return modelMapper.map(customerSaved, CustomerInfo.class);
    }


//    public CustomerInfo saveProduct(CustomerCreateCommand command) {
//        Customer customerToSave = modelMapper.map(command, Customer.class);
//        Customer customerSaved = customerRepository.saveCustomer(customerToSave);
//        return modelMapper.map(customerSaved, CustomerInfo.class);
//    }
//
//    public List<CustomerInfo> listAllCustomer() {
//        List<Customer> costumers = customerRepository.listAllCustomer();
//        return costumers.stream()
//                .map(item -> modelMapper.map(item, CustomerInfo.class))
//                .collect(Collectors.toList());
//    }
//
//    public CustomerInfo findProductById(int customerId) {
//        try {
//            Customer customer = customerRepository.findCustomerById(customerId);
//            return modelMapper.map(customer, CustomerInfo.class);
//        } catch (RecordNotFoundByIdException e) {
//            throw new ProductNotFoundException(e.getId());
//        }
//    }
//
//    public CustomerInfo deleteProductById(int customerId) {
//        try {
//            Customer deletedCustomer = customerRepository.deleteCustomerById(customerId);
//            return modelMapper.map(deletedCustomer, CustomerInfo.class);
//        } catch (RecordNotFoundByIdException e) {
//            throw new ProductNotFoundException(e.getId());
//        }
//    }
}
