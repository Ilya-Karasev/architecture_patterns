package cqrs_architecture;

import cqrs_architecture.api.console.ConsoleInterface;
import cqrs_architecture.api.facade.Facade;
import cqrs_architecture.command.command.CreateOrderCommand;
import cqrs_architecture.command.handler.*;
import cqrs_architecture.command.repository.OrderRepository;
import cqrs_architecture.common.event.EventBus;
import cqrs_architecture.query.dto.OrderDTO;
import cqrs_architecture.query.repository.OrderViewRepository;
import cqrs_architecture.query.service.OrderQueryService;
import cqrs_architecture.query.service.EventHandler;

import java.util.List;

public class Main_CQRS {
    public static void main(String[] args) {
        OrderRepository commandRepository = new OrderRepository();
        OrderViewRepository orderViewRepository = new OrderViewRepository();
        EventHandler eventHandler = new EventHandler(orderViewRepository);
        EventBus.getInstance().register(eventHandler);
        CommandBus commandBus = new CommandBus();
        commandBus.register(CreateOrderCommand.class, new CreateOrderHandler(commandRepository));
        commandBus.register(cqrs_architecture.command.command.AddDishToOrderCommand.class, new AddDishToOrderHandler(commandRepository));
        commandBus.register(cqrs_architecture.command.command.UpdateOrderItemCommand.class, new UpdateOrderItemHandler(commandRepository));
        commandBus.register(cqrs_architecture.command.command.ChangeOrderStatusCommand.class, new ChangeOrderStatusHandler(commandRepository));
        commandBus.register(cqrs_architecture.command.command.CompleteOrderCommand.class, new CompleteOrderHandler(commandRepository));
        OrderQueryService queryService = new OrderQueryService(orderViewRepository);
        Facade facade = new Facade(commandBus, queryService);
        facade.createOrder("Иванов Иван");
        List<OrderDTO> orders = facade.getAllOrders();
        String orderId = orders.get(0).getId();
        System.out.println("Создан заказ с ID: " + orderId);
        facade.addDish(orderId, "Кофе 'Эспрессо'", 2);
        facade.addDish(orderId, "Стейк из мраморной говядины", 1);
        ConsoleInterface consoleInterface = new ConsoleInterface(facade);
        consoleInterface.start();
    }
}
