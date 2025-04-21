package cqrs_architecture.query.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderView {
    private final String id;
    private final String customerName;
    private String status;
    private final LocalDateTime createdAt;
    private final List<OrderItemView> items;
    public OrderView(String id, String customerName, String status, LocalDateTime createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.status = status;
        this.createdAt = createdAt;
        this.items = new ArrayList<>();
    }
    public String getId() {
        return id;
    }
    public String getCustomerName() {
        return customerName;
    }
    public String getStatus() {
        return status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public List<OrderItemView> getItems() {
        return items;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void addItem(OrderItemView item) {
        this.items.add(item);
    }
}
