package cqrs_architecture.query.service;

import cqrs_architecture.common.event.*;
import cqrs_architecture.query.model.OrderItemView;
import cqrs_architecture.query.model.OrderView;
import cqrs_architecture.query.repository.OrderViewRepository;

public class EventHandler implements EventBus.EventHandler {
    private final OrderViewRepository orderViewRepository;
    public EventHandler(OrderViewRepository orderViewRepository) {
        this.orderViewRepository = orderViewRepository;
    }
    @Override
    public void handle(Event event) {
        if (event instanceof CreateOrderEvent) {
            handleOrderCreated((CreateOrderEvent) event);
        } else if (event instanceof AddDishToOrderEvent) {
            handleDishAdded((AddDishToOrderEvent) event);
        } else if (event instanceof UpdateOrderEvent) {
            handleOrderUpdated((UpdateOrderEvent) event);
        } else if (event instanceof CompleteOrderEvent) {
            handleOrderCompleted((CompleteOrderEvent) event);
        }
    }
    private void handleOrderCreated(CreateOrderEvent event) {
        OrderView orderView = new OrderView(
                event.getOrderId(),
                event.getCustomerName(),
                "CREATED",
                event.getCreatedAt()
        );
        orderViewRepository.save(orderView);
    }
    private void handleDishAdded(AddDishToOrderEvent event) {
        OrderView orderView = orderViewRepository.findById(event.getOrderId());
        double price = getDishPrice(event.getDishName());
        OrderItemView item = new OrderItemView(event.getDishName(), event.getQuantity(), price);
        orderView.addItem(item);
        orderViewRepository.save(orderView);
    }
    private void handleOrderUpdated(UpdateOrderEvent event) {
        OrderView orderView = orderViewRepository.findById(event.getOrderId());
        orderView.setStatus("UPDATED");
        orderViewRepository.save(orderView);
    }
    private void handleOrderCompleted(CompleteOrderEvent event) {
        OrderView orderView = orderViewRepository.findById(event.getOrderId());
        orderView.setStatus("COMPLETED");
        orderViewRepository.save(orderView);
    }
    private double getDishPrice(String dishName) {
        return switch (dishName.toLowerCase()) {
            case "стейк" -> 300.0;
            case "картофель фри" -> 150.0;
            case "салат" -> 250.0;
            case "кофе" -> 100.0;
            default -> 200.0;
        };
    }
}
