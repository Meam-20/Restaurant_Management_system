/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant_management_system;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.net.URL;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.Pane;
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
    private ComboBox<String> availableFD_productType;

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
    
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    
    public void availableFDAdd(){
        String sql = "INSERT INTO catagory (product_id,product_name,type,price,status)"
                +"VALUES(?,?,?,?,?)";
            connect = database.connectDb();
            
            try{
                
                prepare.setString(1, availableFD_productID.getText());
                prepare.setString(2, availableFD_productName.getText());
                prepare.setString(3, (String) availableFD_productType.getSelectionModel().getSelectedItem());
                prepare.setString(4, availableFD_productPrice.getText());
                prepare.setString(5, (String) availableFD_productStatus.getSelectionModel().getSelectedItem());
                
                if(availableFD_productID.getText().isEmpty() 
                        ||availableFD_productName.getText().isEmpty()
                        ||availableFD_productType.getSelectionModel() == null
                        ||availableFD_productPrice.getText().isEmpty()
                        || availableFD_productStatus.getSelectionModel() == null){
                    
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank fields");
                    alert.showAndWait();
                }else
                    
                    String checkData = "SELECT product_id FROM catagory WHERE product_id ='"
                            +availableFD_productID.getText()+"'";
                
                            connect = database.connectDb();
                            statement = connect.createStatement();
                            result = statement.executeQuery(checkData);
                            
                            if(result.next()){
                                    alert = new Alert(AlertType.ERROR);
                                    alert.setTitle("Error Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Product ID :" + availableFD_productID.getText() +"is already exists!" );
                                    alert.showAndWait();
                                
                            }else{


                            prepare.executeQuery();
                            
                            alert = new Alert(AlertType.IMFORMATION);
                                    alert.setTitle("Information Message");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Successfully Added!" );
                                    alert.showAndWait();
                                    
                            }
                

            }catch(exception e)(e.printStackTrace());
    }
            
            
    
    public ObservableList<catagories> availableFDListData(){
        ObservableList<catagories> listData = FXCollections.observableArrayList();
        
        String sql = "SELECT FROM catagory";
         connect = database.connectDb();
         try{
             prepare = connect.prepareStatement(sql);
             result = statement.executeQuery();
             catagories cat;
             
             while(result.next()){
                 cat = new catagories(result.getString("product_id"))
                         , (result.getString("product_name")
                         ,(result.getstring("type"),(result.getstring("price"),
                         (result.getstring("status");
                         
                     listData.add(cat);    
             }
             
         }catch(exception e){e.printStackTrace();
    }
    return listData;
    }
     public ObservableList<catagories> availableFDList;
     public void availableFDShowData(){
         availableFDList = availableFDListData();
         availableFD_col_productID.setCellValueFactory (new propertyValueFactory<>("productId"));
         availableFD_col_productName.setCellValueFactory (new propertyValueFactory<>("name"));
         availableFD_col_type.setCellValueFactory (new propertyValueFactory<>("type"));
         availableFD_col_price.setCellValueFactory (new propertyValueFactory<>("price"));
         availableFD_col_status.setCellValueFactory (new propertyValueFactory<>("status"));
         
         
     }
    
    //AVAILABLE food/drinks
    private String[] categories = {"Meals", "Drinks"};

   
    public void availableFDtype() {
        List<String> listCat = new ArrayList<>();

        for (String data : categories) {
            listCat.add(data);
        }

        ObservableList<String> listdata = FXCollections.observableArrayList(listCat);
        availableFD_productType.setItems(listdata);
    }

    
    
    
    
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
public void swichForm(ActionEvent event) {
    if (event.getSource() == dashboard_btn) {
        dashborad_form.setVisible(true);
        availableFD_form.setVisible(false);
        order_form.setVisible(false);

        dashboard_btn.setStyle("-fx-background-color:#3796a7;-fx-text-fill:#fff;-fx-border-width: 0px;");
        availableFD_btn.setStyle("-fx-background-color:transparent;-fx-border-width: 1px;-fx-text-fill:#000;");
        order_btn.setStyle("-fx-background-color:transparent;-fx-border-width: 1px;-fx-text-fill:#000;");

    } else if (event.getSource() == availableFD_btn) {
        dashborad_form.setVisible(false);
        availableFD_form.setVisible(true);
        order_form.setVisible(false);

        availableFD_btn.setStyle("-fx-background-color:#3796a7;-fx-text-fill:#fff;-fx-border-width: 0px;");
        dashboard_btn.setStyle("-fx-background-color:transparent;-fx-border-width: 1px;-fx-text-fill:#000;");
        order_btn.setStyle("-fx-background-color:transparent;-fx-border-width: 1px;-fx-text-fill:#000;");

    } else if (event.getSource() == order_btn) {
        dashborad_form.setVisible(false);
        availableFD_form.setVisible(false);
        order_form.setVisible(true);

        order_btn.setStyle("-fx-background-color:#3796a7;-fx-text-fill:#fff;-fx-border-width: 0px;");
        dashboard_btn.setStyle("-fx-background-color:transparent;-fx-border-width: 1px;-fx-text-fill:#000;");
        availableFD_btn.setStyle("-fx-background-color:transparent;-fx-border-width: 1px;-fx-text-fill:#000;");
    }
}

    //LETS GIVE THEM BEHAVIORS
    private double x = 0;
    private double y = 0;

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
        availableFDtype();

    }

}

