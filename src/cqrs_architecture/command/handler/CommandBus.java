package cqrs_architecture.command.handler;

import cqrs_architecture.command.command.Command;

import java.util.HashMap;
import java.util.Map;

/**
 * Шина команд для маршрутизации команд к соответствующим обработчикам.
 */
public class CommandBus {
    private final Map<Class<? extends Command>, CommandHandler<? extends Command>> handlers = new HashMap<>();
    public <T extends Command> void register(Class<T> commandClass, CommandHandler<T> handler) {
        handlers.put(commandClass, handler);
    }
    @SuppressWarnings("unchecked")
    public <T extends Command> void dispatch(T command) {
        CommandHandler<T> handler = (CommandHandler<T>) handlers.get(command.getClass());
        if (handler == null) {
            throw new IllegalStateException("Нет обработчика для команды " + command.getClass().getName());
        }
        handler.handle(command);
    }
}
