package main.by.bsuir.server.service.impl;

import main.by.bsuir.server.bean.User;
import main.by.bsuir.server.dao.exception.DAOException;
import main.by.bsuir.server.dao.factory.DAOFactory;
import main.by.bsuir.server.dao.UserDAO;
import main.by.bsuir.server.service.UserService;
import main.by.bsuir.server.service.exception.ServiceException;
import main.by.bsuir.server.service.factory.ServiceFactory;

public class UserServiceImlp implements UserService {
    @Override
    public void auth(String login, String password) throws ServiceException {
        // check parameters
        if (login == null || login.equals(" "))
            throw new ServiceException("Incorrect login");
        if (password == null || password.equals(" "))
            throw new ServiceException("Incorrect password");

        // realize functionality user login in system
        DAOFactory daoFactory = DAOFactory.getInstance();
        UserDAO userDAO = daoFactory.getUserDAO();

        try {
            ServiceFactory service = ServiceFactory.getInstance();
            User activeUser = service.getActiveUser();
            if (activeUser != null)
                throw new ServiceException("The previous session is not over");
            activeUser = userDAO.auth(login, password);
            service.setActiveUser(activeUser);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void quit() throws ServiceException {
        ServiceFactory service = ServiceFactory.getInstance();
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            User activeUser = service.getActiveUser();
            if (activeUser == null)
                throw new ServiceException("The session was not started");
            userDAO.quit(activeUser);
            service.setActiveUser(null);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void registration(User user) throws ServiceException {

        ServiceFactory service = ServiceFactory.getInstance();
        if (service.getActiveUser().getRights() != User.Rights.ROOT)
            throw new ServiceException("Insufficient rights to create a user");

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        try {
            userDAO.createUser(user);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
