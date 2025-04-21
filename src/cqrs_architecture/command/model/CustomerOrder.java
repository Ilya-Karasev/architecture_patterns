package cqrs_architecture.command.model;

import cqrs_architecture.common.event.*;
import cqrs_architecture.common.exception.DishNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import cqrs_architecture.common.exception.InvalidDishQuantityException;
import cqrs_architecture.common.exception.OrderAlreadyCompletedException;

/**
 * Агрегат "Заказ клиента". Содержит список блюд, статус и информацию о клиенте.
 */
public class CustomerOrder {
    private final String id;
    private final String customerName;
    private final List<OrderItem> items;
    private OrderStatus status;
    private final LocalDateTime createdAt;
    public CustomerOrder(String customerName) {
        this.id = UUID.randomUUID().toString();
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.status = OrderStatus.CREATED;
        this.createdAt = LocalDateTime.now();

        EventBus.getInstance().publish(new CreateOrderEvent(this.id, this.customerName, this.createdAt));
    }
    public String getId() {
        return id;
    }
    public String getCustomerName() {
        return customerName;
    }
    public List<OrderItem> getItems() {
        return items;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void addDish(String dishName, int quantity) {
        if (this.status == OrderStatus.COMPLETED) {
            throw new OrderAlreadyCompletedException(this.id);
        }
        if (quantity < 0) {
            throw new InvalidDishQuantityException(quantity);
        }
        items.add(new OrderItem(dishName, quantity));
        EventBus.getInstance().publish(new AddDishToOrderEvent(this.id, dishName, quantity, LocalDateTime.now()));
    }
    public void updateDishQuantity(String dishName, int newQuantity) {
        if (this.status == OrderStatus.COMPLETED) {
            throw new OrderAlreadyCompletedException(this.id);
        }
        boolean found = false;
        for (OrderItem item : items) {
            if (item.getDishName().equals(dishName)) {
                item.setQuantity(newQuantity);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new DishNotFoundException(dishName);
        }
        EventBus.getInstance().publish(new UpdateOrderEvent(this.id, LocalDateTime.now()));
    }
    public void changeStatus(OrderStatus newStatus) {
        this.status = newStatus;
        EventBus.getInstance().publish(new UpdateOrderEvent(this.id, LocalDateTime.now()));
    }
    public void completeOrder() {
        if (this.status == OrderStatus.COMPLETED) {
            throw new OrderAlreadyCompletedException(this.id);
        }
        this.status = OrderStatus.COMPLETED;
        EventBus.getInstance().publish(new CompleteOrderEvent(this.id, LocalDateTime.now()));
    }
}
