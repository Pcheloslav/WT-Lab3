package main.by.bsuir.server.service.factory;

import main.by.bsuir.server.bean.User;
import main.by.bsuir.server.service.StudentService;
import main.by.bsuir.server.service.UserService;
import main.by.bsuir.server.service.impl.StudentServiceImlp;
import main.by.bsuir.server.service.impl.UserServiceImlp;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private User activeUser = null;
    private final UserService userService = new UserServiceImlp();
    private final StudentService studentService = new StudentServiceImlp();

    private ServiceFactory(){};

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService(){
        return userService;
    }

    public StudentService getStudentService(){
        return studentService;
    }

    public void setActiveUser(User user){
        this.activeUser = user;
    }

    public User getActiveUser(){
        return activeUser;
    }
}
