package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.customer.Customer;
import hu.kalmancheysandor.webshop.domain.customer.CustomerAddress;
import hu.kalmancheysandor.webshop.domain.order.DeliveryStatus;
import hu.kalmancheysandor.webshop.domain.order.Order;
import hu.kalmancheysandor.webshop.domain.order.OrderItem;
import hu.kalmancheysandor.webshop.domain.product.Product;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderRepositoryIT {
    CustomerAddress address01;
    Customer customer01;
    Product product01;
    Order order01;
    Order order02;
    OrderItem item01;
    OrderItem item02;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerAddressRepository customerAddressRepository;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void init() {
        // Setting up customer and address entities
        address01 = new CustomerAddress();
        address01.setCountry("Hungary");
        address01.setCity("Budapest");
        address01.setStreet("Dózsa Street 25");
        address01.setPostcode("1125");

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

        // Save customer
        customerRepository.saveCustomer(customer01);
        customerAddressRepository.saveAddress(address01);

        // Setting up product entities
        product01 = new Product();
        product01.setName("Product01");
        product01.setPriceVat(10);
        product01.setPriceNet(100f);
        product01.setDescription("My description");
        product01.setActive(true);

        // Save product
        productRepository.saveProduct(product01);

        // Setting up order item entities
        item01 = new OrderItem();
        item01.setTotalNetPrice(100f);
        item01.setTotalGrossPrice(110f);
        item01.setQuantity(1);
        item01.setProduct(product01);

        item02 = new OrderItem();
        item02.setTotalNetPrice(200f);
        item02.setTotalGrossPrice(220f);
        item02.setQuantity(2);
        item01.setProduct(product01);

        product01.setOrderItems(List.of(item01, item02));

        // Setting up order entities
        order01 = new Order();
        order01.setTotalNetPrice(100f);
        order01.setTotalGrossPrice(110f);
        order01.setCustomer(customer01);
        order01.setOrderItems(List.of(item01));
        order01.setDeliveryStatus(DeliveryStatus.PURCHASED);
        item01.setOrder(order01);

        order02 = new Order();
        order02.setTotalNetPrice(200f);
        order02.setTotalGrossPrice(220f);
        order02.setCustomer(customer01);
        order02.setOrderItems(List.of(item02));
        order02.setDeliveryStatus(DeliveryStatus.PURCHASED);
        item02.setOrder(order02);
    }

    @Test
    @Transactional
    void test_saveOrder_oneOrder() {
        // Initial assertion(s)
        assertThat(orderRepository.listAllOrder()).isEmpty();

        // Operations
        Order savedOrder = orderRepository.saveOrder(order01);

        // Final assertion(s)
        assertEquals(1, savedOrder.getId());
        assertEquals(100f, savedOrder.getTotalNetPrice());
        assertEquals(110f, savedOrder.getTotalGrossPrice());
        assertEquals(customer01, savedOrder.getCustomer());
        assertEquals(DeliveryStatus.PURCHASED, savedOrder.getDeliveryStatus());

        assertThat(orderRepository.listAllOrder())
                .hasSize(1)
                .containsExactly(savedOrder);

    }

    @Test
    @Transactional
    void test_saveOrder_withItem() {
        // Initial assertion(s)
        assertThat(orderRepository.listAllOrder()).isEmpty();
        assertThat(orderRepository.listAllOrderItem()).isEmpty();

        // Operations
        Order savedOrder = orderRepository.saveOrder(order01);

        for (OrderItem itemToSave : order01.getOrderItems()) {
            OrderItem savedItem = orderRepository.saveOrderItem(itemToSave);
        }

        // Final assertion(s)
        assertEquals(1, savedOrder.getId());
        assertEquals(100f, savedOrder.getTotalNetPrice());
        assertEquals(110f, savedOrder.getTotalGrossPrice());
        assertEquals(customer01, savedOrder.getCustomer());
        assertEquals(DeliveryStatus.PURCHASED, savedOrder.getDeliveryStatus());

        assertThat(savedOrder.getOrderItems())
                .containsExactly(item01);

        assertThat(orderRepository.listAllOrder())
                .hasSize(1)
                .containsExactly(savedOrder);

        assertThat(orderRepository.listAllOrderItem())
                .hasSize(1)
                .containsExactly(item01);
    }

    @Test
    @Transactional
    void test_saveOrder_twoOrder() {
        // Initial assertion(s)
        assertThat(orderRepository.listAllOrder()).isEmpty();

        // Operations
        Order savedOrder1 = orderRepository.saveOrder(order01);
        Order savedOrder2 = orderRepository.saveOrder(order02);

        // Final assertion(s)
        assertEquals(1, savedOrder1.getId());
        assertEquals(2, savedOrder2.getId());
        assertThat(orderRepository.listAllOrder())
                .hasSize(2)
                .containsExactly(savedOrder1, savedOrder2);


    }

    @Test
    @Transactional
    void test_findOrderById_foundSuccessfully() {
        // Initial assertion(s)
        assertThat(orderRepository.listAllOrder()).isEmpty();

        // Operations
        Order saveOrder1 = orderRepository.saveOrder(order01);
        Order savedOrder2 = orderRepository.saveOrder(order02);
        Order orderFound = orderRepository.findOrderById(2);

        // Final assertion(s)
        assertEquals(orderFound, savedOrder2);
    }

    @Test
    @Transactional
    void test_findOrderById_notFound() {
        // Initial assertion(s)
        assertThat(orderRepository.listAllOrder()).isEmpty();

        // Operations
        Order savedOrder1 = orderRepository.saveOrder(order01);
        Order savedOrder2 = orderRepository.saveOrder(order02);

        // Final assertion(s)
        assertThrows(RecordNotFoundByIdException.class, () -> orderRepository.findOrderById(5));

    }

    @Test
    @Transactional
    void test_findOrderItemById_foundSuccessfully() {
        // Initial assertion(s)
        assertThat(orderRepository.listAllOrder()).isEmpty();
        assertThat(orderRepository.listAllOrderItem()).isEmpty();

        // Operations
        Order savedOrder1 = orderRepository.saveOrder(order01);
        for (OrderItem itemToSave : savedOrder1.getOrderItems()) {
            orderRepository.saveOrderItem(itemToSave);
        }
        Order savedOrder2 = orderRepository.saveOrder(order02);
        for (OrderItem itemToSave : savedOrder2.getOrderItems()) {
            orderRepository.saveOrderItem(itemToSave);
        }

        OrderItem orderItemFound = orderRepository.findOrderItemById(2);

        // Final assertion(s)
        assertEquals(orderItemFound, item02);
    }


    @Test
    @Transactional
    void test_findOrderItemById_notFound() {
        // Initial assertion(s)
        assertThat(orderRepository.listAllOrder()).isEmpty();
        assertThat(orderRepository.listAllOrderItem()).isEmpty();

        // Operations
        Order savedOrder1 = orderRepository.saveOrder(order01);
        for (OrderItem itemToSave : savedOrder1.getOrderItems()) {
            orderRepository.saveOrderItem(itemToSave);
        }
        Order savedOrder2 = orderRepository.saveOrder(order02);
        for (OrderItem itemToSave : savedOrder2.getOrderItems()) {
            orderRepository.saveOrderItem(itemToSave);
        }

        // Final assertion(s)
        assertThrows(RecordNotFoundByIdException.class, () -> orderRepository.findOrderItemById(66));
    }
}