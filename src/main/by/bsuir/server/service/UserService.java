package main.by.bsuir.server.service;

import main.by.bsuir.server.bean.User;
import main.by.bsuir.server.service.exception.ServiceException;

public interface UserService {
    void auth (String login, String password) throws ServiceException;
    void quit () throws ServiceException;
    void registration(User user) throws ServiceException;
}
