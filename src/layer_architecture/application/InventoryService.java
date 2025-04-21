package layer_architecture.application;

import layer_architecture.domain.Inventory;
import layer_architecture.domain.InventoryItem;
import layer_architecture.domain.InventoryRepository;
import layer_architecture.domain.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InventoryService {
    private final InventoryRepository repository;
    private Inventory inventory;
    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
        this.inventory = repository.load().orElse(new Inventory());
    }
    public void addProduct(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Продукт не может быть пустым.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным.");
        }
        Optional<InventoryItem> existingItem = inventory.getCurrentStock().stream()
                .filter(item -> item.getProduct().getName().equals(product.getName()))
                .findFirst();
        if (existingItem.isPresent()) {
            InventoryItem item = existingItem.get();
            item.increaseQuantity(quantity);
        } else {
            inventory.addProduct(product, quantity, LocalDate.now());
        }
        repository.save(inventory);
    }
    public void useProduct(String productName, int quantity) {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Имя продукта не может быть пустым.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным.");
        }
        inventory.useProduct(productName, quantity);
        repository.save(inventory);
    }
    public void writeOffExpiredProducts() {
        inventory.writeOffExpiredProducts(LocalDate.now());
        repository.save(inventory);
    }
    public void adjustStock(String productName, int newTotalQuantity) {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Имя продукта не может быть пустым.");
        }
        if (newTotalQuantity < 0) {
            throw new IllegalArgumentException("Новый уровень количества не может быть отрицательным.");
        }
        inventory.adjustStock(productName, newTotalQuantity);
        repository.save(inventory);
    }
    public List<InventoryItem> getCriticalStockItems() {
        return inventory.getCriticalStockItems();
    }
    public List<InventoryItem> getBelowMinimumStockItems() {
        return inventory.getCurrentStock().stream()
                .filter(InventoryItem::isBelowMinimumStock)
                .collect(Collectors.toList());
    }
    public Map<String, Integer> generateRawReport() {
        return inventory.generateReport();
    }
    public int getCurrentQuantity(String productName) {
        return inventory.getQuantityByName(productName);
    }
    public void takeInventory(String productName, int actualQuantity) {
        inventory.recordInventoryCheck(productName, actualQuantity, LocalDate.now());
        repository.save(inventory);
    }
    public Inventory getInventory() {
        return inventory;
    }
    public void saveInventory() {
        repository.save(inventory);
    }
}