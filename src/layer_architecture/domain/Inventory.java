package layer_architecture.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<InventoryItem> items = new ArrayList<>();
    public void addProduct(Product product, int quantity, LocalDate currentDate) {
        if (product == null || currentDate == null) {
            throw new IllegalArgumentException("Продукт или дата не могут быть пустыми.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным.");
        }
        LocalDate expiryDate;
        if (product.getShelfLifeDays() > 0) {
            expiryDate = currentDate.plusDays(product.getShelfLifeDays());
        } else if (product.getShelfLifeDays() < 0) {
            expiryDate = currentDate.plusDays(product.getShelfLifeDays());
        } else {
            expiryDate = currentDate;
        }
        items.add(new InventoryItem(product, quantity, expiryDate));
    }
    public void useProduct(String productName, int quantity) {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Имя продукта не может быть пустым.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть положительным.");
        }
        List<InventoryItem> matchingItems = items.stream()
                .filter(item -> item.getProduct().getName().equalsIgnoreCase(productName)
                        && !item.isExpired(LocalDate.now()))
                .sorted(Comparator.comparing(InventoryItem::getExpiryDate)) // FIFO
                .collect(Collectors.toList());

        int remaining = quantity;
        for (InventoryItem item : matchingItems) {
            if (item.getQuantity() >= remaining) {
                item.decreaseQuantity(remaining);
                return;
            } else {
                remaining -= item.getQuantity();
                item.decreaseQuantity(item.getQuantity());
            }
        }
        if (remaining > 0) {
            throw new IllegalArgumentException("Недостаточно доступного продукта: " + productName);
        }
    }
    public void writeOffExpiredProducts(LocalDate currentDate) {
        if (currentDate == null) {
            throw new IllegalArgumentException("Дата не может быть пустой.");
        }
        items.removeIf(item -> item.isExpired(currentDate));
    }
    public void adjustStock(String productName, int newTotalQuantity) {
        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Имя продукта не может быть пустым.");
        }
        if (newTotalQuantity < 0) {
            throw new IllegalArgumentException("Новый уровень количества не может быть отрицательным.");
        }
        List<InventoryItem> toRemove = items.stream()
                .filter(i -> i.getProduct().getName().equalsIgnoreCase(productName))
                .collect(Collectors.toList());
        items.removeAll(toRemove);

        if (!toRemove.isEmpty()) {
            Product product = toRemove.get(0).getProduct();
            addProduct(product, newTotalQuantity, LocalDate.now());
        }
    }
    public List<InventoryItem> getCurrentStock() {
        return Collections.unmodifiableList(items);
    }
    public List<InventoryItem> getCriticalStockItems() {
        return items.stream()
                .filter(item -> item.isCritical() && !item.isExpired(LocalDate.now()))
                .collect(Collectors.toList());
    }
    public int getQuantityByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Имя продукта не может быть пустым.");
        }
        return items.stream()
                .filter(item -> item.getProduct().getName().equalsIgnoreCase(name))
                .mapToInt(InventoryItem::getQuantity)
                .sum();
    }
    public void recordInventoryCheck(String name, int actualQuantity, LocalDate date) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Имя продукта не может быть пустым.");
        }
        if (actualQuantity < 0) {
            throw new IllegalArgumentException("Фактическое количество не может быть отрицательным.");
        }
        if (date == null) {
            throw new IllegalArgumentException("Дата не может быть пустой.");
        }

        List<InventoryItem> toRemove = items.stream()
                .filter(item -> item.getProduct().getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
        items.removeAll(toRemove);
        if (!toRemove.isEmpty()) {
            Product product = toRemove.get(0).getProduct();
            addProduct(product, actualQuantity, date);
        } else {
            throw new IllegalArgumentException("Продукт с именем '" + name + "' не найден.");
        }
    }
    public Map<String, Integer> generateReport() {
        Map<String, Integer> report = new HashMap<>();
        for (InventoryItem item : items) {
            String name = item.getProduct().getName();
            report.put(name, report.getOrDefault(name, 0) + item.getQuantity());
        }
        return report;
    }
}