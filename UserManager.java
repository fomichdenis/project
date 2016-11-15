/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import library.Users;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author fomichdenis
 */
public class UserManager {
    private ArrayList<Users> usersArray = new ArrayList<>();
    
    public void UserParser () {
        final String readPath = "C:\\Users\\fomichdenis\\Desktop\\Project\\Users.xml";
        try {	
            File inputFile = new File(readPath);
            DocumentBuilderFactory dbFactory 
               = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("User");
            int userId = 0;
            int authorId = 0;
            int birthday = 0;
            String userName = "";
            String userSurname = "";
            String login = "";
            String email = "";
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    userId = new Integer(eElement
                      .getElementsByTagName("userId")
                      .item(0)
                      .getTextContent());
                    userName = eElement
                      .getElementsByTagName("userName")
                      .item(0)
                      .getTextContent();
                    userSurname = eElement
                      .getElementsByTagName("userSurname")
                      .item(0)
                      .getTextContent();
                    login = eElement
                      .getElementsByTagName("login")
                      .item(0)
                      .getTextContent();
                   email = eElement
                      .getElementsByTagName("email")
                      .item(0)
                      .getTextContent();
                   birthday = new Integer(eElement
                      .getElementsByTagName("birthday")
                      .item(0)
                      .getTextContent());
                }
                this.AddUser(userId, userName, userSurname, login, email, birthday);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
    }

    public ArrayList<Users> getUserArray() {
        return usersArray;
    }

    public void setUserArray(ArrayList<Users> userArray) {
        this.usersArray = userArray;
    }
    

    
    public void AddUser (int userId, String userName, String userSurname, String login, String email, int birthday){
        Users newUser = new Users();
        newUser.setUserId(userId);
        newUser.setUserName(userName);
        newUser.setUserSurname(userSurname);
        newUser.setLogin(login);
        newUser.setEmail(email);
        newUser.setBirthday(birthday);
        this.usersArray.add(newUser);
    }
    
    public void modifyUsers (int oldUserId, int newUserId, String userName, String userSurname, String login, String email, int birtday){
        int i;
        for (i = 0; i < this.usersArray.size(); i++) {
            if (this.usersArray.get(i).getUserId() == oldUserId) {
                break;
            }
        }
        this.usersArray.get(i).setUserId(newUserId);
        this.usersArray.get(i).setUserName(userName);
        this.usersArray.get(i).setUserSurname(userSurname);
        this.usersArray.get(i).setLogin(login);
        this.usersArray.get(i).setEmail(email);
        this.usersArray.get(i).setBirthday(birtday);
    }
    
    public void deleteUser (int userId) {
        for (Iterator i = this.usersArray.iterator(); i.hasNext();  ) {
            Users user = (Users)i.next();
            if (user.getUserId() == userId) {
                i.remove();
            }
        }
    }
    

    
    public void printBooksToXml() {
        final String writePath = "C:\\Users\\fomichdenis\\Desktop\\Project\\newUsers.xml";
        try{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document doc = factory.newDocumentBuilder().newDocument();
        
        Element root = doc.createElement("Users");
        doc.appendChild(root);
        for (int i = 0; i < this.usersArray.size(); i++) {
            
            Element user = doc.createElement("User");
            root.appendChild(user);

            Element userId = doc.createElement("userId");
            userId.appendChild(doc.createTextNode(Integer.toString(this.usersArray.get(i).getUserId())));
            user.appendChild(userId);

            Element userName = doc.createElement("userName");
            userName.appendChild(doc.createTextNode(this.usersArray.get(i).getUserName()));
            user.appendChild(userName);

            Element userSurname = doc.createElement("userSurname");
            userSurname.appendChild(doc.createTextNode(this.usersArray.get(i).getUserSurname()));
            user.appendChild(userSurname);

            Element login = doc.createElement("login");
            login.appendChild(doc.createTextNode(this.usersArray.get(i).getLogin()));
            user.appendChild(login);

            Element email = doc.createElement("email");
            email.appendChild(doc.createTextNode(this.usersArray.get(i).getEmail()));
            user.appendChild(email);
            
            Element birthday = doc.createElement("birthday");
            birthday.appendChild(doc.createTextNode(Integer.toString(this.usersArray.get(i).getBirthday())));
            user.appendChild(birthday);
        }
        File file = new File(writePath);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(file));
        
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
