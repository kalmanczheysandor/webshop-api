package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.order.OrderItem;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderItemRepository {
    @PersistenceContext
    EntityManager entityManager;


//    public OrderItem saveOrderItem(OrderItem orderItem) {
//        entityManager.persist(orderItem);
//        return orderItem;
//    }
//
//
//    public OrderItem findOrderItemById(int orderItemId) {
//        OrderItem orderItem = entityManager.find(OrderItem.class, orderItemId);
//        if( orderItem==null) {
//            throw new RecordNotFoundByIdException(orderItemId);
//        }
//        return orderItem;
//    }
//
//    public List<OrderItem> listAllOrderItem() {
//        List<OrderItem> orderItems = entityManager.createQuery("SELECT c FROM OrderItem c", OrderItem.class).getResultList();
//        return orderItems;
//    }
//
//    public OrderItem updateOrderItem(OrderItem orderItem) {
//        OrderItem updated = entityManager.merge(orderItem);
//        return updated;
//    }
//
//    public void deleteOrderItemById(int orderItemId) {
//        OrderItem orderItemToDelete = findOrderItemById(orderItemId);
//        deleteOrderItem(orderItemToDelete);
//    }
//
//    private void deleteOrderItem(OrderItem orderItem) {
//        entityManager.remove(orderItem);
//    }


}
