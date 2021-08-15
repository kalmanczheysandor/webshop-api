package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.customer.Customer;
import hu.kalmancheysandor.webshop.domain.order.Order;
import hu.kalmancheysandor.webshop.domain.order.OrderItem;
import hu.kalmancheysandor.webshop.domain.product.Product;
import hu.kalmancheysandor.webshop.dto.order.OrderCreateRequest;
import hu.kalmancheysandor.webshop.dto.order.OrderResponse;
import hu.kalmancheysandor.webshop.dto.order.OrderUpdateRequest;
import hu.kalmancheysandor.webshop.dto.product.ProductResponse;
import hu.kalmancheysandor.webshop.respository.CustomerRepository;
import hu.kalmancheysandor.webshop.respository.OrderItemRepository;
import hu.kalmancheysandor.webshop.respository.OrderRepository;
import hu.kalmancheysandor.webshop.respository.ProductRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.service.exception.*;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public OrderResponse saveOrder(OrderCreateRequest command) {

        // Convert parent dto object to entity
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        Order orderToSave = modelMapper.map(command, Order.class);
        orderToSave.setId(null);
        orderToSave.getOrderItems().clear();
        orderToSave.setCustomer(null);

        //Save parent entity
        Order savedOrder = orderRepository.saveOrder(orderToSave);
        try {
            Integer customerId = command.getCustomerId();
            Customer customer = customerRepository.findCustomerById(customerId);
            orderToSave.setCustomer(customer);
        } catch (RecordNotFoundByIdException e) {
            throw new CustomerNotFoundException(e.getId());
        }

        // Convert and save children entities
        int sumTotalNet = 0;
        int sumTotalGross = 0;
        List<Integer> usedProductIdList = new ArrayList<>();
        List<OrderCreateRequest.Item> items = command.getItems();
        for (OrderCreateRequest.Item item : items) {
            OrderItem orderItemToSave = modelMapper.map(item, OrderItem.class);
            orderItemToSave.setId(null);
            orderItemToSave.setOrder(null);
            orderItemToSave.setProduct(null);

            Product product;
            try {
                Integer productId = item.getProductId();

                // Test whether the productId is in use only once
                if (usedProductIdList.contains(productId)) {
                    throw new OrderItemDuplicationException(productId);
                }
                usedProductIdList.add(productId);


                product = productRepository.findProductById(productId);
                orderItemToSave.setProduct(product);
            } catch (RecordNotFoundByIdException e) {
                throw new ProductNotFoundException(e.getId());
            }


            int totalNet = product.getPriceNet();
            totalNet *= orderItemToSave.getQuantity();
            int totalGross = (int) (totalNet + (totalNet * (product.getPriceVat() / 100)));


            orderItemToSave.setTotalNetPrice(totalNet);
            orderItemToSave.setTotalGrossPrice(totalGross);

            sumTotalNet += totalNet;
            sumTotalGross += totalGross;

            // Set relationship references between entities

            orderToSave.addOrderItem(orderItemToSave);
            orderItemToSave.setOrder(orderToSave);


            //Save child entities
            orderRepository.saveOrderItem(orderItemToSave);
        }

        orderToSave.setTotalNetPrice(sumTotalNet);
        orderToSave.setTotalGrossPrice(sumTotalGross);

        return modelMapper.map(savedOrder, OrderResponse.class);
    }


    public OrderResponse updateOrder(int orderId, OrderUpdateRequest command) {
        try {
            Order orderCurrentState = orderRepository.findOrderById(orderId);
            Order orderNewState = modelMapper.map(command, Order.class);
            orderNewState.setId(null);

            modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
            modelMapper.map(orderNewState, orderCurrentState);

            Order modifiedOrder = orderRepository.updateOrder(orderCurrentState);

            return modelMapper.map(modifiedOrder, OrderResponse.class);
        } catch (RecordNotFoundByIdException e) {
            throw new OrderNotFoundException(e.getId());
        }
    }


    public List<OrderResponse> listAllOrder() {
        List<Order> orders = orderRepository.listAllOrder();
        return orders.stream()
                .map(item -> modelMapper.map(item, OrderResponse.class))
                .collect(Collectors.toList());
    }

    public OrderResponse findOrderById(int orderId) {
        try {
            Order order = orderRepository.findOrderById(orderId);
            return modelMapper.map(order, OrderResponse.class);
        } catch (RecordNotFoundByIdException e) {
            throw new OrderNotFoundException(e.getId());
        }
    }

    public void deleteOrderById(int orderId) {
        try {
            // Delete all children entities
            orderRepository.deleteAllOrderItemByOrderId(orderId);

            // Delete the main entity
            orderRepository.deleteOrderById(orderId);
        } catch (RecordNotFoundByIdException e) {
            throw new OrderNotFoundException(e.getId());
        }
    }
}
