package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.customer.Customer;
import hu.kalmancheysandor.webshop.domain.customer.CustomerAddress;
import hu.kalmancheysandor.webshop.dto.customer.CustomerCreateRequest;
import hu.kalmancheysandor.webshop.dto.customer.CustomerResponse;
import hu.kalmancheysandor.webshop.dto.customer.CustomerUpdateRequest;
import hu.kalmancheysandor.webshop.respository.CustomerAddressRepository;
import hu.kalmancheysandor.webshop.respository.CustomerRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.respository.exception.RecordStillInUseException;
import hu.kalmancheysandor.webshop.service.exception.CustomerNotFoundException;
import hu.kalmancheysandor.webshop.service.exception.CustomerStillInUseException;
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

    public CustomerResponse saveCustomer(CustomerCreateRequest command) {

        // Convert dto objects to entities
        Customer customerToSave = modelMapper.map(command, Customer.class);
        CustomerAddress addressToSave = modelMapper.map(command.getAddress(), CustomerAddress.class);

        // Set relationship references between entities
        customerToSave.setAddress(addressToSave);
        addressToSave.setCustomer(customerToSave);

        //Persist all entities
        Customer savedCustomer = customerRepository.saveCustomer(customerToSave);
        CustomerAddress savedAddress = customerAddressRepository.saveAddress(addressToSave);

        return modelMapper.map(savedCustomer, CustomerResponse.class);
    }

    public CustomerResponse updateCustomer(int customerId, CustomerUpdateRequest command) {
        try {

            //Find persistent entities which referencing each-other
            Customer customerCurrentState = customerRepository.findCustomerById(customerId);
            CustomerAddress addressCurrentState = customerCurrentState.getAddress();

            // Creating and overwriting objects
            Customer customerNewState = modelMapper.map(command, Customer.class);
            CustomerAddress addressNewState = modelMapper.map(command.getAddress(), CustomerAddress.class);

            // Ignore (by null value) overwriting fields to participate in overwrite act
            customerNewState.setId(null);
            customerNewState.setAddress(null);
            addressNewState.setId(null);
            addressNewState.setCustomer(null);

            // The overwrite-act when all field value copied into the persisted object fields. However 'null' values are ignored to be copied!
            modelMapper.map(customerNewState, customerCurrentState);
            modelMapper.map(addressNewState, addressCurrentState);

            // Providing persistence update
            Customer modifiedCustomer = customerRepository.updateCustomer(customerCurrentState);
            CustomerAddress modifiedAddress = customerAddressRepository.updateAddress(addressCurrentState);

            return modelMapper.map(modifiedCustomer, CustomerResponse.class);
        } catch (RecordNotFoundByIdException e) {
            throw new CustomerNotFoundException(e.getId());
        }
    }

    public List<CustomerResponse> listAllCustomer() {
        List<Customer> customers = customerRepository.listAllCustomer();
        return customers.stream()
                .map(item -> modelMapper.map(item, CustomerResponse.class))
                .collect(Collectors.toList());
    }

    public CustomerResponse findCustomerById(int customerId) {
        try {
            Customer customer = customerRepository.findCustomerById(customerId);
            return modelMapper.map(customer, CustomerResponse.class);
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
        } catch (RecordStillInUseException e) {
            throw new CustomerStillInUseException(e.getId());
        }
    }
}
