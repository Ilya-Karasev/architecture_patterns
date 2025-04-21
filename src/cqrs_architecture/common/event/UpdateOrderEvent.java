package cqrs_architecture.common.event;

import java.time.LocalDateTime;

public class UpdateOrderEvent extends Event {
    private final String orderId;
    private final LocalDateTime updatedAt;
    public UpdateOrderEvent(String orderId, LocalDateTime updatedAt) {
        super();
        this.orderId = orderId;
        this.updatedAt = updatedAt;
    }
    public String getOrderId() {
        return orderId;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
