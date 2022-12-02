package main.by.bsuir.server.controller.command.impl;

import main.by.bsuir.server.bean.User;
import main.by.bsuir.server.controller.CommandProvider;
import main.by.bsuir.server.controller.command.Command;
import main.by.bsuir.server.controller.exception.ControllerException;
import main.by.bsuir.server.controller.Controller;
import main.by.bsuir.server.service.exception.ServiceException;
import main.by.bsuir.server.service.factory.ServiceFactory;

public class Register implements Command {
    @Override
    public String execute(String request) throws ControllerException {
        String[] arguments = request.split(Controller.getDelimiter());
        if (arguments.length != 4)
            throw new ControllerException("CREATE command should contains 3 arguments");
        try {
            User.Rights rights = User.Rights.valueOf(arguments[3].toUpperCase());
            ServiceFactory.getInstance().getUserService().registration(new User(arguments[1], arguments[2], rights));
        } catch (ServiceException e) {
            throw new ControllerException(e.getMessage());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ControllerException("Incorrect rights value");
        }

        return "REGISTER user success";
    }
}
