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





        // Map and save the nested CustomerAddress entity
        CustomerAddress addressToSave = modelMapper.map(command.getAddress(), CustomerAddress.class);


        customerToSave.setAddress(addressToSave);
        addressToSave.setCustomer(customerToSave);

        Customer savedCustomer = customerRepository.saveCustomer(customerToSave);

        CustomerAddress savedAddress = null;
                //customerAddressRepository.saveAddress(addressToSave);
        System.err.println("-------------[1]------------------");
        System.err.println("SavedCustomer:"+savedCustomer);
        System.err.println("SavedAddress:"+savedAddress);


//


        return modelMapper.map(savedCustomer, CustomerInfo.class);
    }



//    public CustomerInfo saveCustomer(CustomerCreateCommand command) {
//        // Map and save the Customer entity
//        Customer customerToSave = modelMapper.map(command, Customer.class);
//        Customer savedCustomer = customerRepository.saveCustomer(customerToSave);
//
//
//
//
//        // Map and save the nested CustomerAddress entity
//        CustomerAddress addressToSave = modelMapper.map(command.getAddress(), CustomerAddress.class);
//        CustomerAddress savedAddress = customerAddressRepository.saveAddress(addressToSave);
//        System.err.println("-------------[1]------------------");
//        System.err.println("SavedCustomer:"+savedCustomer);
//        System.err.println("SavedAddress:"+savedAddress);
//
//
//        System.err.println("-----------------[2-a------------------------]");
//        if(savedCustomer==savedAddress.getCustomer()) {
//            System.err.println("Yes!!! savedCustomer==savedAddress.getCustomer()");
//        }
//        else {
//            System.err.println("No!!! savedCustomer==savedAddress.getCustomer()");
//        }
//
//        if(savedCustomer.getAddress()==savedAddress) {
//            System.err.println("Yes!!! savedCustomer.getAddress()==savedAddress");
//        }
//        else {
//            System.err.println("No!!! savedCustomer.getAddress()==savedAddress");
//        }
//
//
//
//        // Set object references between objects
//        //savedCustomer.setAddress(savedAddress);
//
////        System.err.println("-------------[2:AtCustomer]------------------");
////        System.err.println("SavedCustomer:"+savedCustomer);
////        System.err.println("SavedAddress:"+savedAddress);
//
//
////        savedAddress.setCustomer(savedCustomer);
////
////        System.err.println("-------------[3:AtAddress]------------------");
////        System.err.println("SavedCustomer:"+savedCustomer);
////        System.err.println("SavedAddress:"+savedAddress);
//
//
//        return modelMapper.map(savedCustomer, CustomerInfo.class);
//    }










    /* Hát ez izgi
    -----------------[VALUES]------------------------]
parent>>>>>>Customer{id=null, identifier='Imre06', password='string', firstname='string', lastname='string', phone='string', email='string', address='YYYYY' , active=true}
parent.child>>>>>>CustomerAddress{id=null, country='ImreLand', city='string', street='string', customer='XXXXXXX' , postcode='string'}
parent.child.parent>>>>>>Customer{id=null, identifier='null', password='null', firstname='null', lastname='null', phone='null', email='null', address='YYYYY' , active=null}
parent.child.parent.child>>>>>>CustomerAddress{id=null, country='ImreLand', city='string', street='string', customer='null' , postcode='string'}
-----------------[0-a------------------------]
No!!! parent==parent.chind.parent
-----------------[0-b------------------------]
NO!!! parent.child==parent.chind.parent.child
     *
     */
//    public CustomerInfo saveCustomer(CustomerCreateCommand command) {
//        // Map and save the Customer entity
//        Customer customerToSave = modelMapper.map(command, Customer.class);
//
//        System.err.println("-----------------[VALUES]------------------------]");
//        System.err.println("parent>>>>>>"+customerToSave);
//        System.err.println("parent.child>>>>>>"+customerToSave.getAddress());
//        System.err.println("parent.child.parent>>>>>>"+customerToSave.getAddress().getCustomer());
//        System.err.println("parent.child.parent.child>>>>>>"+customerToSave.getAddress().getCustomer().getAddress());
//
//        System.err.println("-----------------[0-a------------------------]");
//        if(customerToSave==customerToSave.getAddress().getCustomer()) {
//            System.err.println("Yes!!! parent==parent.chind.parent");
//        }
//        else {
//            System.err.println("No!!! parent==parent.chind.parent");
//        }
//
//
//        System.err.println("-----------------[0-b------------------------]");
//        if(customerToSave.getAddress()==customerToSave.getAddress().getCustomer().getAddress()) {
//            System.err.println("Yes!!! parent.child==parent.chind.parent.child");
//        }
//        else {
//            System.err.println("NO!!! parent.child==parent.chind.parent.child");
//        }
//
//        Customer savedCustomer = customerRepository.saveCustomer(customerToSave);
//
//
//
//
//        // Map and save the nested CustomerAddress entity
//        CustomerAddress savedAddress = customerAddressRepository.saveAddress(savedCustomer.getAddress());
//        System.err.println("-------------[1]------------------");
//        System.err.println("SavedCustomer:"+savedCustomer);
//        System.err.println("SavedAddress:"+savedAddress);
//
//
//
//
//
//        // Set object references between objects
//        //savedCustomer.setAddress(savedAddress);
//
//        System.err.println("-------------[2:AtCustomer]------------------");
//        System.err.println("SavedCustomer:"+savedCustomer);
//        System.err.println("SavedAddress:"+savedAddress);
//
//
////        savedAddress.setCustomer(savedCustomer);
////
////        System.err.println("-------------[3:AtAddress]------------------");
////        System.err.println("SavedCustomer:"+savedCustomer);
////        System.err.println("SavedAddress:"+savedAddress);
//
//
//        return modelMapper.map(savedCustomer, CustomerInfo.class);
//    }

    /*
    Ejnye: a mapper le tudta mappelni a child enitást is de a perzisztencát nem biztosítottam
    ezért a retun nál
    org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing : hu.kalmancheysandor.webshop.domain.Customer.address -> hu.kalmancheysandor.webshop.domain.CustomerAddress; nested exception is java.lang.IllegalStateException: org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing : hu.kalmancheysandor.webshop.domain.Customer.address -> hu.kalmancheysandor.webshop.domain.CustomerAddress
     *
     */
//    public CustomerInfo saveCustomer(CustomerCreateCommand command) {
//        // Map and save the Customer entity
//        Customer customerToSave = modelMapper.map(command, Customer.class);
//        System.err.println("-------------[0]------------------");
//        System.err.println("customerToSave:"+customerToSave);
//
//        System.err.println("WhatClass:"+customerToSave.getAddress().getClass());
//        //customerToSave.setAddress(null);
//        Customer savedCustomer = customerRepository.saveCustomer(customerToSave);
//
//        // Map and save the nested CustomerAddress entity
//        CustomerAddress addressToSave = modelMapper.map(command.getAddress(), CustomerAddress.class);
//        CustomerAddress savedAddress = customerAddressRepository.saveAddress(addressToSave);
//        System.err.println("-------------[1]------------------");
//        System.err.println("SavedCustomer:"+savedCustomer);
//        System.err.println("SavedAddress:"+savedAddress);
//
//
//
//
//
//        // Set object references between objects
//        //savedCustomer.setAddress(savedAddress);
//
//        System.err.println("-------------[2:AtCustomer]------------------");
//        System.err.println("SavedCustomer:"+savedCustomer);
//        System.err.println("SavedAddress:"+savedAddress);
//
//
////        savedAddress.setCustomer(savedCustomer);
////
////        System.err.println("-------------[3:AtAddress]------------------");
////        System.err.println("SavedCustomer:"+savedCustomer);
////        System.err.println("SavedAddress:"+savedAddress);
//
//
//        return modelMapper.map(savedCustomer, CustomerInfo.class);
//    }


//    public CustomerInfo saveCustomer(CustomerCreateCommand command) {
//        // Map and save the Customer entity
//        Customer customerToSave = modelMapper.map(command, Customer.class);
//        customerToSave.setAddress(null);
//        Customer savedCustomer = customerRepository.saveCustomer(customerToSave);
//
//        // Map and save the nested CustomerAddress entity
//        CustomerAddress addressToSave = modelMapper.map(command.getAddress(), CustomerAddress.class);
//        CustomerAddress savedAddress = customerAddressRepository.saveAddress(addressToSave);
//        System.err.println("-------------[1]------------------");
//        System.err.println("SavedCustomer:"+savedCustomer);
//        System.err.println("SavedAddress:"+savedAddress);
//
//
//
//
//
//        // Set object references between objects
//        savedCustomer.setAddress(savedAddress);
//
//        System.err.println("-------------[2:AtCustomer]------------------");
//        System.err.println("SavedCustomer:"+savedCustomer);
//        System.err.println("SavedAddress:"+savedAddress);
//
//
////        savedAddress.setCustomer(savedCustomer);
////
////        System.err.println("-------------[3:AtAddress]------------------");
////        System.err.println("SavedCustomer:"+savedCustomer);
////        System.err.println("SavedAddress:"+savedAddress);
//
//
//        return modelMapper.map(savedCustomer, CustomerInfo.class);
//    }


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
