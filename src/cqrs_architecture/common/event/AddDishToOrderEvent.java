package cqrs_architecture.common.event;

import java.time.LocalDateTime;

public class AddDishToOrderEvent extends Event {
    private final String orderId;
    private final String dishName;
    private final int quantity;
    public AddDishToOrderEvent(String orderId, String dishName, int quantity, LocalDateTime timestamp) {
        super();
        this.orderId = orderId;
        this.dishName = dishName;
        this.quantity = quantity;
    }
    public String getOrderId() {
        return orderId;
    }
    public String getDishName() {
        return dishName;
    }
    public int getQuantity() {
        return quantity;
    }
}
