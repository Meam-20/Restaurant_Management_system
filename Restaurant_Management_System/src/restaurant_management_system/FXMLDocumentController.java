/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package restaurant_management_system;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author User
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane main_form;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginbtn;
    @FXML
    private FontAwesomeIcon close;
    
    
   //CLOSE THE PROGRAM
    @FXML
    public void close(){
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
