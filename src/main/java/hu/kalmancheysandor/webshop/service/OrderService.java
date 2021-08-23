package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.customer.Customer;
import hu.kalmancheysandor.webshop.domain.order.DeliveryStatus;
import hu.kalmancheysandor.webshop.domain.order.Order;
import hu.kalmancheysandor.webshop.domain.order.OrderItem;
import hu.kalmancheysandor.webshop.domain.product.Product;
import hu.kalmancheysandor.webshop.dto.order.*;
import hu.kalmancheysandor.webshop.respository.CustomerRepository;
import hu.kalmancheysandor.webshop.respository.OrderRepository;
import hu.kalmancheysandor.webshop.respository.ProductRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.service.exception.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public OrderResponse saveOrder(OrderCreateRequest command) {

        // Convert parent dto object to entity
        Order orderToSave = modelMapper.map(command, Order.class);
        orderToSave.setId(null);
        orderToSave.getOrderItems().clear();
        orderToSave.setCustomer(null);
        orderToSave.setDeliveryStatus(DeliveryStatus.PURCHASED);

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
        float sumTotalNet = 0;
        float sumTotalGross = 0;
        List<Integer> usedProductIdList = new ArrayList<>();
        List<OrderCreateRequest.CreateRequestItem> items = command.getItems();
        for (OrderCreateRequest.CreateRequestItem item : items) {
            OrderItem orderItemToSave = modelMapper.map(item, OrderItem.class);
            orderItemToSave.setId(null);
            orderItemToSave.setOrder(null);
            orderItemToSave.setProduct(null);

            // Find product and save it to item
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

            // Calculating prices
            float totalNet = product.getPriceNet();
            totalNet *= orderItemToSave.getQuantity();
            float totalGross = (totalNet + (totalNet * (product.getPriceVat() / 100)));
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

        // Save final prices
        orderToSave.setTotalNetPrice(sumTotalNet);
        orderToSave.setTotalGrossPrice(sumTotalGross);

        return modelMapper.map(savedOrder, OrderResponse.class);
    }

    public OrderResponse updateOrder(int orderId, OrderUpdateRequest command) {
        try {
            Order orderCurrentState = orderRepository.findOrderById(orderId);
            orderCurrentState.setDeliveryStatus(command.getDeliveryStatus());
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

    public OrderItemResponse addOrderItemToOrder(int orderId, OrderItemCreateRequest command) {

        // Find order
        Order parentOrder = retrieveOrderAtId(orderId);

        // Find product
        Product product = retrieveProductAtId(command.getProductId());

        // Calculating prices
        float totalNet = product.getPriceNet() * command.getQuantity();
        float totalGross = totalNet + (totalNet * (product.getPriceVat() / 100));

        // Setting up item to save
        OrderItem itemToSave = new OrderItem();
        itemToSave.setTotalNetPrice(totalNet);
        itemToSave.setTotalGrossPrice(totalGross);
        itemToSave.setQuantity(command.getQuantity());
        itemToSave.setOrder(parentOrder);
        itemToSave.setProduct(product);

        // Adding item to order list
        parentOrder.addOrderItem(itemToSave);

        // Save final prices
        parentOrder.setTotalNetPrice(parentOrder.getTotalNetPrice() + itemToSave.getTotalNetPrice());
        parentOrder.setTotalGrossPrice(parentOrder.getTotalGrossPrice() + itemToSave.getTotalGrossPrice());

        // Save and update entities
        orderRepository.saveOrderItem(itemToSave);
        orderRepository.updateOrder(parentOrder);

        return modelMapper.map(itemToSave, OrderItemResponse.class);
    }

    public OrderItemResponse updateOrderItem(int orderId, int orderItemId, OrderItemUpdateRequest command) {

        // Find order
        Order parentOrder = retrieveOrderAtId(orderId);

        // Find order item
        OrderItem orderItem = retrieveOrderItemAtId(orderItemId);

        // OrderItem must be child of the specified order otherwise item not counted as "Found"
        if (!parentOrder.equals(orderItem.getOrder())) {
            throw new OrderItemNotFoundException(orderItemId);
        }

        // Find product
        Product product = retrieveProductAtId(command.getProductId());

        // Calculating prices
        float previousTotalNet = orderItem.getTotalNetPrice();
        float previousTotalGross = orderItem.getTotalGrossPrice();
        float newTotalNet = product.getPriceNet() * command.getQuantity();
        float newTotalGross = newTotalNet + (newTotalNet * (product.getPriceVat() / 100));

        // Setting new values
        orderItem.setTotalNetPrice(newTotalNet);
        orderItem.setTotalGrossPrice(newTotalGross);
        orderItem.setQuantity(command.getQuantity());
        orderItem.setProduct(product);

        // Save final prices
        parentOrder.setTotalNetPrice(parentOrder.getTotalNetPrice() - previousTotalNet + orderItem.getTotalNetPrice());
        parentOrder.setTotalGrossPrice(parentOrder.getTotalGrossPrice() - previousTotalGross + orderItem.getTotalGrossPrice());
        // Update entity
        orderRepository.updateOrderItem(orderItem);
        return modelMapper.map(orderItem, OrderItemResponse.class);
    }

    public List<OrderItemResponse> listAllOrderItemByOrderId(int orderId) {
        try {
            List<OrderItem> orderItems = orderRepository.listAllOrderItemByOrderId(orderId);
            return orderItems.stream()
                    .map(orderItem -> modelMapper.map(orderItem, OrderItemResponse.class))
                    .collect(Collectors.toList());
        } catch (RecordNotFoundByIdException e) {
            throw new OrderNotFoundException(e.getId());
        }
    }

    public OrderItemResponse findOrderItem(int orderId, int orderItemId) {
        // Find order
        Order parentOrder = retrieveOrderAtId(orderId);

        // Find order item
        OrderItem orderItem = retrieveOrderItemAtId(orderItemId);

        // OrderItem must be child of the specified order otherwise item not counted as "Found"
        if (!parentOrder.equals(orderItem.getOrder())) {
            throw new OrderItemNotFoundException(orderItemId);
        }

        return modelMapper.map(orderItem, OrderItemResponse.class);
    }

    public void deleteOrderItem(int orderId, int orderItemId) {
        // Find order
        Order parentOrder = retrieveOrderAtId(orderId);

        // Find order item
        OrderItem orderItem = retrieveOrderItemAtId(orderItemId);

        // OrderItem must be child of the specified order otherwise item not counted as "Found"
        if (!parentOrder.equals(orderItem.getOrder())) {
            throw new OrderItemNotFoundException(orderItemId);
        }

        // At least one item must  be
        if (parentOrder.getOrderItems().size() == 1) {
            throw new OrderMustNotBeEmptyException(orderId);
        }

        parentOrder.getOrderItems().remove(orderItem);
        parentOrder.setTotalNetPrice(parentOrder.getTotalNetPrice() - orderItem.getTotalNetPrice());
        parentOrder.setTotalGrossPrice(parentOrder.getTotalGrossPrice() - orderItem.getTotalGrossPrice());
        orderRepository.deleteOrderItem(orderItem);
    }


    private Order retrieveOrderAtId(int orderId) {
        try {
            return orderRepository.findOrderById(orderId);
        } catch (RecordNotFoundByIdException e) {
            throw new OrderNotFoundException(e.getId());
        }
    }

    private Product retrieveProductAtId(int productId) {
        try {
            return productRepository.findProductById(productId);
        } catch (RecordNotFoundByIdException e) {
            throw new ProductNotFoundException(e.getId());
        }
    }

    private OrderItem retrieveOrderItemAtId(int orderItemId) {
        try {
            return orderRepository.findOrderItemById(orderItemId);
        } catch (RecordNotFoundByIdException e) {
            throw new OrderItemNotFoundException(e.getId());
        }
    }
}
