package main.by.bsuir.server.dao.impl;

import main.by.bsuir.server.bean.Student;
import main.by.bsuir.server.dao.StudentDAO;
import main.by.bsuir.server.dao.exception.DAOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutionException;

public class XMLStudentDAO implements StudentDAO {

    private static final String STUDENT_PATH = "./src/resources/students.xml";

    @Override
    public void addStudent(Student student) throws DAOException {
        try {
            Document doc = getDoc();
            Element root = doc.getDocumentElement();
            Element eStudent = doc.createElement("student");
            Element eFirstName = doc.createElement("firstname");
            eFirstName.setTextContent(student.getFirstName());
            Element eLastName = doc.createElement("lastname");
            eLastName.setTextContent(student.getLastName());
            eStudent.appendChild(eFirstName);
            eStudent.appendChild(eLastName);
            root.appendChild(eStudent);
            root.normalize();

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(STUDENT_PATH)));

        } catch (Exception e) {
            throw new DAOException("Can not create a user");
        }

    }

    @Override
    public void deleteStudent(Student student) throws DAOException {
        boolean find = false;
        Document doc;
        try {
            doc = getDoc();
            Element root = doc.getDocumentElement();
            NodeList nList = doc.getElementsByTagName("student");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String eFirstName = eElement.getElementsByTagName("firstname").item(0).getTextContent();
                    String eLastName = eElement.getElementsByTagName("lastname").item(0).getTextContent();
                    if (eFirstName.equals(student.getFirstName()) && eLastName.equals(student.getLastName())) {
                        root.removeChild(eElement);
                        root.normalize();
                        find = true;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new DAOException("Can not get a student DB");
        }

        if (!find)
            throw new DAOException("Can not find a student");

        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(STUDENT_PATH)));
        } catch (Exception e) {
            throw new DAOException("Can not delete a student");
        }
    }

    @Override
    public StringBuilder viewStudents() throws DAOException {
        StringBuilder response = new StringBuilder("");

        try {
            Document doc = getDoc();
            NodeList nList = doc.getElementsByTagName("student");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String eFirstName = eElement.getElementsByTagName("firstname").item(0).getTextContent();
                    String eLastName = eElement.getElementsByTagName("lastname").item(0).getTextContent();
                    response.append(eFirstName).append(" ").append(eLastName).append("\r\n");
                }
            }
        } catch (Exception e) {
            throw new DAOException("Can not get list of students");
        }

        return response;
    }

    private Document getDoc() throws DAOException {
        Document doc;
        try {
            File DBFile = new File(STUDENT_PATH);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(DBFile);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            throw new DAOException();
        }
        return doc;
    }
}
