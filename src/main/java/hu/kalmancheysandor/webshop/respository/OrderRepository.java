package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.order.Order;
import hu.kalmancheysandor.webshop.domain.order.OrderItem;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Order saveOrder(Order order) {
        entityManager.persist(order);
        return order;
    }

    public OrderItem saveOrderItem(OrderItem orderItem) {
        entityManager.persist(orderItem);
        return orderItem;
    }


    public Order findOrderById(int orderId) {
        Order order = entityManager.find(Order.class, orderId);
        if( order==null) {
            throw new RecordNotFoundByIdException(orderId);
        }
        return order;
    }

    public OrderItem findOrderItemById(int orderItemId) {
        OrderItem orderItem = entityManager.find(OrderItem.class, orderItemId);
        if( orderItem==null) {
            throw new RecordNotFoundByIdException(orderItemId);
        }
        return orderItem;
    }

    public List<Order> listAllOrder() {
        List<Order> orders = entityManager.createQuery("SELECT c FROM Order c", Order.class).getResultList();
        return orders;
    }

    public List<OrderItem> listAllOrderItem() {
        List<OrderItem> orderItems = entityManager.createQuery("SELECT c FROM OrderItem c", OrderItem.class).getResultList();
        return orderItems;
    }

    public Order updateOrder(Order order) {
        Order updated = entityManager.merge(order);
        return updated;
    }

    public OrderItem updateOrderItem(OrderItem orderItem) {
        OrderItem updated = entityManager.merge(orderItem);
        return updated;
    }

    public void deleteOrderById(int orderId) {
        Order orderToDelete = findOrderById(orderId);
        deleteOrder(orderToDelete);
    }

    public void deleteOrderItemById(int orderItemId) {
        OrderItem orderItemToDelete = findOrderItemById(orderItemId);
        deleteOrderItem(orderItemToDelete);
    }

    private void deleteOrder(Order order) {
        entityManager.remove(order);
    }
    private void deleteOrderItem(OrderItem orderItem) {
        entityManager.remove(orderItem);
    }







}
