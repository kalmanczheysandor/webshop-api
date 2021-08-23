package hu.kalmancheysandor.webshop.service;

import hu.kalmancheysandor.webshop.domain.order.DeliveryStatus;
import hu.kalmancheysandor.webshop.domain.order.Order;
import hu.kalmancheysandor.webshop.domain.order.OrderItem;
import hu.kalmancheysandor.webshop.dto.order.OrderCreateRequest;
import hu.kalmancheysandor.webshop.dto.order.OrderResponse;
import hu.kalmancheysandor.webshop.dto.order.OrderUpdateRequest;
import hu.kalmancheysandor.webshop.respository.OrderRepository;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import hu.kalmancheysandor.webshop.service.exception.OrderNotFoundException;
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
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    OrderService orderService;

    OrderCreateRequest orderCreateRequest01, orderCreateRequest02;
    OrderCreateRequest.CreateRequestItem orderCreateRequestItem01, orderCreateRequestItem02;

    OrderUpdateRequest orderUpdateRequest01;

    Order orderEntity01, orderEntity02;
    Order orderEntity01Updated;
    OrderItem itemEntity01, itemEntity02;
    OrderItem itemEntity01Updated;

    OrderResponse orderResponse01, orderResponse02;
    OrderResponse orderResponse01Updated;

    OrderResponse.ResponseItem orderResponseItem01, orderResponseItem02;
    OrderResponse.ResponseItem orderUpdateResponseItem01;

    @BeforeEach
    void init() {
        // Generation create requests
        orderCreateRequestItem01 = new OrderCreateRequest.CreateRequestItem();
        orderCreateRequestItem01.setProductId(1);
        orderCreateRequestItem01.setQuantity(1);

        orderCreateRequestItem02 = new OrderCreateRequest.CreateRequestItem();
        orderCreateRequestItem01.setProductId(1);
        orderCreateRequestItem01.setQuantity(2);


        // Generation create requests
        orderCreateRequest01 = new OrderCreateRequest();
        orderCreateRequest01.setCustomerId(1);
        orderCreateRequest01.setItems(List.of(orderCreateRequestItem01));

        orderCreateRequest02 = new OrderCreateRequest();
        orderCreateRequest02.setCustomerId(1);
        orderCreateRequest02.setItems(List.of(orderCreateRequestItem02));

        // Generation update requests
        orderUpdateRequest01 = new OrderUpdateRequest();
        orderUpdateRequest01.setDeliveryStatus(DeliveryStatus.DELIVERED);


        // Generation of entities
        itemEntity01 = new OrderItem();
        itemEntity01.setTotalNetPrice(100f);
        itemEntity01.setTotalGrossPrice(110f);
        itemEntity01.setQuantity(1);

        itemEntity02 = new OrderItem();
        itemEntity02.setTotalNetPrice(200f);
        itemEntity02.setTotalGrossPrice(220f);
        itemEntity02.setQuantity(2);

        itemEntity01Updated = new OrderItem();
        itemEntity01Updated.setTotalNetPrice(300f);
        itemEntity01Updated.setTotalGrossPrice(330f);
        itemEntity01Updated.setQuantity(3);

        orderEntity01 = new Order();
        orderEntity01.setId(1);
        orderEntity01.setTotalNetPrice(100.0f);
        orderEntity01.setTotalGrossPrice(110f);
        orderEntity01.setOrderItems(List.of(itemEntity01));
        orderEntity01.setDeliveryStatus(DeliveryStatus.PURCHASED);

        orderEntity02 = new Order();
        orderEntity02.setId(2);
        orderEntity02.setTotalNetPrice(200.0f);
        orderEntity02.setTotalGrossPrice(220f);
        orderEntity02.setOrderItems(List.of(itemEntity02));
        orderEntity02.setDeliveryStatus(DeliveryStatus.PURCHASED);

        orderEntity01Updated = new Order();
        orderEntity01Updated.setId(1);
        orderEntity01Updated.setTotalNetPrice(300.0f);
        orderEntity01Updated.setTotalGrossPrice(330f);
        orderEntity01Updated.setOrderItems(List.of(itemEntity01Updated));
        orderEntity01Updated.setDeliveryStatus(DeliveryStatus.DELIVERED);

        itemEntity01.setOrder(orderEntity01);
        itemEntity02.setOrder(orderEntity02);
        itemEntity01Updated.setOrder(orderEntity01Updated);


        // Generation of responses
        orderResponseItem01 = new OrderResponse.ResponseItem();
        orderResponseItem01.setId(1);
        orderResponseItem01.setTotalNetPrice(100f);
        orderResponseItem01.setTotalGrossPrice(110f);
        orderResponseItem01.setQuantity(1);

        orderResponseItem02 = new OrderResponse.ResponseItem();
        orderResponseItem02.setId(2);
        orderResponseItem02.setTotalNetPrice(200f);
        orderResponseItem02.setTotalGrossPrice(220f);
        orderResponseItem02.setProduct(null);
        orderResponseItem02.setQuantity(2);

        orderUpdateResponseItem01 = new OrderResponse.ResponseItem();
        orderUpdateResponseItem01.setId(1);
        orderUpdateResponseItem01.setTotalNetPrice(300f);
        orderUpdateResponseItem01.setTotalGrossPrice(330f);
        orderUpdateResponseItem01.setQuantity(3);


        orderResponse01 = new OrderResponse();
        orderResponse01.setId(1);
        orderResponse01.setTotalNetPrice(100f);
        orderResponse01.setTotalGrossPrice(110f);
        orderResponse01.setDeliveryStatus(DeliveryStatus.PURCHASED);
        orderResponse01.setItems(List.of(orderResponseItem01));

        orderResponse02 = new OrderResponse();
        orderResponse02.setId(2);
        orderResponse02.setTotalNetPrice(200f);
        orderResponse02.setTotalGrossPrice(220f);
        orderResponse02.setDeliveryStatus(DeliveryStatus.PURCHASED);
        orderResponse02.setItems(List.of(orderResponseItem02));

        orderResponse01Updated = new OrderResponse();
        orderResponse01Updated.setId(1);
        orderResponse01Updated.setTotalNetPrice(300f);
        orderResponse01Updated.setTotalGrossPrice(330f);
        orderResponse01Updated.setDeliveryStatus(DeliveryStatus.DELIVERED);
        orderResponse01Updated.setItems(List.of(orderUpdateResponseItem01));
    }

    @Test
    void test_updateOrder_whenOrderIsFound() {
        // Mocking of repository method(s)
        when(orderRepository.findOrderById(1)).thenReturn(orderEntity01);
        when(orderRepository.updateOrder(orderEntity01)).thenReturn(orderEntity01Updated);

        // Mocking from entity to response
        when(modelMapper.map(orderEntity01Updated, OrderResponse.class)).thenReturn(orderResponse01Updated);

        //Operation(s)
        OrderResponse response = orderService.updateOrder(1, orderUpdateRequest01);

        // Statement(s)
        assertEquals(orderResponse01Updated, response);
        verify(orderRepository, times(1)).findOrderById(1);
        verify(orderRepository, times(1)).updateOrder(orderEntity01);
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void test_updateOrder_whenOrderIsNotFound() {
        // Mocking of repository method(s)
        when(orderRepository.findOrderById(1)).thenThrow(new RecordNotFoundByIdException(1));

        // Statement(s)
        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrder(1, orderUpdateRequest01));
        verify(orderRepository, times(1)).findOrderById(1);
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void test_listAllOrder() {
        // Mocking of repository method(s)
        when(orderRepository.listAllOrder()).thenReturn(List.of(orderEntity01, orderEntity02));

        // Mocking entity to response
        when(modelMapper.map(orderEntity01, OrderResponse.class)).thenReturn(orderResponse01);
        when(modelMapper.map(orderEntity02, OrderResponse.class)).thenReturn(orderResponse02);

        // Statement(s)
        assertThat(orderService.listAllOrder())
                .hasSize(2)
                .containsExactly(orderResponse01, orderResponse02);
        verify(orderRepository, times(1)).listAllOrder();
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void test_findOrderById_whenOrderIsFound() {
        // Mocking of repository method(s)
        when(orderRepository.findOrderById(1)).thenReturn(orderEntity01);

        // Mocking from entity to response
        when(modelMapper.map(orderEntity01, OrderResponse.class)).thenReturn(orderResponse01);

        // Statement(s)
        assertThat(orderService.findOrderById(1))
                .isEqualTo(orderResponse01);
        verify(orderRepository, times(1)).findOrderById(1);
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void test_findOrderById_whenOrderNotFound() {
        // Mocking of repository method(s)
        when(orderRepository.findOrderById(1)).thenThrow(new RecordNotFoundByIdException(1));

        // Statement(s)
        assertThrows(OrderNotFoundException.class, () -> orderService.findOrderById(1));

        verify(orderRepository, times(1)).findOrderById(1);
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    void test_deleteOrderById_whenOrderNotFound() {
        // Mocking of repository method(s)
        doThrow(new RecordNotFoundByIdException(1))
                .when(orderRepository)
                .deleteOrderById(1);

        // Statement(s)
        assertThrows(OrderNotFoundException.class, () -> orderService.deleteOrderById(1));
        verify(orderRepository, times(1)).deleteOrderById(1);
    }
}