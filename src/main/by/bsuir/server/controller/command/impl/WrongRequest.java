package main.by.bsuir.server.controller.command.impl;

import main.by.bsuir.server.controller.command.Command;

public class WrongRequest implements Command {
    @Override
    public String execute(String request) {
        return "Wrong request";
    }
}
