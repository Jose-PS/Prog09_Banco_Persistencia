/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package Vista;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

/**
 *
 * @author DAW
 */
public class MainFX extends Application {
    private Stage mainStage;
    private SplitPane mainPane;
    
    @Override
    public void start(Stage mainStage) {
        this.mainStage=mainStage;
        this.mainStage.setTitle("Banco do Hio");
        
        
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainFX.class.getResource("/Vista/MainVista.fxml"));
            mainPane=(SplitPane) loader.load();
            Scene scene=new Scene(mainPane);
            mainStage.setScene(scene);
            mainStage.show();
            
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
