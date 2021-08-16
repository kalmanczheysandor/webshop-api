package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.customer.Customer;
import hu.kalmancheysandor.webshop.domain.customer.CustomerAddress;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerRepositoryTest {

    Customer customer01;
    Customer customer02;
    CustomerAddress address01;
    CustomerAddress address02;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerAddressRepository addressRepository;

    @BeforeEach
    void init() {
        address01   = new CustomerAddress();
        address01.setCountry("Hungary");
        address01.setCity("Budapest");
        address01.setStreet("Dózsa Street 25");
        address01.setPostcode("1125");

        address02   = new CustomerAddress();
        address02.setCountry("England");
        address02.setCity("Middleton");
        address02.setStreet("Castlerigg Drive 28");
        address02.setPostcode("M24 4LW");

        customer01 = new Customer();
        customer01.setIdentifier("PaulJackson");
        customer01.setPassword("123456");
        customer01.setEmail("paul_jackson@mymail.com");
        customer01.setFirstname("Paul");
        customer01.setLastname("Jackson");
        customer01.setPhone("0044545656565");
        customer01.setActive(true);
        customer01.setAddress(address01);
        address01.setCustomer(customer01);

        customer02 = new Customer();
        customer02.setIdentifier("PeterParker");
        customer02.setPassword("112233");
        customer02.setEmail("peter_parker@mymail.com");
        customer02.setFirstname("Peter");
        customer02.setLastname("Parker");
        customer02.setPhone("0036777777");
        customer02.setActive(false);
        customer02.setAddress(address02);
        address02.setCustomer(customer02);
    }

    @Test
    @Transactional
    void testSaveCustomer_oneItem() {
        // Initial assertion(s)
        assertThat(customerRepository.listAllCustomer()).isEmpty();
        assertThat(addressRepository.listAllAddress()).isEmpty();

        // Operations
        Customer savedCustomer = customerRepository.saveCustomer(customer01);
        CustomerAddress savedAddress = addressRepository.saveAddress(customer01.getAddress());

        // Final assertion(s)
        assertEquals(customer01.getId(), savedCustomer.getId());
        assertEquals(customer01.getIdentifier(), savedCustomer.getIdentifier());
        assertEquals(customer01.getPassword(), savedCustomer.getPassword());
        assertEquals(customer01.getEmail(), savedCustomer.getEmail());
        assertEquals(customer01.getFirstname(), savedCustomer.getFirstname());
        assertEquals(customer01.getLastname(), savedCustomer.getLastname());
        assertEquals(customer01.getPhone(), savedCustomer.getPhone());
        assertEquals(customer01.getActive(), savedCustomer.getActive());

        assertEquals(address01.getCountry(), savedCustomer.getAddress().getCountry());
        assertEquals(address01.getCity(), savedCustomer.getAddress().getCity());
        assertEquals(address01.getStreet(), savedCustomer.getAddress().getStreet());
        assertEquals(address01.getPostcode(), savedCustomer.getAddress().getPostcode());

        assertThat(customerRepository.listAllCustomer())
                .hasSize(1)
                .containsExactly(savedCustomer);

        assertThat(addressRepository.listAllAddress())
                .hasSize(1)
                .containsExactly(savedAddress);
    }

    @Test
    @Transactional
    void testSaveCustomer_twoItem() {
        // Initial assertion(s)
        assertThat(customerRepository.listAllCustomer()).isEmpty();
        assertThat(addressRepository.listAllAddress()).isEmpty();

        // Operations
        Customer savedCustomer1 = customerRepository.saveCustomer(customer01);
        Customer savedCustomer2 = customerRepository.saveCustomer(customer02);
        CustomerAddress savedAddress1 = addressRepository.saveAddress(customer01.getAddress());
        CustomerAddress savedAddress2 = addressRepository.saveAddress(customer02.getAddress());

        // Final assertion(s)
        assertEquals(1, savedCustomer1.getId());
        assertEquals(2, savedCustomer2.getId());
        assertThat(customerRepository.listAllCustomer())
                .hasSize(2)
                .containsExactly(savedCustomer1, savedCustomer2);

        assertEquals(1, savedAddress1.getId());
        assertEquals(2, savedAddress2.getId());
        assertEquals(1, savedAddress1.getCustomer().getId());
        assertEquals(2, savedAddress2.getCustomer().getId());
        assertThat(addressRepository.listAllAddress())
                .hasSize(2)
                .containsExactly(savedAddress1, savedAddress2);

    }

    @Test
    @Transactional
    void testFindCustomerById_foundSuccessfully() {
        // Initial assertion(s)
        assertThat(customerRepository.listAllCustomer()).isEmpty();
        assertThat(addressRepository.listAllAddress()).isEmpty();

        // Operations
        Customer saveCustomer1 = customerRepository.saveCustomer(customer01);
        Customer savedCustomer2 = customerRepository.saveCustomer(customer02);
        CustomerAddress savedAddress1 = addressRepository.saveAddress(customer01.getAddress());
        CustomerAddress savedAddress2 = addressRepository.saveAddress(customer02.getAddress());
        Customer customerFound = customerRepository.findCustomerById(2);

        // Final assertion(s)
        assertEquals(customerFound, savedCustomer2);
    }


    @Test
    @Transactional
    void testFindCustomerById_notFound() {
        // Initial assertion(s)
        assertThat(customerRepository.listAllCustomer()).isEmpty();
        assertThat(addressRepository.listAllAddress()).isEmpty();

        // Operations
        Customer savedCustomer1 = customerRepository.saveCustomer(customer01);
        Customer savedCustomer2 = customerRepository.saveCustomer(customer02);
        CustomerAddress savedAddress1 = addressRepository.saveAddress(customer01.getAddress());
        CustomerAddress savedAddress2 = addressRepository.saveAddress(customer02.getAddress());

        // Final assertion(s)
        assertThrows(RecordNotFoundByIdException.class, () -> customerRepository.findCustomerById(5));

    }

    @Test
    @Transactional
    void testFindCustomerAddressById_foundSuccessfully() {
        // Initial assertion(s)
        assertThat(customerRepository.listAllCustomer()).isEmpty();
        assertThat(addressRepository.listAllAddress()).isEmpty();

        // Operations
        Customer saveCustomer1 = customerRepository.saveCustomer(customer01);
        Customer savedCustomer2 = customerRepository.saveCustomer(customer02);
        CustomerAddress savedAddress1 = addressRepository.saveAddress(customer01.getAddress());
        CustomerAddress savedAddress2 = addressRepository.saveAddress(customer02.getAddress());
        CustomerAddress addressFound = addressRepository.findAddressById(2);

        // Final assertion(s)
        assertEquals(addressFound, address02);
    }

    @Test
    @Transactional
    void testFindCustomerAddressById_notFound() {
        // Initial assertion(s)
        assertThat(customerRepository.listAllCustomer()).isEmpty();
        assertThat(addressRepository.listAllAddress()).isEmpty();

        // Operations
        Customer savedCustomer1 = customerRepository.saveCustomer(customer01);
        Customer savedCustomer2 = customerRepository.saveCustomer(customer02);
        CustomerAddress savedAddress1 = addressRepository.saveAddress(customer01.getAddress());
        CustomerAddress savedAddress2 = addressRepository.saveAddress(customer02.getAddress());

        // Final assertion(s)
        assertThrows(RecordNotFoundByIdException.class, () -> addressRepository.findAddressById(55));

    }

    @Test
    @Transactional
    void testUpdateCustomer_updatedSuccessfully() {
        // Initial assertion(s)
        assertThat(customerRepository.listAllCustomer()).isEmpty();
        assertThat(addressRepository.listAllAddress()).isEmpty();

        // Operations
        customer02.setActive(true);
        Customer savedCustomer1 = customerRepository.saveCustomer(customer01);
        Customer savedCustomer2 = customerRepository.saveCustomer(customer02);
        CustomerAddress savedAddress1 = addressRepository.saveAddress(customer01.getAddress());
        CustomerAddress savedAddress2 = addressRepository.saveAddress(customer02.getAddress());

        assertEquals(true, savedCustomer2.getActive());
        assertThat(customerRepository.listAllCustomer())
                .hasSize(2)
                .containsExactly(savedCustomer1, savedCustomer2);

        savedCustomer2.setActive(false);
        customerRepository.updateCustomer(savedCustomer2);

        // Final assertion(s)
        assertEquals(false, savedCustomer2.getActive());
        assertThat(customerRepository.listAllCustomer())
                .hasSize(2)
                .containsExactly(savedCustomer1, savedCustomer2);
    }

    @Test
    @Transactional
    void testUpdateCustomerAddress_updatedSuccessfully() {
        // Initial assertion(s)
        assertThat(customerRepository.listAllCustomer()).isEmpty();
        assertThat(addressRepository.listAllAddress()).isEmpty();

        // Operations
        customer02.getAddress().setCountry("Berlin");
        Customer savedCustomer1 = customerRepository.saveCustomer(customer01);
        Customer savedCustomer2 = customerRepository.saveCustomer(customer02);
        CustomerAddress savedAddress1 = addressRepository.saveAddress(customer01.getAddress());
        CustomerAddress savedAddress2 = addressRepository.saveAddress(customer02.getAddress());

        assertEquals("Berlin", savedCustomer2.getAddress().getCountry());
        assertThat(addressRepository.listAllAddress())
                .hasSize(2)
                .containsExactly(savedAddress1, savedAddress2);

        savedAddress2.setCountry("Germany");
        addressRepository.updateAddress(savedAddress2);

        // Final assertion(s)
        assertEquals("Germany", savedCustomer2.getAddress().getCountry());
        assertThat(addressRepository.listAllAddress())
                .hasSize(2)
                .containsExactly(savedAddress1, savedAddress2);
    }




    @Test
    @Transactional
    void testDeleteCustomer_deletedSuccessfully() {
        // Initial assertion(s)
        assertThat(customerRepository.listAllCustomer()).isEmpty();
        assertThat(addressRepository.listAllAddress()).isEmpty();

        // Operations
        Customer savedCustomer1 = customerRepository.saveCustomer(customer01);
        Customer savedCustomer2 = customerRepository.saveCustomer(customer02);
        CustomerAddress savedAddress1 = addressRepository.saveAddress(customer01.getAddress());
        CustomerAddress savedAddress2 = addressRepository.saveAddress(customer02.getAddress());

        assertThat(customerRepository.listAllCustomer())
                .hasSize(2)
                .containsExactly(savedCustomer1, savedCustomer2);

        assertThat(addressRepository.listAllAddress())
                .hasSize(2)
                .containsExactly(savedAddress1, savedAddress2);

        // Operations
        addressRepository.deleteAllAddressByCustomerId(2);
        customerRepository.deleteCustomerById(2);

        // Final assertion(s)
        assertThat(customerRepository.listAllCustomer())
                .hasSize(1)
                .containsExactly(savedCustomer1);

        assertThat(addressRepository.listAllAddress())
                .hasSize(1)
                .containsExactly(savedAddress1);
    }

    @Test
    @Transactional
    void testDeleteCustomer_deletedUnsuccessfully_idNotExist() {
        // Initial assertion(s)
        assertThat(customerRepository.listAllCustomer()).isEmpty();
        assertThat(addressRepository.listAllAddress()).isEmpty();

        // Operations
        Customer savedCustomer1 = customerRepository.saveCustomer(customer01);
        Customer savedCustomer2 = customerRepository.saveCustomer(customer02);
        CustomerAddress savedAddress1 = addressRepository.saveAddress(customer01.getAddress());
        CustomerAddress savedAddress2 = addressRepository.saveAddress(customer02.getAddress());

        // Final assertion(s)
        assertThat(customerRepository.listAllCustomer())
                .hasSize(2)
                .containsExactly(savedCustomer1, savedCustomer2);
        assertThat(addressRepository.listAllAddress())
                .hasSize(2)
                .containsExactly(savedAddress1, savedAddress2);
        assertThrows(RecordNotFoundByIdException.class, () -> customerRepository.deleteCustomerById(20));

    }

    @Test
    @Transactional
    void testDeleteCustomerAddress_deletedUnsuccessfully_idNotExist() {
        // Initial assertion(s)
        assertThat(customerRepository.listAllCustomer()).isEmpty();
        assertThat(addressRepository.listAllAddress()).isEmpty();

        // Operations
        Customer savedCustomer1 = customerRepository.saveCustomer(customer01);
        Customer savedCustomer2 = customerRepository.saveCustomer(customer02);
        CustomerAddress savedAddress1 = addressRepository.saveAddress(customer01.getAddress());
        CustomerAddress savedAddress2 = addressRepository.saveAddress(customer02.getAddress());

        // Final assertion(s)
        assertThat(customerRepository.listAllCustomer())
                .hasSize(2)
                .containsExactly(savedCustomer1, savedCustomer2);
        assertThat(addressRepository.listAllAddress())
                .hasSize(2)
                .containsExactly(savedAddress1, savedAddress2);
        assertThrows(RecordNotFoundByIdException.class, () -> addressRepository.deleteAddressById(45));

    }
}