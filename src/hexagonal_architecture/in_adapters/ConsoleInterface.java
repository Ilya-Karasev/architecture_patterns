package hexagonal_architecture.in_adapters;

import hexagonal_architecture.domain.*;
import hexagonal_architecture.in_ports.SupplyOrderUseCase;

import java.util.*;

public class ConsoleInterface {
    private final SupplyOrderUseCase service;
    private final Scanner scanner = new Scanner(System.in);
    private final Map<Integer, SupplyOrderId> orderMap = new HashMap<>();
    private int orderCounter = 1;
    public ConsoleInterface(SupplyOrderUseCase service) {
        this.service = service;
    }
    public void run() {
        while (true) {
            System.out.println("\n--- Меню ---");
            System.out.println("1. Создать заказ");
            System.out.println("2. Создать заказ по прогнозу");
            System.out.println("3. Подтвердить заказ");
            System.out.println("4. Отправить заказ поставщику");
            System.out.println("5. Отслеживать статус заказа");
            System.out.println("6. Принять поставку и провести проверку качества");
            System.out.println("7. Выйти");
            System.out.print(">> ");
            String input = scanner.nextLine();
            switch (input) {
                case "1" -> createOrder();
                case "2" -> createOrderFromForecast();
                case "3" -> confirmOrder();
                case "4" -> sendOrder();
                case "5" -> checkStatus();
                case "6" -> receiveDelivery();
                case "7" -> {
                    System.out.print("Завершение работы...");
                    return;
                }
                default -> System.out.println("Неверный ввод");
            }
        }
    }
    public void addTestOrder(SupplyOrderId id) {
        orderMap.put(orderCounter++, id);
    }
    private void createOrderFromForecast() {
        SupplyOrder order = service.createOrderFromForecast();
        System.out.println("Заказ по прогнозу создан с ID [" + orderCounter + "]: " + order.getId().getId());
        System.out.println("Содержимое заказа:");
        printProducts(order.getProducts());
        orderMap.put(orderCounter, order.getId());
        orderCounter++;
    }
    private void createOrder() {
        List<Product> products = new ArrayList<>();
        System.out.println("Введите товары (название и количество, 'готово' — для завершения):");
        while (true) {
            System.out.print("Название: ");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("готово")) break;
            System.out.print("Количество: ");
            int qty = Integer.parseInt(scanner.nextLine());
            String id = UUID.randomUUID().toString();
            products.add(new Product(id, name, qty));
        }
        SupplyOrder order = service.createOrder(products);
        orderMap.put(orderCounter, order.getId());
        System.out.println("Заказ создан с ID [" + orderCounter + "]: " + order.getId().getId());
        System.out.println("Содержимое заказа:");
        printProducts(order.getProducts());
        orderCounter++;
    }
    private void confirmOrder() {
        SupplyOrderId id = selectOrder("Подтверждение заказа");
        if (id == null) return;
        try {
            service.confirmOrder(id);
            System.out.println("Заказ подтверждён.");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    private void sendOrder() {
        SupplyOrderId id = selectOrder("Отправка заказа");
        if (id == null) return;
        try {
            service.sendOrder(id);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    private void checkStatus() {
        System.out.println("Проверка статуса - Доступные заказы:");
        for (Map.Entry<Integer, SupplyOrderId> entry : orderMap.entrySet()) {
            int num = entry.getKey();
            SupplyOrderId id = entry.getValue();
            Optional<SupplyOrder> orderOpt = getOrderById(id);
            System.out.print(num + ". " + id.getId());
            orderOpt.ifPresent(order -> {
                System.out.print(" (");
                List<Product> products = order.getProducts();
                for (int i = 0; i < products.size(); i++) {
                    Product p = products.get(i);
                    System.out.print(p.getName() + " x" + p.getQuantity());
                    if (i < products.size() - 1) System.out.print(", ");
                }
                System.out.print(") — " + order.getStatus());
            });
            System.out.println();
        }
    }
    private Optional<SupplyOrder> getOrderById(SupplyOrderId id) {
        return service.findOrderById(id);
    }
    private void receiveDelivery() {
        SupplyOrderId id = selectOrder("Приёмка поставки");
        if (id == null) return;
        Optional<Delivery> existing = service.findDeliveryByOrderId(id);
        if (existing.isPresent()) {
            Delivery delivery = existing.get();
            if (delivery.isQualityChecked()) {
                System.out.println("Качество уже проверено. Результат: " + delivery.getQualityResult());
                return;
            }
            try {
                service.receiveDelivery(delivery);
                System.out.println("Поставка повторно обработана. Результат: " + delivery.getQualityResult());
            } catch (Exception e) {
                System.out.println("Ошибка при повторной приёмке: " + e.getMessage());
            }
        } else {
            List<Product> deliveredProducts = new ArrayList<>();
            System.out.println("Введите поставленные товары (название и количество, 'готово' — для завершения):");
            while (true) {
                System.out.print("Название: ");
                String name = scanner.nextLine();
                if (name.equalsIgnoreCase("готово")) break;

                System.out.print("Количество: ");
                int qty = Integer.parseInt(scanner.nextLine());
                String productId = UUID.randomUUID().toString();
                deliveredProducts.add(new Product(productId, name, qty));
            }
            Delivery delivery = new Delivery(new DeliveryId(), id, deliveredProducts);
            try {
                service.receiveDelivery(delivery);
                System.out.println("Поставка обработана. Результат проверки качества: " + delivery.getQualityResult());
            } catch (Exception e) {
                System.out.println("Ошибка при приёмке: " + e.getMessage());
            }
        }
    }
    private SupplyOrderId selectOrder(String actionTitle) {
        if (orderMap.isEmpty()) {
            System.out.println("Нет доступных заказов.");
            return null;
        }
        System.out.println(actionTitle + " - Доступные заказы:");
        for (Map.Entry<Integer, SupplyOrderId> entry : orderMap.entrySet()) {
            int num = entry.getKey();
            SupplyOrderId id = entry.getValue();
            Optional<SupplyOrder> orderOpt = getOrderById(id);
            System.out.print(num + ". " + id.getId());
            orderOpt.ifPresent(order -> {
                System.out.print(" (");
                List<Product> products = order.getProducts();
                for (int i = 0; i < products.size(); i++) {
                    Product p = products.get(i);
                    System.out.print(p.getName() + " x" + p.getQuantity());
                    if (i < products.size() - 1) System.out.print(", ");
                }
                System.out.print(")");
            });
            System.out.println();
        }
        System.out.print("Введите номер заказа для действия: ");
        String input = scanner.nextLine();
        try {
            int orderNumber = Integer.parseInt(input);
            return orderMap.get(orderNumber);
        } catch (NumberFormatException | NullPointerException e) {
            System.out.println("Некорректный номер заказа.");
            return null;
        }
    }
    private void printProducts(List<Product> products) {
        for (Product product : products) {
            System.out.println(" - " + product.getName() + ": " + product.getQuantity() + " шт.");
        }
    }
}