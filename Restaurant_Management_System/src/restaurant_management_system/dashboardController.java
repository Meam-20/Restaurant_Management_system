/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant_management_system;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author User
 */
public class dashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button availableFD_addBtn;

    @FXML
    private Button availableFD_btn;

    @FXML
    private Button availableFD_clearBtn;

    @FXML
    private TableColumn<?, ?> availableFD_col_price;

    @FXML
    private TableColumn<?, ?> availableFD_col_productID;

    @FXML
    private TableColumn<?, ?> availableFD_col_productName;

    @FXML
    private TableColumn<?, ?> availableFD_col_status;

    @FXML
    private TableColumn<?, ?> availableFD_col_type;

    @FXML
    private Button availableFD_deleteBtn;

    @FXML
    private AnchorPane availableFD_form;

    @FXML
    private TextField availableFD_productID;

    @FXML
    private TextField availableFD_productName;

    @FXML
    private TextField availableFD_productPrice;

    @FXML
    private ComboBox<?> availableFD_productStatus;

    @FXML
    private ComboBox<?> availableFD_productType;

    @FXML
    private TextField availableFD_search;

    @FXML
    private Button availableFD_updatBtn;

    @FXML
    private Button close;

    @FXML
    private BarChart<?, ?> dashboard_ICChart;

    @FXML
    private Label dashboard_NC;

    @FXML
    private BarChart<?, ?> dashboard_NOChart;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label dashboard_TIncome;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashborad_form;

    @FXML
    private Button logout;

    @FXML
    private Button minimize;

    @FXML
    private Button order_addBtn;

    @FXML
    private TextField order_amount;

    @FXML
    private Button order_btn;

    @FXML
    private TableColumn<?, ?> order_col_price;

    @FXML
    private TableColumn<?, ?> order_col_productID;

    @FXML
    private TableColumn<?, ?> order_col_productName;

    @FXML
    private TableColumn<?, ?> order_col_quantity;

    @FXML
    private TableColumn<?, ?> order_col_type;

    @FXML
    private AnchorPane order_form;

    @FXML
    private Button order_payBtn;

    @FXML
    private ComboBox<?> order_productID;

    @FXML
    private ComboBox<?> order_productName;

    @FXML
    private Spinner<?> order_quantity;

    @FXML
    private Button order_receiptBtn;

    @FXML
    private Button order_removeBtn;

    @FXML
    private TableView<?> order_tableview;

    @FXML
    private Label order_total;

    @FXML
    private Label username;

    //LETS GIVE THEM BEHAVIORS
    private double x = 0;
    private double y = 0;
    
    //AVAILABLE FOODS/DRINKS
    private String[] status ={"Available", "Not available"};
    
    public void availableFDStatus(){
        List<String> listStatus = new ArrayList<>();
        
        for(String data:status){
            listStatus.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listStatus);
        availableFD_productStatus.setItems(listData);
        
    }        

    @FXML
    public void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                // Perform logout logic here
                
                logout.getScene().getWindow().hide();
                
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.initStyle(StageStyle.TRANSPARENT);

                
                root.setOnMousePressed((MouseEvent event) -> {
                 x = event.getSceneX();
                 y = event.getSceneY();
               });

               root.setOnMouseDragged((MouseEvent event) -> {
                   stage.setX(event.getScreenX() - x);
                   stage.setY(event.getScreenY() - y);
                   stage.setOpacity(0.8);
               });

               root.setOnMouseReleased((MouseEvent event) -> {
                   stage.setOpacity(1);
               });

                       stage.setScene(scene);
                       stage.show();
                   }
        } catch (Exception e)
        {
            e.printStackTrace();
                    }

        }


    

    public void displayUsername() {
        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);
        username.setText(user);
    }

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        displayUsername();
        availableFDStatus();

    }

}

