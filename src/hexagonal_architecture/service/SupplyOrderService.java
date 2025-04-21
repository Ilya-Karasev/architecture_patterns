package hexagonal_architecture.service;

import hexagonal_architecture.domain.*;
import hexagonal_architecture.in_ports.SupplyOrderUseCase;
import hexagonal_architecture.out_ports.*;

import java.util.*;

public class SupplyOrderService implements SupplyOrderUseCase {
    private final SupplyOrderRepository supplyOrderRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryNotifier deliveryNotifier;
    private final QualityChecker qualityChecker;
    private final ForecastingPort forecastingPort;
    public SupplyOrderService(SupplyOrderRepository supplyOrderRepository,
                              DeliveryRepository deliveryRepository,
                              DeliveryNotifier deliveryNotifier,
                              QualityChecker qualityChecker,
                              ForecastingPort forecastingPort) {
        this.supplyOrderRepository = supplyOrderRepository;
        this.deliveryRepository = deliveryRepository;
        this.deliveryNotifier = deliveryNotifier;
        this.qualityChecker = qualityChecker;
        this.forecastingPort = forecastingPort;
    }
    @Override
    public SupplyOrder createOrder(List<Product> products) {
        SupplyOrder newOrder = new SupplyOrder(new SupplyOrderId(), products);
        supplyOrderRepository.save(newOrder);
        return newOrder;
    }
    @Override
    public SupplyOrder createOrderFromForecast() {
        Forecast forecast = forecastingPort.generateForecast();
        List<Product> products = new ArrayList<>();
        forecast.getPredictedQuantities().forEach((productId, quantity) ->
                products.add(new Product(productId, productId, quantity)));
        SupplyOrder newOrder = new SupplyOrder(new SupplyOrderId(), products);
        supplyOrderRepository.save(newOrder);
        return newOrder;
    }
    @Override
    public void confirmOrder(SupplyOrderId id) {
        SupplyOrder order = supplyOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден."));
        order.confirm();
        supplyOrderRepository.save(order);
    }
    @Override
    public void sendOrder(SupplyOrderId id) {
        SupplyOrder order = supplyOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден."));
        order.markAsSent();
        deliveryNotifier.notifySupplier(order);
        supplyOrderRepository.save(order);
    }
    @Override
    public void receiveDelivery(Delivery delivery) {
        delivery.checkQuality(qualityChecker);
        SupplyOrder order = supplyOrderRepository.findById(delivery.getSupplyOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Связанный заказ не найден."));
        if (delivery.getQualityResult() == QualityCheckResult.PASSED) {
            order.markAsDelivered();
        } else {
            order.cancel();
        }
        supplyOrderRepository.save(order);
        deliveryRepository.save(delivery);
    }
    @Override
    public OrderStatus getOrderStatus(SupplyOrderId id) {
        return supplyOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден."))
                .getStatus();
    }
    @Override
    public SupplyOrder getOrder(SupplyOrderId id) {
        return supplyOrderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден."));
    }
    @Override
    public Optional<SupplyOrder> findOrderById(SupplyOrderId id) {
        return supplyOrderRepository.findById(id);
    }
    @Override
    public Optional<Delivery> findDeliveryByOrderId(SupplyOrderId id) {
        return deliveryRepository.findBySupplyOrderId(id);
    }
}