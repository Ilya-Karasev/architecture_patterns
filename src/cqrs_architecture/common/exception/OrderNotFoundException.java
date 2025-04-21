package cqrs_architecture.common.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderId) {
        super("Заказ с ID " + orderId + " не найден.");
    }
}

