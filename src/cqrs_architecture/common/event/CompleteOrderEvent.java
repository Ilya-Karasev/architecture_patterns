package cqrs_architecture.common.event;

import java.time.LocalDateTime;

public class CompleteOrderEvent extends Event {
    private final String orderId;
    private final LocalDateTime completedAt;
    public CompleteOrderEvent(String orderId, LocalDateTime completedAt) {
        super();
        this.orderId = orderId;
        this.completedAt = completedAt;
    }
    public String getOrderId() {
        return orderId;
    }
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}
