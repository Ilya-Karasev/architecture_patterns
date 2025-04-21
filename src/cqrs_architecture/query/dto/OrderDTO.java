package cqrs_architecture.query.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private final String id;
    private final String customerName;
    private final String status;
    private final LocalDateTime createdAt;
    private final List<String> items;
    public OrderDTO(String id, String customerName, String status, LocalDateTime createdAt, List<String> items) {
        this.id = id;
        this.customerName = customerName;
        this.status = status;
        this.createdAt = createdAt;
        this.items = items;
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
    public List<String> getItems() {
        return items;
    }
}
