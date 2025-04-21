package cqrs_architecture.command.model;

import cqrs_architecture.common.exception.InvalidDishQuantityException;

/**
 * Элемент заказа – блюдо и его количество.
 */
public class OrderItem {
    private final String dishName;
    private int quantity;
    public OrderItem(String dishName, int quantity) {
        if (quantity < 0) {
            throw new InvalidDishQuantityException(quantity);
        }
        this.dishName = dishName;
        this.quantity = quantity;
    }
    public String getDishName() {
        return dishName;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new InvalidDishQuantityException(quantity);
        }
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return dishName + " x " + quantity;
    }
}