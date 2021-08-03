package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.Customer;
import hu.kalmancheysandor.webshop.domain.Order;
import hu.kalmancheysandor.webshop.dto.OrderCreateCommand;
import hu.kalmancheysandor.webshop.dto.OrderInfo;
import hu.kalmancheysandor.webshop.respository.OrderRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.service.exception.CustomerNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public OrderInfo saveOrder(OrderCreateCommand command) {
        System.err.println("Hello:"+command);
        try {
            Integer customerId = command.getCustomerId();
            Customer customer = orderRepository.findCustomerById(customerId);

            System.err.println("Bello:"+customer);


            Order orderToSave = modelMapper.map(customer, Order.class);
            orderToSave.setId(null);
            orderToSave.setTotalNetPrice(0);
            orderToSave.setTotalGrossPrice(0);
            System.err.println("Mello:"+orderToSave);

            Order savedOrder = orderRepository.saveOrder(orderToSave);
            return modelMapper.map(savedOrder, OrderInfo.class);
        } catch (RecordNotFoundByIdException e) {
            throw new CustomerNotFoundException(e.getId());
        }
    }
}
