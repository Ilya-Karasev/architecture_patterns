package cqrs_architecture.command.repository;

import cqrs_architecture.command.model.CustomerOrder;
import cqrs_architecture.common.exception.OrderNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * Простейшая in-memory реализация репозитория заказов.
 */
public class OrderRepository {
    private final Map<String, CustomerOrder> orders = new HashMap<>();
    public void save(CustomerOrder order) {
        orders.put(order.getId(), order);
    }
    public CustomerOrder findById(String orderId) {
        CustomerOrder order = orders.get(orderId);
        if (order == null) {
            throw new OrderNotFoundException(orderId);
        }
        return order;
    }
}
