package layer_architecture.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class InventoryItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Product product;
    private int quantity;
    private final LocalDate expiryDate;
    public InventoryItem(Product product, int quantity, LocalDate expiryDate) {
        this.product = product;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }
    public Product getProduct() {
        return product;
    }
    public int getQuantity() {
        return quantity;
    }
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    public boolean isExpired(LocalDate currentDate) {
        return expiryDate.isBefore(currentDate);
    }
    public boolean isCritical() {
        return quantity <= product.getCriticalLevel();
    }
    public boolean isBelowMinimumStock() {
        return quantity < product.getMinimumStock();
    }
    public void decreaseQuantity(int amount) {
        if (amount > quantity) {
            throw new IllegalArgumentException("Недостаточно продукта на складе.");
        }
        quantity -= amount;
    }
    public void increaseQuantity(int amount) {
        quantity += amount;
    }
}