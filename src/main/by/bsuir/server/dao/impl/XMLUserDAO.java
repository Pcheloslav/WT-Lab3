package main.by.bsuir.server.dao.impl;

import main.by.bsuir.server.bean.User;
import main.by.bsuir.server.dao.UserDAO;
import main.by.bsuir.server.dao.exception.DAOException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.views.DocumentView;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class XMLUserDAO implements UserDAO {

    private static final String USER_PATH = "./src/resources/users.xml";

    @Override
    public User auth(String login, String password) throws DAOException {
        String rights = null;
        try {
            Document doc = getDoc();
            NodeList nList = doc.getElementsByTagName("user");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String eLogin = eElement.getElementsByTagName("login").item(0).getTextContent();
                    String ePassword = eElement.getElementsByTagName("password").item(0).getTextContent();
                    if (login.equals(eLogin) && password.equals(ePassword)) {
                        rights = eElement.getElementsByTagName("rights").item(0).getTextContent();
                        break;
                    }
                }
            }
            if (rights == null)
                throw new DAOException("Can not find user");
        } catch (Exception e) {
            throw new DAOException("Can not get user DB");
        }

        User.Rights uRights;
        try {
            uRights = User.Rights.valueOf(rights.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new DAOException("Illegal rights");
        }
        return new User(login, password, uRights);
    }

    @Override
    public void createUser(User user) throws DAOException {
        try {
            Document doc = getDoc();
            Element root = doc.getDocumentElement();
            Element eUser = doc.createElement("user");
            Element eLogin = doc.createElement("login");
            eLogin.setTextContent(user.getLogin());
            Element ePassword = doc.createElement("password");
            ePassword.setTextContent(user.getPassword());
            Element eRights = doc.createElement("rights");
            eRights.setTextContent(user.getRights().toString().toUpperCase(Locale.ROOT));
            eUser.appendChild(eLogin);
            eUser.appendChild(ePassword);
            eUser.appendChild(eRights);
            root.appendChild(eUser);
            root.normalize();

            Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(USER_PATH)));

        } catch (Exception e) {
            throw new DAOException("Can not create a user");
        }
    }

    @Override
    public void quit(User user) throws DAOException {
        try{
            Document doc = getDoc();
            NodeList nList = doc.getElementsByTagName("user");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String eLogin = eElement.getElementsByTagName("login").item(0).getTextContent();
                    String ePassword = eElement.getElementsByTagName("password").item(0).getTextContent();
                    String eRights =  eElement.getElementsByTagName("rights").item(0).getTextContent();
                    if (user.getLogin().equals(eLogin) && user.getPassword().equals(ePassword) && user.getRights().toString().toUpperCase(Locale.ROOT).equals(eRights)) {
                        return;
                    }
                }
            }
        } catch (Exception e){
            throw new DAOException("Can not find a user");
        }
    }

    private Document getDoc() throws DAOException {
        Document doc;
        try {
            File DBFile = new File(USER_PATH);
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
