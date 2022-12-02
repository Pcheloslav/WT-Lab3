package main.by.bsuir.server.service;

import main.by.bsuir.server.bean.Student;
import main.by.bsuir.server.service.exception.ServiceException;

public interface StudentService {
    void addStudent(Student student) throws ServiceException;
    void deleteStudent(Student student) throws ServiceException;
    String viewStudents() throws ServiceException;

}
