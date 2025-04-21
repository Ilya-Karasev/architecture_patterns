package hexagonal_architecture.in_ports;

import hexagonal_architecture.domain.*;

import java.util.List;
import java.util.Optional;

public interface SupplyOrderUseCase {
    SupplyOrder createOrder(List<Product> products);
    void confirmOrder(SupplyOrderId id);
    void sendOrder(SupplyOrderId id);
    void receiveDelivery(Delivery delivery);
    OrderStatus getOrderStatus(SupplyOrderId id);
    SupplyOrder getOrder(SupplyOrderId id);
    Optional<SupplyOrder> findOrderById(SupplyOrderId id);
    Optional<Delivery> findDeliveryByOrderId(SupplyOrderId id);
    SupplyOrder createOrderFromForecast();
}
