package main.by.bsuir.server.controller.command.impl;

import main.by.bsuir.server.controller.Controller;
import main.by.bsuir.server.controller.command.Command;
import main.by.bsuir.server.controller.exception.ControllerException;
import main.by.bsuir.server.service.exception.ServiceException;
import main.by.bsuir.server.service.factory.ServiceFactory;

public class Quit implements Command {
    @Override
    public String execute(String request) throws ControllerException {
        try{
            ServiceFactory.getInstance().getUserService().quit();
        } catch (ServiceException e){
            throw new ControllerException(e.getMessage());
        }
        return "QUIT success";
    }
}
