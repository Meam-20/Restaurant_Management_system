/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant_management_system;

import com.mysql.jdbc.Connection;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.ResultSet;
import java.util.Date;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.SpinnerValueFactory;

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
    private TableView<catagories> availableFD_tableView;

    @FXML
    private TableColumn<catagories, String> availableFD_col_price;

    @FXML
    private TableColumn<catagories, String> availableFD_col_productID;

    @FXML
    private TableColumn<catagories, String> availableFD_col_productName;

    @FXML
    private TableColumn<catagories, String> availableFD_col_status;

    @FXML
    private TableColumn<catagories, String> availableFD_col_type;

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
    private TableColumn<product, String> order_col_price;

    @FXML
    private TableColumn<product, String> order_col_productID;

    @FXML
    private TableColumn<product, String> order_col_productName;

    @FXML
    private TableColumn<product, String> order_col_quantity;

    @FXML
    private TableColumn<product, String> order_col_type;

    @FXML
    private AnchorPane order_form;

    @FXML
    private Button order_payBtn;

    @FXML
    private ComboBox<?> order_productID;

    @FXML
    private ComboBox<?> order_productName;

    @FXML
    private Spinner<Integer> order_quantity;

    @FXML
    private Button order_receiptBtn;

    @FXML
    private Button order_removeBtn;

    @FXML
    private TableView<product> order_tableview;

    @FXML
    private Label order_total;

    @FXML
    private Label username;
    
//  private ResultSet result;
   private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    @FXML
    private Label order_balance;
    
    
    public void dashboard_NC(){
         String sql = "SELECT COUNT(id) FROM product_info";

        int nc = 0;

        connect = (Connection) database.connectDb();

        try {
            
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            
            if(result.next()){
                 nc = result.getInt("COUNT(id)");
            }
              dashboard_NC.setText(String.valueOf(nc));
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void dashboard_TI(){
         Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "SELECT SUM(total) FROM product_info WHERE date = '" + sqlDate + "'";

        connect = (Connection) database.connectDb();
        double ti = 0;

        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }

            dashboard_TI.setText("$" + String.valueOf(ti));

        } catch (Exception e) {
            e.printStackTrace();
        }
         
    }
   
    
    public void dashboard_TIncome(){
        
         String sql = "SELECT SUM(total) FROM product_info";

           connect = (Connection) database.connectDb();

        double ti = 0;

        try {

            statement = connect.createStatement();
            result = statement.executeQuery(sql);

            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }
            dashboard_TIncome.setText("$" + String.valueOf(ti));
        } catch (Exception e) {
            e.printStackTrace();
        }

        
    }
    
    @FXML
       public void availableFDAdd() {

        String sql = "INSERT INTO catagory (product_id, product_name, type, price, status) "
                + "VALUES(?,?,?,?,?)";

          connect = (Connection) database.connectDb();

        try {
           prepare = connect.prepareStatement(sql);
           
            prepare.setString(1, availableFD_productID.getText());
            prepare.setString(2, availableFD_productName.getText());
            prepare.setString(3, (String) availableFD_productType.getSelectionModel().getSelectedItem());
            prepare.setString(4, availableFD_productPrice.getText());
            prepare.setString(5, (String) availableFD_productStatus.getSelectionModel().getSelectedItem());
 
            Alert alert;

            if (availableFD_productID.getText().isEmpty()
                    || availableFD_productName.getText().isEmpty()
                    || availableFD_productType.getSelectionModel() == null
                    || availableFD_productPrice.getText().isEmpty()
                    || availableFD_productStatus.getSelectionModel() == null) {

                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {

                String checkData = "SELECT product_id FROM catagory WHERE product_id = '"
                        + availableFD_productID.getText() + "'";

                 connect = (Connection) database.connectDb();

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product ID: " + availableFD_productID.getText() + " is already exist!");
                    alert.showAndWait();
                } else {
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    // TO SHOW THE DATA
                    //availableFDShowData();
                    // TO CLEAR THE FIELDS
                     //availableFDClear();

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
       
       
    
    @FXML
    public void availableFDUpdate(){
       String sql = "UPDATE catagory SET product_name = '" + availableFD_productName.getText() +
                 "', type = '" + availableFD_productType.getSelectionModel().getSelectedItem() +
                 "', price = '" + availableFD_productPrice.getText() +
                 "', status = '" + availableFD_productStatus.getSelectionModel().getSelectedItem() +
                 "' WHERE product_id = '" + availableFD_productID.getText() + "'";
                //connect = database.connectDb();
        try{
             Statement statement = connect.createStatement();
             Alert alert;
            if (availableFD_productID.getText().isEmpty()
                || availableFD_productName.getText().isEmpty()
                || availableFD_productType.getSelectionModel().getSelectedItem() == null
                || availableFD_productPrice.getText().isEmpty()
                || availableFD_productStatus.getSelectionModel().getSelectedItem() == null){
                     alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank fields");
                    alert.showAndWait();
             }else{
                 alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to UPDATE product_ID:"
                    +availableFD_productID.getText() + "?");
                    Optional<ButtonType> option = alert.showAndWait();
                    
                    if(option.get().equals(ButtonType.OK)){
                            alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully Updated:");
                            alert.showAndWait();
                       statement = connect.createStatement();
                       statement.executeUpdate(sql);
                       
                                 // TO SHOW THE DATA
                                availableFDShowData();
                                // TO CLEAR THE FIELDS
                                  //availableFDClear();
                       
                    }
                    else{
                            alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Cancelled.");
                            alert.showAndWait();
                            
                                 
                    }
             }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void availableFDDelete(){
        String sql = "DELETE FROM catagory WHERE product_id = '" 
                + availableFD_productName.getText() +"'";
         connect = (Connection) database.connectDb();
         
         try{
             Alert alert;
             if (availableFD_productID.getText().isEmpty()
                || availableFD_productName.getText().isEmpty()
                || availableFD_productType.getSelectionModel().getSelectedItem() == null
                || availableFD_productPrice.getText().isEmpty()
                || availableFD_productStatus.getSelectionModel().getSelectedItem() == null){
                     alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank fields");
                    alert.showAndWait();
             }else{
                 alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to DELETE product_ID:"
                    +availableFD_productID.getText() + "?");
                    Optional<ButtonType> option = alert.showAndWait();
                    
                    if(option.get().equals(ButtonType.OK)){
                            alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Successfully Deleted:");
                            alert.showAndWait();
                       statement = connect.createStatement();
                       statement.executeUpdate(sql);
                       
                                 // TO SHOW THE DATA
                                availableFDShowData();
                                // TO CLEAR THE FIELDS
                                  //availableFDClear();
                       
                    }
                    else{
                            alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Cancelled.");
                            alert.showAndWait();
                            
                                 
                    }
             }
                 
                 
             
         }catch (Exception e) {
            e.printStackTrace();
        }
         
         

    }
            
    @FXML
    public void availableFDclear(){
        
         availableFD_productID.setText("");
         availableFD_productName.setText("");
         availableFD_productType.getSelectionModel().clearSelection();
         availableFD_productPrice.setText("");
         availableFD_productStatus.getSelectionModel().clearSelection();
         
    }  
    
      public ObservableList<catagories> availableFDListData() {

        ObservableList<catagories> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM catagory";

         connect = (Connection) database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            catagories cat;

            while (result.next()) {
                cat = new catagories(result.getString("product_id"),
                        result.getString("product_name"), result.getString("type"),
                        result.getDouble("price"), result.getString("status"));

                listData.add(cat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    @FXML
     public void availableFDSearch() {
    FilteredList<catagories> filter = new FilteredList<>(availableFDList, e -> true);

    availableFD_search.textProperty().addListener((observable, oldValue, newValue) -> {
        filter.setPredicate(predicateCatagories -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String searchKey = newValue.toLowerCase();

            if (predicateCatagories.getProductId().toLowerCase().contains(searchKey)) {
                return true;
            } else if (predicateCatagories.getName().toLowerCase().contains(searchKey)) {
                return true;
            } else if (predicateCatagories.getType().toLowerCase().contains(searchKey)) {
                return true;
            } else if (predicateCatagories.getPrice().toString().contains(searchKey)){
                return true;
            } else if(predicateCatagories.getStatus().toLowerCase().contains(searchKey)) {
                return true;
            }else{

            return false;
            }
        });
    });

    SortedList<catagories> sortedList = new SortedList<>(filter);
    sortedList.comparatorProperty().bind(availableFD_tableView.comparatorProperty());
    availableFD_tableView.setItems(sortedList);
}

     public ObservableList<catagories> availableFDList;
     public void availableFDShowData(){
         availableFDList = availableFDListData();
         availableFD_col_productID.setCellValueFactory (new PropertyValueFactory<>("productId"));
         availableFD_col_productName.setCellValueFactory (new PropertyValueFactory<>("product_name"));
         availableFD_col_type.setCellValueFactory (new PropertyValueFactory<>("type"));
         availableFD_col_price.setCellValueFactory (new PropertyValueFactory<>("price"));
         availableFD_col_status.setCellValueFactory (new PropertyValueFactory<>("status"));
         
         availableFD_tableView.setItems(availableFDList);
         
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

    
    @FXML
    public void availableFDSelect(){
        catagories catData = availableFD_tableView.getSelectionModel().getSelectedItem();
        
        int num = availableFD_tableView.getSelectionModel().getSelectedIndex();
        
        if((num - 1)<-1)
        {
            return;
        
        }
    
        availableFD_productID.setText(catData.getProductId());
        availableFD_productName.setText(catData.getName());
         availableFD_productPrice.setText(String.valueOf(catData.getPrice()));
        
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
    public void orderAdd(){
        orderCustomerId();
          orderTotal();
        String sql = "INSERT INTO product "
               + ("customer_id,product_id,product_name,type,price,quantity,date")
                +"VALUES(?,?,?,?,?,?,?)";
        
         connect = (Connection) database.connectDb();
         try{
             
             String orderType="";
             double orderPrice=0;
             
           statement = connect.createStatement();
           // result = statement.executeQuery(checkData);
             result = prepare.executeQuery();
            if(result.next()){
                orderType=result.getString("type");
              orderPrice= result.getDouble("price");
            }
             
             
             String checkData = "SELECT * FROM catagory  WHERE product_id = '"+
                    order_productID.getSelectionModel().getSelectedItem()+"'"; 
             prepare = connect.prepareStatement(sql);
             prepare.setString(1,String.valueOf(customerId));
             prepare.setString(2,(String)order_productID.getSelectionModel().getSelectedItem());
             prepare.setString(3,(String)order_productName.getSelectionModel().getSelectedItem());
              prepare.setString(4,orderType);
              
              double totalPrice = orderPrice *qty;
              prepare.setString(5, String.valueOf(orderPrice));
              prepare.setString(6,String.valueOf(qty));
              
              Date date = new Date();
              java.sql.Date sqlDate = new java.sql.Date(date.getTime());
             // prepare.setDate(1, sqlDate);

             // java.sql.Date sqlDate = new java.sql.Date(date.getTime());

              
              prepare.setString(7,String.valueOf(sqlDate));
              prepare.executeUpdate();
              
              orderTotal();
             orderDisplayTotal();
               orderDisplayData();
        
         }catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    @FXML
    public void order_payBtn(){
        String sql = "INSERT INTO product_info (customer_id, total, date) VALUES(?,?,?)";

        connect = (Connection) database.connectDb();

        try {
             Alert alert;
                if (balance == 0 || String.valueOf(balance) == "$0.0" || String.valueOf(balance) == null
                    ||total1p== 0 || String.valueOf(total1p) == "$0.0" || String.valueOf(total1p) == null) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid :3");
                alert.showAndWait();
            }else{
                    
                    alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMATION Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                alert.showAndWait();
                Optional<ButtonType> option = alert.showAndWait();
                
                if (option.get().equals(ButtonType.OK)){
                    
                       prepare = connect.prepareStatement(sql);
                       prepare.setString(1, String.valueOf(customerId));
                       prepare.setString(2, String.valueOf( total1p ));

                       Date date =new Date();
                       java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                       prepare.setString(3, String.valueOf( sqlDate ));

                       prepare.executeUpdate();
                       
                      
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successful!");
                        alert.showAndWait();

                       order_total.setText("$0.0");
                       order_balance.setText("$0.0");
                       order_amount.setText(" ");  
                }else{
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Cancelled!");
                        alert.showAndWait();
                }
                
                
                }
              

    }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private double total1p = 0;
    public void orderTotal(){
        String sql = "SELECT * SUM(price) FROM product WHERE customer_id = " + customerId;
         connect = (Connection) database.connectDb();

        try {
            prepare=connect.prepareStatement(sql);
            result = prepare.executeQuery(); 
            
          if(result.next()){
              total1p = result.getDouble("SUM(price)");
            }
          orderDisplayTotal();
         
    }catch (Exception e) {
            e.printStackTrace();
        }
    }
        
        private double amount;
        private double balance;
    @FXML
        public void order_amount(){
            Alert alert;
            
            if(order_amount.getText().isEmpty()|| order_amount.getText() == null ||order_amount.getText()==""){
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please type the amount!");
                alert.showAndWait();
            }else{
                amount = Double.parseDouble(order_amount.getText());
                
                if(amount < total1p){
                    order_amount.setText("");
                  
                }else{
                    balance = ( amount-total1p);
                    order_balance.setText("$"+String.valueOf(order_balance));
                }
            }
                    
                    
        }
    
    public void orderDisplayTotal(){
        order_total.setText("$"+String.valueOf(total1p));
        
    }
            public ObservableList<product> orderListdata() {
              orderCustomerId(); // make sure this sets the `customerId` variable

              ObservableList<product> listData = FXCollections.observableArrayList();
              String sql = "SELECT * FROM product WHERE customer_id='" + customerId + "'";

              try {
                  prepare = connect.prepareStatement(sql);
                  result = prepare.executeQuery();

                  product prod;
                  while (result.next()) {
                      prod = new product(result.getInt("id"),
                          result.getString("product_id"),
                          result.getString("product_name"),
                          result.getString("type"),
                          result.getDouble("price"),
                          result.getInt("quantity")
                      );
                      listData.add(prod);
                  }

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     } catch (Exception e) {
                  e.printStackTrace();
              }

              return listData;
          }
            
            //LETS CREATE OUR RECEIPT:
    @FXML
            public void order_removeBtn(){
                
            String sql = "DELETE FROM product WHERE id = " + item;

            connect = (Connection) database.connectDb();

            try {
                Alert alert;
                if (item == 0 || String.valueOf(item) == null || String.valueOf(item) == "") {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the item first");
                alert.showAndWait();
            }else{
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to Remove Item: " + item + "?");
                    Optional<ButtonType> option = alert.showAndWait();
                    
                    if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Removed!");
                    alert.showAndWait();

                    orderDisplayData();
                    orderDisplayTotal();
                    
                    order_total.setText("$0.0");
                    order_amount.setText("");
                    order_balance.setText("$0.0");
                    
                    } else{
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Cancelled!");
                        alert.showAndWait();
                    }
                }
            }catch (Exception e) {
                  e.printStackTrace();
               
                }
              }

            
            
            private int item;
    @FXML
            public void orderSelectData(){
                    product prod =  order_tableview.getSelectionModel().getSelectedItem();
                    int num =  order_tableview.getSelectionModel().getSelectedIndex();

                    if ((num - 1) < -1) {
                        return;
                    }

                    item = prod.getId();
                
            }

        private ObservableList<product> orderData;

      public void orderDisplayData() {
          orderData = orderListdata(); // populate the list from your method

          order_col_productID.setCellValueFactory(new PropertyValueFactory<>("product_id"));
          order_col_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
          order_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
          order_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
          order_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

          // Set the ObservableList to the TableView
          order_tableview.setItems(orderData);
      }

    private int customerId;
    public void orderCustomerId(){
         String sql = "SELECT customer_id FROM product";

        connect = (Connection) database.connectDb();

        try {
            prepare=connect.prepareStatement(sql);
            result = prepare.executeQuery(); 
            ObservableList listData = FXCollections.observableArrayList();
            while(result.next()){
                customerId= result.getInt("customer_id");
               
            }
            String checkData="SELECT customer_id FROM product_info";
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);
            
            int customerInfoId = 0;
            
             while(result.next()){
               customerInfoId= result.getInt("customer_id");
               
            }
            
            if(customerId ==0)
            {
                customerId+= 1;
            }else if(customerId == customerInfoId) {
                customerId+= 1;
            }      
           
        }catch (Exception e) {
            e.printStackTrace();
        }
        
    }
        
    
    
    @FXML
    public void orderProductId(){
      String sql = "SELECT product_id FROM catagory WHERE STATUS = 'Available' ";

        connect = (Connection) database.connectDb();

        try {
            prepare=connect.prepareStatement(sql);
            result = prepare.executeQuery(); 
            ObservableList listData = FXCollections.observableArrayList();
            while(result.next()){
                listData.add(result.getString("product_id"));
               
            }
                    
            order_productID.setItems(listData);
             oderProductName();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
        public void oderProductName(){
            String sql = "SELECT product_name FROM catagory WHERE product_id = '"
                + order_productID.getSelectionModel().getSelectedItem() + "'";
            connect = (Connection) database.connectDb();

        try {
            prepare=connect.prepareStatement(sql);
            result = prepare.executeQuery(); 
            ObservableList listData = FXCollections.observableArrayList();
            while(result.next()){
                listData.add(result.getString("product_name"));
               
            }
                    
            order_productName.setItems(listData);
        }catch (Exception e) {
            e.printStackTrace();
        }
        }
        
        private SpinnerValueFactory<Integer> spinner;
        public void orderSpinner(){
            spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0);
            
            order_quantity.setValueFactory(spinner);
                    
        }
        
           private int qty;
    @FXML
           public void orderQuantity(){
               qty=order_quantity.getValue();
               System.out.println(qty);
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
        
        dashboard_NC();
        dashboard_TI();
        dashboard_TIncome();

    } else if (event.getSource() == availableFD_btn) {
        dashborad_form.setVisible(false);
        availableFD_form.setVisible(true);
        order_form.setVisible(false);

        availableFD_btn.setStyle("-fx-background-color:#3796a7;-fx-text-fill:#fff;-fx-border-width: 0px;");
        dashboard_btn.setStyle("-fx-background-color:transparent;-fx-border-width: 1px;-fx-text-fill:#000;");
        order_btn.setStyle("-fx-background-color:transparent;-fx-border-width: 1px;-fx-text-fill:#000;");
        
        availableFDShowData();
        availableFDSearch();
         

    } else if (event.getSource() == order_btn) {
        dashborad_form.setVisible(false);
        availableFD_form.setVisible(false);
        order_form.setVisible(true);

        order_btn.setStyle("-fx-background-color:#3796a7;-fx-text-fill:#fff;-fx-border-width: 0px;");
        dashboard_btn.setStyle("-fx-background-color:transparent;-fx-border-width: 1px;-fx-text-fill:#000;");
        availableFD_btn.setStyle("-fx-background-color:transparent;-fx-border-width: 1px;-fx-text-fill:#000;");
        orderProductId();
        oderProductName();
        orderSpinner();
       orderDisplayData();
        
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
        
        dashboard_NC();
        dashboard_TI();
        dashboard_TIncome();

        displayUsername();
        availableFDStatus();
        availableFDtype();
        availableFDShowData();
        orderProductId();
        oderProductName();
        orderSpinner();
        orderDisplayData();
        orderDisplayTotal();
      

    }

}
