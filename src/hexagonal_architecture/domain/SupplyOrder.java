package hexagonal_architecture.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SupplyOrder {
    private final SupplyOrderId id;
    private final List<Product> products;
    private OrderStatus status;
    private final LocalDateTime createdAt;
    public SupplyOrder(SupplyOrderId id, List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Заказ должен содержать хотя бы один товар.");
        }
        this.id = id;
        this.products = new ArrayList<>(products);
        this.status = OrderStatus.CREATED;
        this.createdAt = LocalDateTime.now();
    }
    public void confirm() {
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Подтвердить можно только заказ со статусом СОЗДАН.");
        }
        this.status = OrderStatus.CONFIRMED;
    }
    public void markAsSent() {
        if (status != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Отправить можно только подтверждённый заказ.");
        }
        this.status = OrderStatus.SENT;
    }
    public void markAsDelivered() {
        if (status != OrderStatus.SENT) {
            throw new IllegalStateException("Доставить можно только отправленный заказ.");
        }
        this.status = OrderStatus.DELIVERED;
    }
    public void cancel() {
        if (status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Нельзя отменить уже доставленный заказ.");
        }
        this.status = OrderStatus.CANCELLED;
    }
    public SupplyOrderId getId() {
        return id;
    }
    public List<Product> getProducts() {
        return List.copyOf(products);
    }
    public OrderStatus getStatus() {
        return status;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Заказ ID: ").append(id.getId()).append("\n");
        sb.append("Статус: ").append(status).append("\n");
        sb.append("Создан: ").append(createdAt).append("\n");
        sb.append("Товары:\n");
        for (Product product : products) {
            sb.append("- ").append(product.getName()).append(" (").append(product.getQuantity()).append(" шт.)\n");
        }
        return sb.toString();
    }
}
