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
public class BookManager {
    private ArrayList<Books> booksArray = new ArrayList<>();
    
    public void BookParser () {
        final String readPath = "C:\\Users\\fomichdenis\\Desktop\\Project\\Books.xml";
        try {	
            File inputFile = new File(readPath);
            DocumentBuilderFactory dbFactory 
               = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("Book");
            int bookId = 0;
            int authorId = 0;
            int size = 0;
            int date = 0;
            String name = "";
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    bookId = new Integer(eElement
                      .getElementsByTagName("bookId")
                      .item(0)
                      .getTextContent());
                    name = eElement
                      .getElementsByTagName("bookName")
                      .item(0)
                      .getTextContent();
                    authorId = new Integer(eElement
                      .getElementsByTagName("authorId")
                      .item(0)
                      .getTextContent());
                    size = new Integer(eElement
                      .getElementsByTagName("size")
                      .item(0)
                      .getTextContent());
                   date = new Integer(eElement
                      .getElementsByTagName("date")
                      .item(0)
                      .getTextContent());
                }
                this.AddBook(bookId, name, authorId, size, date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        
    }
    

    public ArrayList<Books> getBooksArray() {
        return booksArray;
    }
    
    public void AddBook (int bookId, String name, int authorId, int size, int date){
        Books newBook = new Books();
        newBook.setAuthorId(authorId);
        newBook.setBookId(bookId);
        newBook.setBookName(name);
        newBook.setSize(size);
        newBook.setDate(date);
        this.booksArray.add(newBook);
    }
    
    public void modifyBook (int oldBookId, int newBookId, String name, int authorId, int size, int date){
        int i;
        for (i = 0; i < this.booksArray.size(); i++) {
            if (this.booksArray.get(i).getBookId() == oldBookId) {
                break;
            }
        }
        this.booksArray.get(i).setAuthorId(authorId);
        this.booksArray.get(i).setBookId(newBookId);
        this.booksArray.get(i).setBookName(name);
        this.booksArray.get(i).setSize(size);
        this.booksArray.get(i).setDate(date);
    }
    
    public void deleteBook (int bookId) {
        for (Iterator i = this.booksArray.iterator(); i.hasNext();  ) {
            Books book = (Books)i.next();
            if (book.getBookId() == bookId) {
                i.remove();
            }
        }
    }
    
    public static void printBookToConsole (Books book) {
        System.out.println("BookId: " + book.getBookId());
        System.out.println("AutorId: " + book.getAuthorId());
        System.out.println("Name: " + book.getBookName());
        System.out.println("Size: " + book.getSize());
        System.out.println("Date: " + book.getDate() + "\n");
    }
    
    public void printBooksToXml() {
        final String writePath = "C:\\Users\\fomichdenis\\Desktop\\Project\\newBooks.xml";
        try{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document doc = factory.newDocumentBuilder().newDocument();
        
        Element root = doc.createElement("Books");
        doc.appendChild(root);
        for (int i = 0; i < this.booksArray.size(); i++) {
            
            Element book = doc.createElement("Book");
            root.appendChild(book);

            Element bookId = doc.createElement("bookId");
            bookId.appendChild(doc.createTextNode(Integer.toString(this.booksArray.get(i).getBookId())));
            book.appendChild(bookId);

            Element bookName = doc.createElement("bookName");
            bookName.appendChild(doc.createTextNode(this.booksArray.get(i).getBookName()));
            book.appendChild(bookName);

            Element authorId = doc.createElement("authorId");
            authorId.appendChild(doc.createTextNode(Integer.toString(this.booksArray.get(i).getAuthorId())));
            book.appendChild(authorId);

            Element size = doc.createElement("size");
            size.appendChild(doc.createTextNode(Integer.toString(this.booksArray.get(i).getSize())));
            book.appendChild(size);

            Element date = doc.createElement("date");
            date.appendChild(doc.createTextNode(Integer.toString(this.booksArray.get(i).getDate())));
            book.appendChild(date);
        }
        File file = new File(writePath);

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(file));
        
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    

   

    public void setBooksArray(ArrayList<Books> booksArray) {
        this.booksArray = booksArray;
    }
}
