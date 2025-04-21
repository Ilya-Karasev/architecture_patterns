package layer_architecture;

import layer_architecture.application.InventoryService;
import layer_architecture.infrastructure.MemoryRepository;
import layer_architecture.ui.ConsoleUI;
import layer_architecture.domain.Product;

public class Main_Layer {
    public static void main(String[] args) {
        String filePath = "inventory.dat";
        MemoryRepository repository = new MemoryRepository(filePath);
        InventoryService inventoryService = new InventoryService(repository);
        ConsoleUI consoleUI = new ConsoleUI(inventoryService);
        initializeTestProducts(inventoryService);
        consoleUI.showMenu();
    }
    private static void initializeTestProducts(InventoryService inventoryService) {
        Product bread = new Product("Хлеб", -7, 5, "При комнатной температуре", 10, 30);
        Product milk = new Product("Молоко", 14, 3, "Охлаждённый", 8, 20);
        Product butter = new Product("Масло", 30, 2, "Охлаждённый", 5, 20);
        Product eggs = new Product("Яйца", -21, 10, "Охлаждённый", 20, 50);
        Product cheese = new Product("Сыр", 60, 5, "Охлаждённый", 10, 30);
        inventoryService.addProduct(bread, 20);
        inventoryService.addProduct(milk, 10);
        inventoryService.addProduct(butter, 15);
        inventoryService.addProduct(eggs, 50);
        inventoryService.addProduct(cheese, 30);
        System.out.println("Тестовые продукты добавлены в инвентарь.");
    }
}