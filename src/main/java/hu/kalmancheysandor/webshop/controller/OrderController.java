package hu.kalmancheysandor.webshop.controller;

import hu.kalmancheysandor.webshop.dto.order.OrderCreateRequest;
import hu.kalmancheysandor.webshop.dto.order.OrderResponse;
import hu.kalmancheysandor.webshop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/order")
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
}
