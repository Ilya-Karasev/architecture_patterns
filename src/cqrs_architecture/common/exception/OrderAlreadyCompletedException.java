package cqrs_architecture.common.exception;

public class OrderAlreadyCompletedException extends RuntimeException {
    public OrderAlreadyCompletedException(String orderId) {
        super("Заказ с ID " + orderId + " уже завершён и не может быть изменён.");
    }
}