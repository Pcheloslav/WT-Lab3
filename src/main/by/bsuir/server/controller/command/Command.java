package main.by.bsuir.server.controller.command;

import main.by.bsuir.server.controller.exception.ControllerException;

public interface Command {
    public String execute (String request) throws ControllerException;
}
