package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.customer.Customer;
import hu.kalmancheysandor.webshop.domain.customer.CustomerAddress;
import hu.kalmancheysandor.webshop.domain.product.Product;
import hu.kalmancheysandor.webshop.dto.customer.CustomerCreateRequest;
import hu.kalmancheysandor.webshop.dto.customer.CustomerResponse;
import hu.kalmancheysandor.webshop.dto.customer.CustomerUpdateRequest;
import hu.kalmancheysandor.webshop.dto.product.ProductResponse;
import hu.kalmancheysandor.webshop.respository.CustomerAddressRepository;
import hu.kalmancheysandor.webshop.respository.CustomerRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.respository.exception.RecordStillInUseException;
import hu.kalmancheysandor.webshop.service.exception.CustomerNotFoundException;
import hu.kalmancheysandor.webshop.service.exception.CustomerStillInUseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @Mock
    CustomerAddressRepository addressRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    CustomerService customerService;

    CustomerCreateRequest customerCreateRequest01, customerCreateRequest02;
    CustomerCreateRequest.CreateRequestAddress customerCreateRequestCreateRequestAddress01, customerCreateRequestAddress02;

    CustomerUpdateRequest customerUpdateRequest01;
    CustomerUpdateRequest.UpdateRequestAddress customerUpdateRequestAddress01;

    Customer customerEntity01, customerEntity02;
    Customer customerEntity01Updated;
    CustomerAddress addressEntity01,addressEntity02;
    CustomerAddress addressEntity01Updated;
    
    CustomerResponse customerResponse01, customerResponse02;
    CustomerResponse customerResponse01Updated;
    
    CustomerResponse.ResponseAddress customerResponseAddress01,customerResponseAddress02;
    CustomerResponse.ResponseAddress customerUpdateResponseAddress01;

    @BeforeEach
    void init() {
        customerCreateRequestCreateRequestAddress01 = new CustomerCreateRequest.CreateRequestAddress();
        customerCreateRequestCreateRequestAddress01.setCountry("United Kingdom");
        customerCreateRequestCreateRequestAddress01.setCity("Manchester");
        customerCreateRequestCreateRequestAddress01.setStreet("Joe street 28");
        customerCreateRequestCreateRequestAddress01.setPostcode("MA45 25LY");

        customerCreateRequestAddress02  = new CustomerCreateRequest.CreateRequestAddress();
        customerCreateRequestAddress02.setCountry("Sweden");
        customerCreateRequestAddress02.setCity("Oslo");
        customerCreateRequestAddress02.setStreet("Mary street 23");
        customerCreateRequestAddress02.setPostcode("LU-28-GH155");





        // Generation create requests
        customerCreateRequest01 = new CustomerCreateRequest();
        customerCreateRequest01.setIdentifier("User01");
        customerCreateRequest01.setPassword("Password01");
        customerCreateRequest01.setFirstname("Firstname01");
        customerCreateRequest01.setLastname("Lastname01");
        customerCreateRequest01.setEmail("myemail01@mail.com");
        customerCreateRequest01.setPhone("00361231212");
        customerCreateRequest01.setAddress(customerCreateRequestCreateRequestAddress01);
        customerCreateRequest01.setActive(false);

        customerCreateRequest02 = new CustomerCreateRequest();
        customerCreateRequest02.setIdentifier("User02");
        customerCreateRequest02.setPassword("Password02");
        customerCreateRequest02.setFirstname("Firstname02");
        customerCreateRequest02.setLastname("Lastname02");
        customerCreateRequest02.setEmail("myemail02@mail.com");
        customerCreateRequest02.setPhone("0044678892");
        customerCreateRequest02.setAddress(customerCreateRequestAddress02);
        customerCreateRequest02.setActive(true);

        // Generation update requests

        customerUpdateRequestAddress01  = new CustomerUpdateRequest.UpdateRequestAddress();
        customerUpdateRequestAddress01.setCountry("United Kingdom");
        customerUpdateRequestAddress01.setCity("Manchester");
        customerUpdateRequestAddress01.setStreet("Joe street 28");
        customerUpdateRequestAddress01.setPostcode("MA45 25LY");

        customerUpdateRequest01 = new CustomerUpdateRequest();
        customerUpdateRequest01.setIdentifier("User01b");
        customerUpdateRequest01.setPassword("Password01b");
        customerUpdateRequest01.setFirstname("Firstname01b");
        customerUpdateRequest01.setLastname("Lastname01b");
        customerUpdateRequest01.setEmail("myemail01@mail.com");
        customerUpdateRequest01.setPhone("00361231212");
        customerUpdateRequest01.setAddress(customerUpdateRequestAddress01);
        customerUpdateRequest01.setActive(false);



        // Generation of entities
        addressEntity01  = new CustomerAddress();
        addressEntity01.setCountry("United Kingdom");
        addressEntity01.setCity("Manchester");
        addressEntity01.setStreet("Joe street 28");
        addressEntity01.setPostcode("MA45 25LY");

        addressEntity02  = new CustomerAddress();
        addressEntity02.setCountry("Sweden");
        addressEntity02.setCity("Oslo");
        addressEntity02.setStreet("Mary street 23");
        addressEntity02.setPostcode("LU-28-GH155");

        addressEntity01Updated  = new CustomerAddress();
        addressEntity01Updated.setCountry("United Kingdom");
        addressEntity01Updated.setCity("Manchester");
        addressEntity01Updated.setStreet("Joe street 28");
        addressEntity01Updated.setPostcode("MA45 25LY");
        
        customerEntity01 = new Customer();
        customerEntity01.setId(1);
        customerEntity01.setIdentifier("User01");
        customerEntity01.setPassword("Password01");
        customerEntity01.setFirstname("Firstname01");
        customerEntity01.setLastname("Lastname01");
        customerEntity01.setEmail("myemail01@mail.com");
        customerEntity01.setPhone("00361231212");
        customerEntity01.setActive(false);
        customerEntity01.setAddress(addressEntity01);

        customerEntity02 = new Customer();
        customerEntity02.setId(2);
        customerEntity02.setIdentifier("User02");
        customerEntity02.setPassword("Password02");
        customerEntity02.setFirstname("Firstname02");
        customerEntity02.setLastname("Lastname02");
        customerEntity02.setEmail("myemail02@mail.com");
        customerEntity02.setPhone("0044678892");
        customerEntity02.setActive(false);
        customerEntity02.setAddress(addressEntity02);

        customerEntity01Updated = new Customer();
        customerEntity01Updated.setIdentifier("User01b");
        customerEntity01Updated.setPassword("Password01b");
        customerEntity01Updated.setFirstname("Firstname01b");
        customerEntity01Updated.setLastname("Lastname01b");
        customerEntity01Updated.setEmail("myemail01@mail.com");
        customerEntity01Updated.setPhone("00361231212");
        customerEntity01Updated.setActive(false);
        customerEntity01Updated.setAddress(addressEntity01Updated);



        addressEntity01.setCustomer(customerEntity01);
        addressEntity02.setCustomer(customerEntity02);
        addressEntity01Updated.setCustomer(customerEntity01Updated);












        // Generation of responses
        customerResponseAddress01  = new CustomerResponse.ResponseAddress();
        customerResponseAddress01.setId(1);
        customerResponseAddress01.setCountry("United Kingdom");
        customerResponseAddress01.setCity("Manchester");
        customerResponseAddress01.setStreet("Joe street 28");
        customerResponseAddress01.setPostcode("MA45 25LY");

        customerResponseAddress02  = new CustomerResponse.ResponseAddress();
        customerResponseAddress02.setId(2);
        customerResponseAddress02.setCountry("Sweden");
        customerResponseAddress02.setCity("Oslo");
        customerResponseAddress02.setStreet("Mary street 23");
        customerResponseAddress02.setPostcode("LU-28-GH155");

        customerUpdateResponseAddress01  = new CustomerResponse.ResponseAddress();
        customerUpdateResponseAddress01.setId(1);
        customerUpdateResponseAddress01.setCountry("United Kingdom");
        customerUpdateResponseAddress01.setCity("Manchester");
        customerUpdateResponseAddress01.setStreet("Joe street 28");
        customerUpdateResponseAddress01.setPostcode("MA45 25LY");
        
        
        
        
        
        
        
        
        
        
        
        
        customerResponse01 = new CustomerResponse();
        customerResponse01.setId(1);
        customerResponse01.setIdentifier("User01");
        customerResponse01.setPassword("Password01");
        customerResponse01.setFirstname("Firstname01");
        customerResponse01.setLastname("Lastname01");
        customerResponse01.setEmail("myemail01@mail.com");
        customerResponse01.setPhone("00361231212");
        customerResponse01.setActive(false);
        customerResponse01.setAddress(customerResponseAddress01);

        customerResponse02 = new CustomerResponse();
        customerResponse02.setId(2);
        customerResponse02.setIdentifier("User02");
        customerResponse02.setPassword("Password02");
        customerResponse02.setFirstname("Firstname02");
        customerResponse02.setLastname("Lastname02");
        customerResponse02.setEmail("myemail02@mail.com");
        customerResponse02.setPhone("0044678892");
        customerResponse02.setActive(false);
        customerResponse02.setAddress(customerResponseAddress02);

        customerResponse01Updated = new CustomerResponse();
        customerResponse01Updated.setIdentifier("User01b");
        customerResponse01Updated.setPassword("Password01b");
        customerResponse01Updated.setFirstname("Firstname01b");
        customerResponse01Updated.setLastname("Lastname01b");
        customerResponse01Updated.setEmail("myemail01@mail.com");
        customerResponse01Updated.setPhone("00361231212");
        customerResponse01Updated.setAddress(customerUpdateResponseAddress01);
        customerResponse01Updated.setActive(false);


    }

    @Test
    void test_saveCustomer() {
        // Mocking of repository method(s)
        when(customerRepository.saveCustomer(customerEntity01)).thenReturn(customerEntity01);
        when(addressRepository.saveAddress(addressEntity01)).thenReturn(addressEntity01);

        // Mocking from request to entity
        when(modelMapper.map(customerCreateRequest01, Customer.class)).thenReturn(customerEntity01);
        when(modelMapper.map(customerCreateRequestCreateRequestAddress01, CustomerAddress.class)).thenReturn(addressEntity01);

        // Mocking from entity to response
        when(modelMapper.map(customerEntity01, CustomerResponse.class)).thenReturn(customerResponse01);
        //when(modelMapper.map(addressEntity01, CustomerResponse.Address.class)).thenReturn(customerResponseAddress01);

        // Statement(s)
        assertThat(customerService.saveCustomer(customerCreateRequest01))
                .isEqualTo(customerResponse01);
        verify(customerRepository, times(1)).saveCustomer(customerEntity01);
        verifyNoMoreInteractions(customerRepository);
    }


    @Test
    void test_updateCustomer_whenCustomerIsFound() {

        // Mocking of repository method(s)
        when(customerRepository.findCustomerById(1)).thenReturn(customerEntity01);
        when(customerRepository.updateCustomer(customerEntity01)).thenReturn(customerEntity01Updated);
        when(addressRepository.updateAddress(addressEntity01)).thenReturn(addressEntity01Updated);

        // Mocking from request to entity
        when(modelMapper.map(customerUpdateRequest01, Customer.class)).thenReturn(customerEntity02);
        when(modelMapper.map(customerUpdateRequest01.getAddress(), CustomerAddress.class)).thenReturn(addressEntity02);
        doNothing().when(modelMapper).map(customerEntity02,customerEntity01);
        doNothing().when(modelMapper).map(addressEntity02,addressEntity01);

        // Mocking from entity to response
        when(modelMapper.map(customerEntity01Updated, CustomerResponse.class)).thenReturn(customerResponse01Updated);

        //Operation(s)
        CustomerResponse response = customerService.updateCustomer(1,customerUpdateRequest01);

        // Statement(s)
        assertEquals(customerResponse01Updated,response);
        verify(customerRepository, times(1)).findCustomerById(1);
        verify(customerRepository, times(1)).updateCustomer(customerEntity01);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void test_updateCustomer_whenCustomerIsNotFound() {

        // Mocking of repository method(s)
        when(customerRepository.findCustomerById(1)).thenThrow(new RecordNotFoundByIdException(1));

        // Mocking from entity to response
        //when(modelMapper.map(customerEntity01, CustomerResponse.class)).thenReturn(customerResponse01);

        // Statement(s)
        assertThrows(CustomerNotFoundException.class, () -> customerService.updateCustomer(1, customerUpdateRequest01));
        verify(customerRepository, times(1)).findCustomerById(1);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void test_listAllCustomer() {
        // Mocking of repository method(s)
        when(customerRepository.listAllCustomer()).thenReturn(List.of(customerEntity01, customerEntity02));

        // Mocking entity to response
        when(modelMapper.map(customerEntity01, CustomerResponse.class)).thenReturn(customerResponse01);
        when(modelMapper.map(customerEntity02, CustomerResponse.class)).thenReturn(customerResponse02);

        // Statement(s)
        assertThat(customerService.listAllCustomer())
                .hasSize(2)
                .containsExactly(customerResponse01, customerResponse02);
        verify(customerRepository, times(1)).listAllCustomer();
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void test_findCustomerById_whenCustomerIsFound() {
        // Mocking of repository method(s)
        when(customerRepository.findCustomerById(1)).thenReturn(customerEntity01);

        // Mocking from entity to response
        when(modelMapper.map(customerEntity01, CustomerResponse.class)).thenReturn(customerResponse01);

        // Statement(s)
        assertThat(customerService.findCustomerById(1))
                .isEqualTo(customerResponse01);
        verify(customerRepository, times(1)).findCustomerById(1);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void test_findCustomerById_whenCustomerNotFound() {
        // Mocking of repository method(s)
        when(customerRepository.findCustomerById(1)).thenThrow(new RecordNotFoundByIdException(1));

        // Mocking from entity to response
        //when(modelMapper.map(customerEntity01, CustomerResponse.class)).thenReturn(customerResponse01);

        // Statement(s)
        assertThrows(CustomerNotFoundException.class, () -> customerService.findCustomerById(1));

        verify(customerRepository, times(1)).findCustomerById(1);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void test_deleteCustomerById_whenCustomerNotFound() {

        // Mocking of repository method(s)
        doThrow(new RecordNotFoundByIdException(1))
                .when(customerRepository)
                .deleteCustomerById(1);

        // Statement(s)
        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomerById(1));
        verify(customerRepository, times(1)).deleteCustomerById(1);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void test_deleteCustomerById_whenCustomerStillInUse() {

        // Mocking of repository method(s)
        doThrow(new RecordStillInUseException(1))
                .when(customerRepository)
                .deleteCustomerById(1);

        // Statement(s)
        assertThrows(CustomerStillInUseException.class, () -> customerService.deleteCustomerById(1));
        verify(customerRepository, times(1)).deleteCustomerById(1);
        verifyNoMoreInteractions(customerRepository);
    }
}