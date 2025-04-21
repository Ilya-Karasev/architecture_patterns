package cqrs_architecture.command.handler;

import cqrs_architecture.command.command.Command;

/**
 * Интерфейс обработчика команд.
 */
public interface CommandHandler<T extends Command> {
    void handle(T command);
}
