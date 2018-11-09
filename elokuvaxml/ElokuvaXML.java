/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elokuvaxml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jesse
 */
public class ElokuvaXML extends Application{

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        launch(ElokuvaXML.class);
    }
    private boolean eka = true;
    private ArrayList<String> lista = new ArrayList<>();
    @Override
    public void start(Stage ikkuna) throws Exception {
        lista.add("<root>");
        VBox box = new VBox();
        TextField kentta1 = new TextField();
        TextField kentta2 = new TextField();
        TextField kentta3 = new TextField();
        TextField kentta4 = new TextField();
        TextField kentta5 = new TextField();
        box.getChildren().add(new Label("Title: "));
        box.getChildren().add(kentta1);
        box.getChildren().add(new Label("Kesto: "));
        box.getChildren().add(kentta2);
        box.getChildren().add(new Label("Paikka: "));
        box.getChildren().add(kentta3);
        box.getChildren().add(new Label("Kuvan sijainti: "));
        box.getChildren().add(kentta4);
        box.getChildren().add(new Label("Kuvaus: "));
        box.getChildren().add(kentta5);
        TextField kentta6 = new TextField();
        box.getChildren().add(new Label("Laji: "));
        box.getChildren().add(kentta6);
        box.setPadding(new Insets(10, 50, 50, 50));
        box.setSpacing(10);
        Button nappi = new Button("Lisää");
        box.getChildren().add(nappi);
        nappi.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                     public void handle(ActionEvent event) {
                         String rivi = "<item><title>" + kentta1.getText() + "</title><kesto>" + kentta2.getText() + "</kesto><paikka>" + kentta3.getText() + "</paikka><kuva>" + kentta4.getText() + "</kuva><kuvaus>" + kentta5.getText() + "</kuvaus><laji>" + kentta6.getText() + "</laji></item>";
                
                    try {
                     File file = new File("Elokuvat.xml");
                        if(!file.exists()){
                          //  file.createNewFile();
                         eka = false;
                         //lista.add("<root>");
                        } else{
                        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                        Document doc = dBuilder.parse(file);
                        doc.getDocumentElement().normalize();
                        //  System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
                        NodeList nList = doc.getElementsByTagName("item");
                        // System.out.println("----------------------------");
         
                        for (int temp = 0; temp < nList.getLength(); temp++) {
                            Node nNode = nList.item(temp);
                            //  System.out.println("\nCurrent Element :" + nNode.getNodeName());
            
                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement = (Element) nNode;
               
                                String nimi = eElement
                                    .getElementsByTagName("title")
                                    .item(0)
                                    .getTextContent();
                                String kesto = eElement
                                        .getElementsByTagName("kesto")
                                        .item(0)
                                        .getTextContent();
                                String paikka = eElement
                                        .getElementsByTagName("paikka")
                                        .item(0)
                                        .getTextContent();
                                String kuva = eElement
                                        .getElementsByTagName("kuva")
                                        .item(0)
                                        .getTextContent();
                                String kuvaus = eElement
                                        .getElementsByTagName("kuvaus")
                                        .item(0)
                                        .getTextContent();
                                String laji = eElement
                                        .getElementsByTagName("laji")
                                        .item(0)
                                        .getTextContent();
                               lista.add("<item><title>" + nimi + "</title><kesto>" + kesto + "</kesto><paikka>" + paikka + "</paikka><kuva>" + kuva + "</kuva><kuvaus>" + kuvaus + "</kuvaus><laji>" + laji + "</laji></item>");
                            }
                        }
                        }
                        lista.add(rivi);
                        
                    } catch(Exception e){
                
                    }
                    }});
        
             
        ikkuna.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent we) {
              lista.add("</root>");
              try{
               File file = new File("Elokuvat.xml");
                if(file.delete()) { 
                  //  System.out.println(file.getName() + " is deleted!");
                } else {
                   //  System.out.println("Delete operation is failed.");
    		}
              BufferedWriter out = new BufferedWriter(new FileWriter("Elokuvat.xml", true));
              for(int kierros=0;kierros<lista.size()-1;kierros++){
                  out.write(lista.get(kierros));
                  out.newLine();
              }
              out.write(lista.get(lista.size()-1));
              out.close();
              } catch(Exception e){
                  
              }
          }
      }); 
        Scene nakyma = new Scene(box);
        ikkuna.setScene(nakyma);
        ikkuna.show();
    }
}
