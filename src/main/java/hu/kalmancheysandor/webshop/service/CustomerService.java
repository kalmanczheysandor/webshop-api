package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.Customer;
import hu.kalmancheysandor.webshop.domain.CustomerAddress;
import hu.kalmancheysandor.webshop.dto.CustomerCreateCommand;
import hu.kalmancheysandor.webshop.dto.CustomerInfo;
import hu.kalmancheysandor.webshop.respository.CustomerAddressRepository;
import hu.kalmancheysandor.webshop.respository.CustomerRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.service.exception.CustomerNotFoundException;
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
    private CustomerAddressRepository customerAddressRepository;


    @Autowired
    private ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerAddressRepository customerAddressRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.customerAddressRepository = customerAddressRepository;
        this.modelMapper = modelMapper;
    }

    public CustomerInfo saveCustomer(CustomerCreateCommand command) {
        // Map and save the Customer entity
        Customer customerToSave = modelMapper.map(command, Customer.class);
        customerToSave.setAddress(null);
        Customer savedCustomer = customerRepository.saveCustomer(customerToSave);

        // Map and save the nested CustomerAddress entity
        CustomerAddress addressToSave = modelMapper.map(command.getAddress(), CustomerAddress.class);
        CustomerAddress savedAddress = customerAddressRepository.saveAddress(addressToSave);

        // Set object references between objects
        savedAddress.setCustomer(savedCustomer);
        savedCustomer.setAddress(savedAddress);
        return modelMapper.map(savedCustomer, CustomerInfo.class);
    }


    public List<CustomerInfo> listAllCustomer() {
        List<Customer> customers = customerRepository.listAllCustomer();
        return customers.stream()
                .map(item -> modelMapper.map(item, CustomerInfo.class))
                .collect(Collectors.toList());
    }


    public CustomerInfo findCustomerById(int customerId) {
        try {
            Customer customer = customerRepository.findCustomerById(customerId);
            return modelMapper.map(customer, CustomerInfo.class);
        } catch (RecordNotFoundByIdException e) {
            throw new CustomerNotFoundException(e.getId());
        }
    }

    public void deleteCustomerById(int customerId) {
        try {
            customerAddressRepository.deleteAllAddressByCustomerId(customerId);
            customerRepository.deleteCustomerById(customerId);
        } catch (RecordNotFoundByIdException e) {
            throw new CustomerNotFoundException(e.getId());
        }
    }
}
