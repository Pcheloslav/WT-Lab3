package main.by.bsuir.server.dao;

import main.by.bsuir.server.bean.Student;
import main.by.bsuir.server.dao.exception.DAOException;

public interface StudentDAO  {
    void addStudent(Student student) throws DAOException;
    void deleteStudent(Student student) throws DAOException;
    StringBuilder viewStudents() throws  DAOException;
}
