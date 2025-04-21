package cqrs_architecture.query.repository;

import cqrs_architecture.common.exception.OrderNotFoundException;
import cqrs_architecture.query.model.OrderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderViewRepository {
    private final Map<String, OrderView> orders = new HashMap<>();
    public void save(OrderView orderView) {
        orders.put(orderView.getId(), orderView);
    }
    public OrderView findById(String id) {
        OrderView order = orders.get(id);
        if (order == null) {
            throw new OrderNotFoundException(id);
        }
        return order;
    }
    public List<OrderView> findAll() {
        return new ArrayList<>(orders.values());
    }
}
