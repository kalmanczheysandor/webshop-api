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


    public CustomerInfo updateCustomer(int customerId, CustomerUpdateCommand command) {

        Customer modifyingCustomerData = modelMapper.map(command, Customer.class);
        Customer persistedCustomer = customerRepository.findCustomerById(customerId);


        persistedCustomer.setIdentifier(modifyingCustomerData.getIdentifier());
        persistedCustomer.setPassword(modifyingCustomerData.getPassword());
        persistedCustomer.setFirstname(modifyingCustomerData.getFirstname());
        persistedCustomer.setLastname(modifyingCustomerData.getLastname());
        persistedCustomer.setPhone(modifyingCustomerData.getPhone());
        persistedCustomer.setEmail(modifyingCustomerData.getEmail());
        persistedCustomer.setActive(modifyingCustomerData.getActive());
        Customer modifiedCustomer = customerRepository.updateCustomer(persistedCustomer);


        CustomerAddress modifyingAddressData = modelMapper.map(command.getAddress(), CustomerAddress.class);
        CustomerAddress persistedAddress = modifiedCustomer.getAddress();

//        persistedAddress.setCountry(modifyingAddressData.getCountry());
//        persistedAddress.setCity(modifyingAddressData.getCity());
//        persistedAddress.setStreet(modifyingAddressData.getStreet());
//        persistedAddress.setPostcode(modifyingAddressData.getPostcode());
//        customerAddressRepository.updateAddress(persistedAddress);

        modelMapper.map(modifyingAddressData,persistedCustomer);



















//        Customer persistedCustomer = customerRepository.findCustomerById(customerId);
//        Customer modifyData = modelMapper.map(command, Customer.class);        // no id arrives
//        modifyData.setId(persistedCustomer.getId());
//        modifyData.setAddress(persistedCustomer.getAddress());
//        modelMapper.map(modifyData,persistedCustomer);
//        Customer modifiedCustomer = customerRepository.updateCustomer(persistedCustomer);
//
//        //
//
//        CustomerAddress persistedAddress    = modifiedCustomer.getAddress();
//        CustomerAddress modifyAddressData = modelMapper.map(command.getAddress(), CustomerAddress.class);        // no id arrives
//
//        persistedAddress.setCountry(modifyAddressData.getCountry());
//        persistedAddress.setCity(modifyAddressData.getCountry());


//        CustomerAddress persistedAddress    = modifiedCustomer.getAddress();
//        CustomerAddress modifyAddressData = modelMapper.map(command.getAddress(), CustomerAddress.class);        // no id arrives
//        modifyAddressData.setId(persistedAddress.getId());
//        modifyAddressData.setCustomer(modifiedCustomer);
//        modelMapper.map(modifyAddressData,persistedAddress);
//        customerAddressRepository.updateAddress(persistedAddress);


//        Customer persistedCustomer = customerRepository.findCustomerById(customerId);
//        Customer modifyData = modelMapper.map(command, Customer.class);        // no id arrives
//        modifyData.setId(persistedCustomer.getId());
//        modifyData.setAddress(persistedCustomer.getAddress());
//
//        modelMapper.map(modifyData,persistedCustomer);


        return modelMapper.map(modifiedCustomer, CustomerInfo.class);
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
