package cqrs_architecture.common.event;

import java.time.LocalDateTime;

public class CreateOrderEvent extends Event {
    private final String orderId;
    private final String customerName;
    private final LocalDateTime createdAt;
    public CreateOrderEvent(String orderId, String customerName, LocalDateTime createdAt) {
        super();
        this.orderId = orderId;
        this.customerName = customerName;
        this.createdAt = createdAt;
    }
    public String getOrderId() {
        return orderId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
