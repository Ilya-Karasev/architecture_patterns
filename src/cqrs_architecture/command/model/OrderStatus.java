package cqrs_architecture.command.model;

/**
 * Перечисление статусов заказа.
 */
public enum OrderStatus {
    CREATED,
    IN_PROGRESS,
    CANCELLED,
    COMPLETED
}
