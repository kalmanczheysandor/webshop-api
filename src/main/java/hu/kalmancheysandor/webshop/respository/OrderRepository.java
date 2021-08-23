package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.order.Order;
import hu.kalmancheysandor.webshop.domain.order.OrderItem;
import hu.kalmancheysandor.webshop.domain.product.Product;
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
        if (order == null) {
            throw new RecordNotFoundByIdException(orderId);
        }
        return order;
    }

    public OrderItem findOrderItemById(int orderItemId) {
        OrderItem orderItem = entityManager.find(OrderItem.class, orderItemId);
        if (orderItem == null) {
            throw new RecordNotFoundByIdException(orderItemId);
        }
        return orderItem;
    }

    public List<Order> listAllOrder() {
        return entityManager.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    }

    public List<OrderItem> listAllOrderItem() {
        return entityManager.createQuery("SELECT c FROM OrderItem c", OrderItem.class).getResultList();
    }

    public List<OrderItem> listAllOrderItemByOrderId(int orderId) {
        Order order = entityManager.find(Order.class, orderId);
        if (order == null) {
            throw new RecordNotFoundByIdException(orderId);
        }

        return entityManager.createQuery("SELECT i FROM OrderItem i " +
                "JOIN i.order o " +
                "WHERE o.id=:paramOrderId", OrderItem.class)
                .setParameter("paramOrderId", orderId)
                .getResultList();
    }

    public Order updateOrder(Order order) {
        return entityManager.merge(order);
    }

    public OrderItem updateOrderItem(OrderItem orderItem) {
        return entityManager.merge(orderItem);
    }

    public void deleteOrderById(int orderId) {
        Order orderToDelete = findOrderById(orderId);
        deleteOrder(orderToDelete);
    }

    public void deleteOrderItemById(int orderItemId) {
        OrderItem orderItemToDelete = findOrderItemById(orderItemId);
        deleteOrderItem(orderItemToDelete);
    }

    public void deleteAllOrderItemByOrderId(int orderId) {
        Order order = entityManager.find(Order.class, orderId);
        if (order == null) {
            throw new RecordNotFoundByIdException(orderId);
        }

        List<OrderItem> orderItems = listAllOrderItemByOrderId(orderId);
        for (OrderItem orderItem : orderItems) {
            deleteOrderItem(orderItem);
        }
    }

    private void deleteOrder(Order order) {
        entityManager.remove(order);
    }

    public void deleteOrderItem(OrderItem orderItem) {
        entityManager.remove(orderItem);
    }

}
