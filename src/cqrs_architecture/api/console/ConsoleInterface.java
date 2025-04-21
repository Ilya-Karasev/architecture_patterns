package cqrs_architecture.api.console;

import cqrs_architecture.api.facade.Facade;
import cqrs_architecture.command.model.OrderStatus;
import cqrs_architecture.common.exception.OrderNotFoundException;
import cqrs_architecture.query.dto.OrderDTO;
import cqrs_architecture.query.dto.OrderStatisticsDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ConsoleInterface {
    private final Facade facade;
    private final Scanner scanner;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss");
    public ConsoleInterface(Facade facade) {
        this.facade = facade;
        this.scanner = new Scanner(System.in);
    }
    public void start() {
        while (true) {
            showMainMenu();
            int choice = readIntInput();
            scanner.nextLine();
            handleMainMenuChoice(choice);
        }
    }
    private void showMainMenu() {
        System.out.println("\n=== Меню ===");
        System.out.println("1. Создать новый заказ");
        System.out.println("2. Добавить блюдо в заказ");
        System.out.println("3. Изменить состав заказа (количество блюда)");
        System.out.println("4. Изменить статус заказа");
        System.out.println("5. Завершить заказ");
        System.out.println("6. Получить детали заказа");
        System.out.println("7. Показать все заказы");
        System.out.println("8. Выход");
        System.out.print(">> ");
    }
    private void handleMainMenuChoice(int choice) {
        try {
            switch (choice) {
                case 1 -> createNewOrder();
                case 2 -> addDish();
                case 3 -> updateDish();
                case 4 -> changeStatus();
                case 5 -> completeOrder();
                case 6 -> showOrderDetails();
                case 7 -> showAllOrders();
                case 8 -> {
                    System.out.println("Завершение работы...");
                    System.exit(0);
                    }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    private void createNewOrder() {
        System.out.print("Введите имя клиента: ");
        String customerName = scanner.nextLine().trim();
        facade.createOrder(customerName);
        System.out.println("Заказ создан для клиента " + customerName);
    }
    private void addDish() {
        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine().trim();
        System.out.print("Введите название блюда: ");
        String dishName = scanner.nextLine().trim();
        System.out.print("Введите количество: ");
        int quantity = readIntInput();
        facade.addDish(orderId, dishName, quantity);
        System.out.println("Блюдо добавлено в заказ.");
    }
    private void updateDish() {
        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine().trim();
        System.out.print("Введите название блюда: ");
        String dishName = scanner.nextLine().trim();
        System.out.print("Введите новое количество: ");
        int newQuantity = readIntInput();
        facade.updateDish(orderId, dishName, newQuantity);
        System.out.println("Количество блюда обновлено.");
    }
    private void changeStatus() {
        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine().trim();
        System.out.println("Выберите новый статус:");
        OrderStatus[] statuses = OrderStatus.values();
        for (int i = 0; i < statuses.length - 1; i++) { // исключаем последний
            System.out.println((i + 1) + ". " + statuses[i]);
        }
        System.out.print(">> ");
        int choice = readIntInput();
        if (choice < 1 || choice > statuses.length - 1) {
            System.out.println("Некорректный выбор.");
            return;
        }
        OrderStatus newStatus = statuses[choice - 1];
        facade.changeOrderStatus(orderId, newStatus);
        System.out.println("Статус заказа изменён на " + newStatus);
    }
    private void completeOrder() {
        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine().trim();
        facade.completeOrder(orderId);
        System.out.println("Заказ завершён.");
    }
    private void showOrderDetails() {
        System.out.print("Введите ID заказа: ");
        String orderId = scanner.nextLine().trim();
        OrderDTO order = facade.getOrder(orderId);
        if (order == null) {
            System.out.println("Заказ не найден.");
            return;
        }
        System.out.println("\n=== Детали заказа ===");
        System.out.println("ID: " + order.getId());
        System.out.println("Клиент: " + order.getCustomerName());
        System.out.println("Статус: " + order.getStatus());
        System.out.println("Создан: " + order.getCreatedAt().format(dateFormatter));
        System.out.println("Блюда:");
        order.getItems().forEach(item -> System.out.println(" - " + item));
    }
    private void showAllOrders() {
        List<OrderDTO> orders = facade.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("Нет доступных заказов.");
            return;
        }
        System.out.println("\n=== Статистика заказов ===");
        orders.forEach(order -> {
            OrderStatisticsDTO stats = facade.getOrderStatistics(order.getId());
            System.out.println("ID: " + order.getId() + " | Клиент: " + order.getCustomerName() +
                    " | Статус: " + order.getStatus());
            System.out.println("Общее количество блюд: " + stats.getTotalDishes());
            System.out.println("Общая стоимость: " + stats.getTotalPrice() + " руб.");
        });
    }
    private int readIntInput() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            return -1;
        }
    }
}