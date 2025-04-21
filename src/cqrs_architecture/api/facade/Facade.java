package cqrs_architecture.api.facade;

import cqrs_architecture.command.command.*;
import cqrs_architecture.command.handler.CommandBus;
import cqrs_architecture.query.dto.OrderDTO;
import cqrs_architecture.query.dto.OrderStatisticsDTO;
import cqrs_architecture.query.service.OrderQueryService;

import java.util.List;

public class Facade {
    private final CommandBus commandBus;
    private final OrderQueryService queryService;
    public Facade(CommandBus commandBus, OrderQueryService queryService) {
        this.commandBus = commandBus;
        this.queryService = queryService;
    }
    public void createOrder(String customerName) {
        commandBus.dispatch(new CreateOrderCommand(customerName));
    }
    public void addDish(String orderId, String dishName, int quantity) {
        commandBus.dispatch(new AddDishToOrderCommand(orderId, dishName, quantity));
    }
    public void updateDish(String orderId, String dishName, int newQuantity) {
        commandBus.dispatch(new UpdateOrderItemCommand(orderId, dishName, newQuantity));
    }
    public void changeOrderStatus(String orderId, cqrs_architecture.command.model.OrderStatus newStatus) {
        commandBus.dispatch(new ChangeOrderStatusCommand(orderId, newStatus));
    }
    public void completeOrder(String orderId) {
        commandBus.dispatch(new CompleteOrderCommand(orderId));
    }
    public OrderDTO getOrder(String orderId) {
        return queryService.getOrderById(orderId);
    }
    public List<OrderDTO> getAllOrders() {
        return queryService.getAllOrders();
    }
    public OrderStatisticsDTO getOrderStatistics(String orderId) {
        return queryService.getOrderStatistics(orderId);
    }
}
