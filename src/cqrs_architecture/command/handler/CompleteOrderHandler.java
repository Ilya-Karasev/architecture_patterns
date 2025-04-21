package cqrs_architecture.command.handler;

import cqrs_architecture.command.command.CompleteOrderCommand;
import cqrs_architecture.command.model.CustomerOrder;
import cqrs_architecture.command.repository.OrderRepository;

/**
 * Обработчик команды завершения заказа.
 */
public class CompleteOrderHandler implements CommandHandler<CompleteOrderCommand> {
    private final OrderRepository orderRepository;
    public CompleteOrderHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public void handle(CompleteOrderCommand command) {
        CustomerOrder order = orderRepository.findById(command.getOrderId());
        order.completeOrder();
        orderRepository.save(order);
    }
}
