package cqrs_architecture.common.exception;

public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException(String dishName) {
        super("Блюдо \"" + dishName + "\" не найдено в заказе.");
    }
}
