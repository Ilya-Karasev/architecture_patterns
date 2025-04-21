package cqrs_architecture.query.dto;

public class OrderStatisticsDTO {
    private final String orderId;
    private final int totalDishes;
    private final double totalPrice;
    public OrderStatisticsDTO(String orderId, int totalDishes, double totalPrice) {
        this.orderId = orderId;
        this.totalDishes = totalDishes;
        this.totalPrice = totalPrice;
    }
    public String getOrderId() {
        return orderId;
    }
    public int getTotalDishes() {
        return totalDishes;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
}
