package cqrs_architecture.command.command;

import java.util.UUID;

public class CreateOrderCommand implements Command {
    private final String commandId;
    private final String customerName;
    public CreateOrderCommand(String customerName) {
        this.commandId = UUID.randomUUID().toString();
        this.customerName = customerName;
    }
    @Override
    public String getCommandId() {
        return commandId;
    }
    public String getCustomerName() {
        return customerName;
    }
}

