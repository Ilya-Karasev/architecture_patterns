package cqrs_architecture.command.handler;

import cqrs_architecture.command.command.AddDishToOrderCommand;
import cqrs_architecture.command.model.CustomerOrder;
import cqrs_architecture.command.repository.OrderRepository;

public class AddDishToOrderHandler implements CommandHandler<AddDishToOrderCommand> {
    private final OrderRepository orderRepository;
    public AddDishToOrderHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public void handle(AddDishToOrderCommand command) {
        CustomerOrder order = orderRepository.findById(command.getOrderId());
        order.addDish(command.getDishName(), command.getQuantity());
        orderRepository.save(order);
    }
}
