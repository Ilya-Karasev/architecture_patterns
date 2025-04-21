package cqrs_architecture.command.handler;

import cqrs_architecture.command.command.ChangeOrderStatusCommand;
import cqrs_architecture.command.model.CustomerOrder;
import cqrs_architecture.command.repository.OrderRepository;

public class ChangeOrderStatusHandler implements CommandHandler<ChangeOrderStatusCommand> {
    private final OrderRepository orderRepository;
    public ChangeOrderStatusHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public void handle(ChangeOrderStatusCommand command) {
        CustomerOrder order = orderRepository.findById(command.getOrderId());
        order.changeStatus(command.getNewStatus());
        orderRepository.save(order);
    }
}
