/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webtikicrawler;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Trung_Tin
 */
public class WebTikiCrawler extends Application {
    
   private Stage primaryStage;
        private BorderPane rootLayout;
        private FXMLDocumentController controller;
   @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        this.primaryStage = primaryStage;
        initRootLayout();
       showChatOverview();
    }

	public void initRootLayout(){
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(WebTikiCrawler.class.getResource("FXMLRootLayer.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch(IOException e){
            e.printStackTrace();
        }
    }
        public void showChatOverview(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation( WebTikiCrawler.class.getResource("FXMLDocument.fxml"));
            AnchorPane chatOverview = (AnchorPane) loader.load();
            rootLayout.setCenter(chatOverview);
            controller = loader.getController();
          } catch( IOException e){
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
