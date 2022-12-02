package main.by.bsuir.server.controller.command.impl;

import main.by.bsuir.server.controller.command.Command;
import main.by.bsuir.server.controller.exception.ControllerException;
import main.by.bsuir.server.service.exception.ServiceException;
import main.by.bsuir.server.service.factory.ServiceFactory;

public class View implements Command {
    @Override
    public String execute(String request) throws ControllerException {
        String response = "empty";
        try{
            response = ServiceFactory.getInstance().getStudentService().viewStudents();
        } catch (ServiceException e){
            throw new ControllerException(e.getMessage());
        }
        return response;
    }
}
