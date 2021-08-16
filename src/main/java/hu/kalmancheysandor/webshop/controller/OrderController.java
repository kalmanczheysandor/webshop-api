package hu.kalmancheysandor.webshop.controller;

import hu.kalmancheysandor.webshop.dto.customer.CustomerResponse;
import hu.kalmancheysandor.webshop.dto.order.*;
import hu.kalmancheysandor.webshop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse saveOrder(@Valid @RequestBody OrderCreateRequest command) {
        return orderService.saveOrder(command);
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse updateOrder(@PathVariable("orderId") int orderId, @Valid @RequestBody OrderUpdateRequest command) {
        return orderService.updateOrder(orderId,command);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> listAllOrder() {
        return orderService.listAllOrder();
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponse findOrderById(@PathVariable("orderId") int orderId) {
        return orderService.findOrderById(orderId);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrderById(@PathVariable("orderId") int orderId){
        orderService.deleteOrderById(orderId);
    }



    @PostMapping("/{orderId}/items/")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItemResponse addOrderItemToOrder(@PathVariable("orderId") int orderId, @Valid @RequestBody OrderItemCreateRequest command) {
        return orderService.addOrderItemToOrder(orderId,command);
    }


    @PutMapping("/{orderId}/items/{orderItemId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderItemResponse updateOrderItem(@PathVariable("orderId") int orderId, @PathVariable("orderItemId") int orderItemId, @Valid @RequestBody OrderItemUpdateRequest command) {
        return orderService.updateOrderItem(orderId,orderItemId,command);
    }


    @GetMapping("/{orderId}/items/")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderItemResponse> listAllOrder(@PathVariable("orderId") int orderId) {
        return orderService.listAllOrderItemByOrderId(orderId);
    }

    @GetMapping("/{orderId}/items/{orderItemId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderItemResponse findOrderItemById(@PathVariable("orderId") int orderId,@PathVariable("orderItemId") int orderItemId) {
        return orderService.findOrderItem(orderId,orderItemId);
    }

    @DeleteMapping("/{orderId}/items/{orderItemId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrderById(@PathVariable("orderId") int orderId,@PathVariable("orderItemId") int orderItemId) {
        orderService.findOrderItem(orderId,orderItemId);
    }
}
