package cqrs_architecture.command.handler;

import cqrs_architecture.command.command.UpdateOrderItemCommand;
import cqrs_architecture.command.model.CustomerOrder;
import cqrs_architecture.command.repository.OrderRepository;

/**
 * Обработчик команды изменения количества блюда в заказе.
 */
public class UpdateOrderItemHandler implements CommandHandler<UpdateOrderItemCommand> {
    private final OrderRepository orderRepository;
    public UpdateOrderItemHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public void handle(UpdateOrderItemCommand command) {
        CustomerOrder order = orderRepository.findById(command.getOrderId());
        order.updateDishQuantity(command.getDishName(), command.getNewQuantity());
        orderRepository.save(order);
    }
}
