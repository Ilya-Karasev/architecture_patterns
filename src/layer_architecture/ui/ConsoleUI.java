package layer_architecture.ui;

import layer_architecture.application.InventoryService;
import layer_architecture.domain.InventoryItem;
import layer_architecture.domain.Product;
import layer_architecture.infrastructure.ReportGenerator;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final InventoryService inventoryService;
    private final Scanner scanner;
    public ConsoleUI(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.scanner = new Scanner(System.in);
    }
    public void showMenu() {
        while (true) {
            System.out.println("\n--- Меню ---");
            System.out.println("1. Добавить продукт");
            System.out.println("2. Использовать продукт");
            System.out.println("3. Списать просроченные продукты");
            System.out.println("4. Провести инвентаризацию");
            System.out.println("5. Генерация отчёта");
            System.out.println("6. Просмотр критических запасов");
            System.out.println("7. Просмотр запасов ниже минимального уровня");
            System.out.println("8. Выйти");
            System.out.print("Выберите опцию: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addProduct();
                case 2 -> useProduct();
                case 3 -> writeOffExpiredProducts();
                case 4 -> takeInventoryAndAdjust();
                case 5 -> generateReport();
                case 6 -> showCriticalStock();
                case 7 -> showBelowMinimumStock();
                case 8 -> {
                    System.out.println("Выход из программы...");
                    return;
                }
                default -> System.out.println("Неверный выбор, попробуйте снова.");
            }
        }
    }
    private void addProduct() {
        System.out.print("Введите название продукта: ");
        String name = scanner.nextLine();
        System.out.print("Введите срок годности (в днях): ");
        int shelfLife = scanner.nextInt();
        System.out.print("Введите критический уровень: ");
        int criticalLevel = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите температурный режим (замороженный/охлажденный/при комнатной температуре): ");
        String temperatureMode = scanner.nextLine();
        System.out.print("Введите минимальный запас: ");
        int minimumStock = scanner.nextInt();
        System.out.print("Введите оптимальный запас: ");
        int optimalStock = scanner.nextInt();
        scanner.nextLine();
        Product product = new Product(name, shelfLife, criticalLevel, temperatureMode, minimumStock, optimalStock);
        System.out.print("Введите количество: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        inventoryService.addProduct(product, quantity);
        System.out.println("Продукт добавлен в инвентарь.");
    }
    private void useProduct() {
        System.out.print("Введите название продукта: ");
        String productName = scanner.nextLine();
        System.out.print("Введите количество для использования: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();
        try {
            inventoryService.useProduct(productName, quantity);
            System.out.println("Продукт использован.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    private void writeOffExpiredProducts() {
        inventoryService.writeOffExpiredProducts();
        System.out.println("Просроченные продукты списаны.");
    }
    private void takeInventoryAndAdjust() {
        System.out.print("Введите название продукта для инвентаризации: ");
        String productName = scanner.nextLine();
        int currentQuantity = inventoryService.getCurrentQuantity(productName);
        System.out.println("Текущее количество в системе: " + currentQuantity);
        System.out.print("Введите фактическое количество по результатам подсчета: ");
        int actualQuantity = scanner.nextInt();
        scanner.nextLine();
        inventoryService.takeInventory(productName, actualQuantity);
        System.out.println("Инвентаризация завершена.");
        if (actualQuantity != currentQuantity) {
            inventoryService.adjustStock(productName, actualQuantity);
            System.out.println("Выявлены расхождения. Запасы скорректированы.");
        } else {
            System.out.println("Расхождений не обнаружено. Корректировка не требуется.");
        }
    }
    private void generateReport() {
        ReportGenerator reportGenerator = new ReportGenerator();
        String report = reportGenerator.generateReportText(inventoryService.getInventory());
        System.out.println("\n" + report);
    }
    private void showCriticalStock() {
        List<InventoryItem> criticalItems = inventoryService.getCriticalStockItems();
        if (criticalItems.isEmpty()) {
            System.out.println("Нет критических запасов.");
        } else {
            System.out.println("Критические запасы:");
            for (InventoryItem item : criticalItems) {
                System.out.println("Продукт: " + item.getProduct().getName() +
                        ", Кол-во: " + item.getQuantity());
            }
        }
    }
    private void showBelowMinimumStock() {
        List<InventoryItem> belowMinStockItems = inventoryService.getBelowMinimumStockItems();
        if (belowMinStockItems.isEmpty()) {
            System.out.println("Нет продуктов с запасом ниже минимального.");
        } else {
            System.out.println("Продукты с запасом ниже минимального:");
            for (InventoryItem item : belowMinStockItems) {
                System.out.println("Продукт: " + item.getProduct().getName() +
                        ", Кол-во: " + item.getQuantity());
            }
        }
    }
}