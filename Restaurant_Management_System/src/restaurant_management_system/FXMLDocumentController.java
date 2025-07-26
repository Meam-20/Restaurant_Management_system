package restaurant_management_system;


import java.net.URL;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
   // private FontAwesomeIcon close;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    private double x=0;
    private double y=0;
    

    @FXML
    public void login() {
        String user = username.getText();
        String pass = password.getText();

        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, user);
            prepare.setString(2, pass);
            result = prepare.executeQuery();
            
            Alert alert;

                if (username.getText().isEmpty() || password.getText().isEmpty()) {
             alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error Message");
             alert.setHeaderText(null);
             alert.setContentText("Please fill in all fields.");
             alert.showAndWait();
             return;
         }else

                if (result.next()) {
                    
                 data.username = username.getText();   
                
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setHeaderText(null);
                alert.setContentText("Welcome, " + user + "!");
                alert.showAndWait();
                
                //to hide your login from
                loginbtn.getScene().getWindow().hide();

                // Link your Dashboard FXML
               


                Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                
                
                
               
                             
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                
                root.setOnMousePressed((MouseEvent event) ->{
                    x=event.getSceneX();
                    y=event.getSceneY();
                });
                root.setOnMouseDragged((MouseEvent event) ->{
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenX() - y);
                    
                });
                
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();

                // Close login window
                Stage currentStage = (Stage) loginbtn.getScene().getWindow();
                currentStage.close();

            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password.");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CLOSE THE PROGRAM
    @FXML
    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization code if needed
    }

    // Unused close method with event
    private void close(ActionEvent event) {
        System.exit(0);
    }
}
