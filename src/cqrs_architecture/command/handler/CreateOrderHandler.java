package cqrs_architecture.command.handler;

import cqrs_architecture.command.command.CreateOrderCommand;
import cqrs_architecture.command.model.CustomerOrder;
import cqrs_architecture.command.repository.OrderRepository;

/**
 * Обработчик команды создания нового заказа клиента.
 */
public class CreateOrderHandler implements CommandHandler<CreateOrderCommand> {
    private final OrderRepository orderRepository;
    public CreateOrderHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public void handle(CreateOrderCommand command) {
        CustomerOrder order = new CustomerOrder(command.getCustomerName());
        orderRepository.save(order);
    }
}
