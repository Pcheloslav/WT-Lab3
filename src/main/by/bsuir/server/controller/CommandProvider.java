package main.by.bsuir.server.controller;

import main.by.bsuir.server.controller.command.Command;
import main.by.bsuir.server.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    private enum CommandName {AUTH, QUIT, VIEW, ADD, DELETE,  REGISTER, WRONG_REQUEST};
    private final Map<CommandName, Command> repository = new HashMap<>();

    public CommandProvider(){
        repository.put(CommandName.AUTH, new Auth());
        repository.put(CommandName.QUIT, new Quit());
        repository.put(CommandName.VIEW, new View());
        repository.put(CommandName.ADD, new Add());
        repository.put(CommandName.DELETE, new Delete());
        repository.put(CommandName.REGISTER, new Register());
        repository.put(CommandName.WRONG_REQUEST, new WrongRequest());
    }

    public Command getCommand(String name){
        CommandName commandName = null;
        Command command = null;

        try{
            commandName = CommandName.valueOf(name.toUpperCase());
            command = repository.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e){
            command = repository.get(CommandName.WRONG_REQUEST);
        }
        return command;
    }
}
