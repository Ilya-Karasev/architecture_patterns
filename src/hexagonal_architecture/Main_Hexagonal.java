package hexagonal_architecture;

import hexagonal_architecture.domain.Product;
import hexagonal_architecture.domain.SupplyOrder;
import hexagonal_architecture.in_adapters.ConsoleInterface;
import hexagonal_architecture.in_ports.SupplyOrderUseCase;
import hexagonal_architecture.out_adapters.DefectChecker;
import hexagonal_architecture.out_adapters.DeliveryNotifierService;
import hexagonal_architecture.out_adapters.InMemoryDeliveryRepository;
import hexagonal_architecture.out_adapters.InMemorySupplyOrderRepository;
import hexagonal_architecture.out_ports.*;
import hexagonal_architecture.out_adapters.ForecastingService;
import hexagonal_architecture.service.SupplyOrderService;

import java.util.*;

public class Main_Hexagonal {
    public static void main(String[] args) {
        SupplyOrderRepository orderRepository = new InMemorySupplyOrderRepository();
        DeliveryRepository deliveryRepository = new InMemoryDeliveryRepository();
        DeliveryNotifier deliveryNotifier = new DeliveryNotifierService();
        QualityChecker qualityChecker = new DefectChecker(orderRepository);
        ForecastingService forecastingService = new ForecastingService(orderRepository);
        SupplyOrderUseCase orderService = new SupplyOrderService(
                orderRepository,
                deliveryRepository,
                deliveryNotifier,
                qualityChecker,
                forecastingService
        );
        List<Product> testProducts1 = Arrays.asList(
                new Product(UUID.randomUUID().toString(), "Картофель", 10),
                new Product(UUID.randomUUID().toString(), "Масло", 5)
        );
        List<Product> testProducts2 = Arrays.asList(
                new Product(UUID.randomUUID().toString(), "Булочки", 20),
                new Product(UUID.randomUUID().toString(), "Бракованный сыр", 3)
        );
        SupplyOrder testOrder1 = orderService.createOrder(testProducts1);
        SupplyOrder testOrder2 = orderService.createOrder(testProducts2);
        ConsoleInterface consoleInterface = new ConsoleInterface(orderService);
        consoleInterface.addTestOrder(testOrder1.getId());
        consoleInterface.addTestOrder(testOrder2.getId());
        consoleInterface.run();
    }
}