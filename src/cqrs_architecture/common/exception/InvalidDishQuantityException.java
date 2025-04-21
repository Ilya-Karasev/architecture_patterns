package cqrs_architecture.common.exception;

public class InvalidDishQuantityException extends RuntimeException {
    public InvalidDishQuantityException(int quantity) {
        super("Количество блюда не может быть отрицательным: " + quantity);
    }
}
