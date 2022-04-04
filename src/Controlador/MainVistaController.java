/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author DAW
 */
public class MainVistaController implements Initializable {

    @FXML
    private Pane menu;
    @FXML
    private Pane pantalla;
    @FXML
    private TextFlow text;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Text inicio=new Text("Benvidxs a Banco do Hio!");
        text.getChildren().add(inicio);
    }    

    @FXML
    private void abreConta(ActionEvent event) {
    }

    @FXML
    private void listaContas(ActionEvent event) {
    }

    @FXML
    private void exportaTxt(ActionEvent event) {
    }

    @FXML
    private void datosConta(ActionEvent event) {
    }

    @FXML
    private void ingresaSaldo(ActionEvent event) {
    }

    @FXML
    private void retiraSaldo(ActionEvent event) {
    }

    @FXML
    private void consultaSaldo(ActionEvent event) {
    }

    @FXML
    private void eliminaConta(ActionEvent event) {
    }

    @FXML
    private void sair(ActionEvent event) {
        try {
            Principal.Principal.save();   
        } catch (IOException ex) {
            Logger.getLogger(MainVistaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.exit(0);
    }
    
}
