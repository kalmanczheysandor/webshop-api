package hu.kalmancheysandor.webshop.respository;

import hu.kalmancheysandor.webshop.domain.Order;
import hu.kalmancheysandor.webshop.domain.Product;
import hu.kalmancheysandor.webshop.respository.exception.RecordNotFoundByIdException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {

    private Map<Integer, Order> orders = new HashMap<>();
    private int nextId = 0;

    public OrderRepository() {
//        saveOrder(new Order("BMW","CFX-VE-663",1,"decripto"));
//        saveOrder(new Order("Audi","CFX-VE-569",1,"decripto"));
//        saveOrder(new Order("Trabant","CFX-VE-003",2,"decripto"));
//        saveOrder(new Order("Suzuki","CFX-VE-002",2,"decripto"));
//        saveOrder(new Order("Mercedes","CFX-VE-958",3,"decripto"));
//        saveOrder(new Order("Toyota","CFX-VE-459",3,"decripto"));
    }

    public Order saveOrder(Order toSave) {
        toSave.setId(nextId);
        orders.put(nextId, toSave);
        nextId++;
        return toSave;
    }

    public List<Order> listAllOrder() {
        return new ArrayList<>(orders.values());
    }

    public Order findOrderById(Integer orderId) {
        if(orders.containsKey(orderId)) {
            return orders.get(orderId);
        }
        throw new RecordNotFoundByIdException(orderId);
    }
    public Order deleteOrderById(int orderId){
        Order order=orders.remove(orderId);
        if(order==null) {
            throw new RecordNotFoundByIdException(orderId);
        }
        return order;
    }
}
