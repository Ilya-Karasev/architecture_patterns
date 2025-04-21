package cqrs_architecture.query.model;

public class OrderItemView {
    private final String dishName;
    private final int quantity;
    private final double pricePerUnit;
    public OrderItemView(String dishName, int quantity, double pricePerUnit) {
        this.dishName = dishName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }
    public String getDishName() {
        return dishName;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPricePerUnit() {
        return pricePerUnit;
    }
    public double getTotalPrice() {
        return quantity * pricePerUnit;
    }
    @Override
    public String toString() {
        return dishName + " x" + quantity + " = " + getTotalPrice() + " руб.";
    }
}
