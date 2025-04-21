package cqrs_architecture.command.command;

import cqrs_architecture.command.model.OrderStatus;

import java.util.UUID;

public class ChangeOrderStatusCommand implements Command {
    private final String commandId;
    private final String orderId;
    private final OrderStatus newStatus;
    public ChangeOrderStatusCommand(String orderId, OrderStatus newStatus) {
        this.commandId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.newStatus = newStatus;
    }
    @Override
    public String getCommandId() {
        return commandId;
    }
    public String getOrderId() {
        return orderId;
    }
    public OrderStatus getNewStatus() {
        return newStatus;
    }
}
