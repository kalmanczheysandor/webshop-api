package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.Customer;
import hu.kalmancheysandor.webshop.domain.CustomerAddress;
import hu.kalmancheysandor.webshop.dto.CustomerCreateCommand;
import hu.kalmancheysandor.webshop.dto.CustomerInfo;
import hu.kalmancheysandor.webshop.dto.CustomerUpdateCommand;
import hu.kalmancheysandor.webshop.respository.CustomerAddressRepository;
import hu.kalmancheysandor.webshop.respository.CustomerRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.service.exception.CustomerNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.OneToOne;
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

        // Convert dto objects to entities
        Customer customerToSave = modelMapper.map(command, Customer.class);
        CustomerAddress addressToSave = modelMapper.map(command.getAddress(), CustomerAddress.class);

        // Set relationship references between entities
        customerToSave.setAddress(addressToSave);
        addressToSave.setCustomer(customerToSave);

        //Persist all entities
        Customer savedCustomer = customerRepository.saveCustomer(customerToSave);
        CustomerAddress savedAddress =customerAddressRepository.saveAddress(addressToSave);

        return modelMapper.map(savedCustomer, CustomerInfo.class);
    }

    public CustomerInfo updateCustomer(int customerId, CustomerUpdateCommand command) {
        try {
            // Modifying Customer
            Customer customerModifyingData = modelMapper.map(command, Customer.class);
            Customer customerToUpdate = customerRepository.findCustomerById(customerId);

            customerToUpdate.setIdentifier(customerModifyingData.getIdentifier());
            customerToUpdate.setPassword(customerModifyingData.getPassword());
            customerToUpdate.setFirstname(customerModifyingData.getFirstname());
            customerToUpdate.setLastname(customerModifyingData.getLastname());
            customerToUpdate.setPhone(customerModifyingData.getPhone());
            customerToUpdate.setEmail(customerModifyingData.getEmail());
            customerToUpdate.setActive(customerModifyingData.getActive());
            Customer modifiedCustomer = customerRepository.updateCustomer(customerToUpdate);

            // Modifying CustomerAddress
            CustomerAddress modifyingAddressData = modelMapper.map(command.getAddress(), CustomerAddress.class);
            CustomerAddress persistedAddress = modifiedCustomer.getAddress();

            persistedAddress.setCountry(modifyingAddressData.getCountry());
            persistedAddress.setCity(modifyingAddressData.getCity());
            persistedAddress.setStreet(modifyingAddressData.getStreet());
            persistedAddress.setPostcode(modifyingAddressData.getPostcode());
            customerAddressRepository.updateAddress(persistedAddress);

            return modelMapper.map(modifiedCustomer, CustomerInfo.class);
        } catch (RecordNotFoundByIdException e) {
            throw new CustomerNotFoundException(e.getId());
        }
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
