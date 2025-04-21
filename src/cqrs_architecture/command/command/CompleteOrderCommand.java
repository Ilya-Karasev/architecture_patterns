package cqrs_architecture.command.command;

import java.util.UUID;

public class CompleteOrderCommand implements Command {
    private final String commandId;
    private final String orderId;
    public CompleteOrderCommand(String orderId) {
        this.commandId = UUID.randomUUID().toString();
        this.orderId = orderId;
    }
    @Override
    public String getCommandId() {
        return commandId;
    }
    public String getOrderId() {
        return orderId;
    }
}
