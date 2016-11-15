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
import library.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author fomichdenis
 */
public class AuthorManager {
    private ArrayList<Authors> authorsArray = new ArrayList<>();
    /*private ArrayList<Genres> Genres = new ArrayList<>();
    private ArrayList<BooksGenres> booksGenresArray = new ArrayList<>();
    private ArrayList<Users> usersArray = new ArrayList<>();
    private ArrayList<BooksUsers> booksUsersArray = new ArrayList<>();
    private ArrayList<Awards> awardsArray = new ArrayList<>();
    private ArrayList<BooksAwards> booksAwardsArray = new ArrayList<>();*/
    
    
    public void setAuthorsArray(ArrayList<Authors> authorsArray) {
        this.authorsArray = authorsArray;
    }
    public void AuthorParser () {
        final String readPath = "C:\\Users\\fomichdenis\\Desktop\\Project\\Authors.xml";
        try {	
            File inputFile = new File(readPath);
            DocumentBuilderFactory dbFactory 
               = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Author");
            int authorId = 0;
            int yearBirthday = 0;
            String name = "";
            String Surname = "";
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    authorId = new Integer(eElement
                      .getElementsByTagName("authorId")
                      .item(0)
                      .getTextContent());
                    name = eElement
                      .getElementsByTagName("authorName")
                      .item(0)
                      .getTextContent();
                    Surname = eElement
                      .getElementsByTagName("authorSurname")
                      .item(0)
                      .getTextContent();
                    yearBirthday = new Integer(eElement
                      .getElementsByTagName("yearBirthday")
                      .item(0)
                      .getTextContent());
                }
                this.AddAuthor(authorId, name, Surname, yearBirthday);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }    
    }
    public void AddAuthor (int authorId, String name, String surname , int year ){
        Authors newAuthor = new Authors();
        newAuthor.setAuthorId(authorId);
        newAuthor.setAuthorName(name);
        newAuthor.setAuthorSurname(surname);
        newAuthor.setYearBirthday(year);
        this.authorsArray.add(newAuthor);
    }
    
    public void modifyAuthor (int oldAuthorId, int newAuthorId, String name, String surname, int year){
        int i;
        for (i = 0; i < this.authorsArray.size(); i++) {
            if (this.authorsArray.get(i).getAuthorId() == oldAuthorId) {
                break;
            }
        }
        this.authorsArray.get(i).setAuthorId(newAuthorId);
        this.authorsArray.get(i).setAuthorName(name);
        this.authorsArray.get(i).setAuthorSurname(surname);
        this.authorsArray.get(i).setYearBirthday(year);

    }
    
    public void deleteAuthor (int authorId) {
        for (Iterator i = this.authorsArray.iterator(); i.hasNext();  ) {
            Authors author = (Authors)i.next();
            if (author.getAuthorId() == authorId) {
                i.remove();
            }
        }
    }
    public void printAuthorsToXml() {
        final String writePath = "C:\\Users\\fomichdenis\\Desktop\\Project\\newAuthors.xml";
        try{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document doc = factory.newDocumentBuilder().newDocument();
        
        Element root = doc.createElement("Authors");
        doc.appendChild(root);
        for (int i = 0; i < this.authorsArray.size(); i++) {
            
            Element book = doc.createElement("Author");
            root.appendChild(book);

            Element authorId = doc.createElement("authorId");
            authorId.appendChild(doc.createTextNode(Integer.toString(this.authorsArray.get(i).getAuthorId())));
            book.appendChild(authorId);

            Element authorName = doc.createElement("authorName");
            authorName.appendChild(doc.createTextNode(this.authorsArray.get(i).getAuthorName()));
            book.appendChild(authorName);

            Element authorSurname = doc.createElement("authorSurname");
            authorSurname.appendChild(doc.createTextNode(this.authorsArray.get(i).getAuthorSurname()));
            book.appendChild(authorSurname);

            Element yearBirthday = doc.createElement("yearBirthday");
            yearBirthday.appendChild(doc.createTextNode(Integer.toString(this.authorsArray.get(i).getYearBirthday())));
            book.appendChild(yearBirthday);
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
