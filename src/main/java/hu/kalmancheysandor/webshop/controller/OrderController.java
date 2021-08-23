package hu.kalmancheysandor.webshop.controller;

import hu.kalmancheysandor.webshop.dto.order.*;
import hu.kalmancheysandor.webshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@Slf4j
@Tag(name = "Vásárlás adminisztráció")
public class OrderController {
    @Autowired
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Mentés",
            description = "Új megrendelés felvétele.")
    public OrderResponse saveOrder(@Valid @RequestBody OrderCreateRequest command) {
        log.info("Http request; Method type:POST; URL:/api/admin/orders/; Body:" + command.toString());
        return orderService.saveOrder(command);
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Módosítás",
            description = "Korábban mentett megrendelés id általi elérése és mezőinek felülírása.")
    public OrderResponse updateOrder(@Parameter(description = "Megrendelés id", example = "5")
                                     @PathVariable("orderId") int orderId,
                                     @Valid @RequestBody OrderUpdateRequest command) {
        log.info("Http request; Method type:PUT; URL:/api/admin/orders/" + orderId + "/; Body:" + command.toString());
        return orderService.updateOrder(orderId, command);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listázás",
            description = "Kilistázza az összes mentett megrendelést.")
    public List<OrderResponse> listAllOrder() {
        log.info("Http request; Method type:GET; URL:/api/admin/orders/");
        return orderService.listAllOrder();
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lekérés",
            description = "Korábban mentett megrendelés id általi lekérése.")
    public OrderResponse findOrderById(@Parameter(description = "Megrendelés id", example = "5")
                                       @PathVariable("orderId") int orderId) {
        log.info("Http request; Method type:GET; URL:/api/admin/orders/" + orderId + "/");
        return orderService.findOrderById(orderId);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Törlés",
            description = "Korábban mentett megrendelés id általi törlése.")
    public void deleteOrderById(@Parameter(description = "Megrendelés id", example = "5")
                                @PathVariable("orderId") int orderId) {
        log.info("Http request; Method type:DELETE; URL:/api/admin/orders/" + orderId + "/");
        orderService.deleteOrderById(orderId);
    }


    @PostMapping("/{orderId}/items/")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Mentés(tétel)",
            description = "Új megrendelési tétel felvétele.")
    public OrderItemResponse addOrderItemToOrder(@Parameter(description = "Megrendelés id", example = "5")
                                                 @PathVariable("orderId") int orderId,
                                                 @Valid @RequestBody OrderItemCreateRequest command) {
        log.info("Http request; Method type:POST; URL:/api/admin/orders/" + orderId + "/items/; Body:" + command.toString());
        return orderService.addOrderItemToOrder(orderId, command);
    }


    @PutMapping("/{orderId}/items/{orderItemId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Módosítás(tétel)",
            description = "Korábban mentett megrendelési tételt id általi elérése és mezőinek felülírása.")
    public OrderItemResponse updateOrderItem(@Parameter(description = "Megrendelés id", example = "5")
                                             @PathVariable("orderId") int orderId,
                                             @Parameter(description = "Tétel id", example = "9")
                                             @PathVariable("orderItemId") int orderItemId,
                                             @Valid @RequestBody OrderItemUpdateRequest command) {
        log.info("Http request; Method type:PUT; URL:/api/admin/orders/" + orderId + "/items/" + orderItemId + "/; Body:" + command.toString());
        return orderService.updateOrderItem(orderId, orderItemId, command);
    }


    @GetMapping("/{orderId}/items/")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listázás(tétel)",
            description = "Kilistázza az összes mentett megrendelési tételt.")
    public List<OrderItemResponse> listAllOrder(@Parameter(description = "Megrendelés id", example = "5")
                                                @PathVariable("orderId") int orderId) {
        log.info("Http request; Method type:GET; URL:/api/admin/orders/" + orderId + "/items/");
        return orderService.listAllOrderItemByOrderId(orderId);
    }

    @GetMapping("/{orderId}/items/{orderItemId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Lekérés(tétel)",
            description = "Korábban mentett megrendelési tétel id általi lekérése.")
    public OrderItemResponse findOrderItemById(@Parameter(description = "Megrendelés id", example = "5")
                                               @PathVariable("orderId") int orderId,
                                               @Parameter(description = "Tétel id", example = "9")
                                               @PathVariable("orderItemId") int orderItemId) {
        log.info("Http request; Method type:GET; URL:/api/admin/orders/" + orderId + "/items/" + orderItemId + "/");
        return orderService.findOrderItem(orderId, orderItemId);
    }

    @DeleteMapping("/{orderId}/items/{orderItemId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Törlés(tétel)",
            description = "Korábban mentett megrendelési tétel id általi törlése.")
    public void deleteOrderById(@Parameter(description = "Megrendelés id", example = "5")
                                @PathVariable("orderId") int orderId,
                                @Parameter(description = "Tétel id", example = "9")
                                @PathVariable("orderItemId") int orderItemId) {
        log.info("Http request; Method type:DELETE; URL:/api/admin/orders/" + orderId + "/items/" + orderItemId + "/");
        orderService.deleteOrderItem(orderId, orderItemId);
    }
}
