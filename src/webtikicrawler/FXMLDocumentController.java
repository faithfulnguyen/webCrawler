/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webtikicrawler;

import com.mongodb.DBCursor;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import webtikicrawler.LayerOne;

/**
 *
 * @author Trung_Tin
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField txd;
    @FXML
    private TextField host;
    @FXML
    private TextField port;
    
    @FXML
    private Label lb1;
    @FXML
    private Label lb2;
    
    @FXML 
    private Button scn1Btn1;
   
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        try{
            LayerOne lyo = new LayerOne();
            String text = txd.getText();
            lyo.getAllLink(text);
            lyo.LayerTwoToDo();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
