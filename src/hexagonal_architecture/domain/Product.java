package hexagonal_architecture.domain;

import java.util.Objects;

public class Product {
    private final String productId;
    private final String name;
    private final int quantity;
    public Product(String productId, String name, int quantity) {
        if (quantity < 0) throw new IllegalArgumentException("Quantity must be positive");
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
    }
    public String getProductId() {
        return productId;
    }
    public String getName() {
        return name;
    }
    public int getQuantity() {
        return quantity;
    }
    public Product withQuantity(int newQuantity) {
        return new Product(productId, name, newQuantity);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
