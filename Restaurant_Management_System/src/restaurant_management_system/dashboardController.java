/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant_management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.net.URL;
import java.util.ArrayList;
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
import java.sql.ResultSet;
import java.util.Date;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.SpinnerValueFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 *
 * @author User
 */
public class dashboardController implements Initializable {

    // Existing FXML fields (unchanged)
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
    private AreaChart<?, ?> dashboard_ICChart;
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
    @FXML
    private Label order_balance;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    // Existing variables (unchanged)
    private double total1p = 0;
    private double amount = 0;
    private double balance = 0;
    private int customerId;
    private int item;
    private ObservableList<product> orderData;
    private ObservableList<catagories> availableFDList;
    private int qty;
    private SpinnerValueFactory<Integer> spinner;
    private double x = 0;
    private double y = 0;

    // Existing methods (unchanged, only showing relevant ones for context)
    public void orderTotal() {
        String sql = "SELECT SUM(price) FROM product WHERE customer_id = " + customerId;
        connect = (Connection) database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) {
                total1p = result.getDouble("SUM(price)");
            }
            
            orderDisplayTotal();
            
            if (!order_amount.getText().isEmpty()) {
                calculateBalance();
            } else {
                order_balance.setText("$0.00");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateBalance() {
        Alert alert;
        
        if (order_amount.getText().isEmpty() || order_amount.getText() == null || order_amount.getText().equals("")) {
            alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please type the amount!");
            alert.showAndWait();
            order_balance.setText("$0.00");
        } else {
            try {
                amount = Double.parseDouble(order_amount.getText());
                
                if (amount < total1p) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Amount must be greater than or equal to total ($" + String.format("%.2f", total1p) + ")!");
                    alert.showAndWait();
                    order_amount.setText("");
                    order_balance.setText("$0.00");
                } else {
                    balance = (amount - total1p);
                    order_balance.setText("$" + String.format("%.2f", balance));
                }
            } catch (NumberFormatException e) {
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid number!");
                alert.showAndWait();
                order_amount.setText("");
                order_balance.setText("$0.00");
            }
        }
    }

    public ObservableList<product> orderListData() {
        orderCustomerId();
        ObservableList<product> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM product WHERE customer_id = " + customerId;
        connect = (Connection) database.connectDb();
        
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            product prod;
            while (result.next()) {
                prod = new product(
                        result.getInt("id"),
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

    // Modified method to generate receipt and show in new window
    @FXML
    public void order_receiptBtn() {
        // Check if payment has been made (total1p > 0 and balance >= 0)
        if (total1p <= 0 || balance < 0 || order_amount.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please complete payment first!");
            alert.showAndWait();
            return;
        }

        try {
            // Fetch order details
            ObservableList<product> orderItems = orderListData();
            if (orderItems.isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("No items to generate receipt for!");
                alert.showAndWait();
                return;
            }

            // Format current date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDate = dateFormat.format(new Date());

            // Build receipt content
            StringBuilder itemsContent = new StringBuilder();
            for (product item : orderItems) {
                double itemTotal = item.getPrice();
                itemsContent.append(String.format("%-10s %-16s %-6d $%-7.2f $%.2f\n",
                        item.getProductId(),
                        item.getName(),
                        item.getQuantity(),
                        item.getPrice() / item.getQuantity(), // Price per unit
                        itemTotal));
            }

            // Create receipt content
            String receiptContent = String.format(
                "RECEIPT\n" +
                "--------------------------------\n" +
                "Restaurant Management System\n" +
                "Date: %s\n" +
                "Customer ID: %d\n" +
                "--------------------------------\n" +
                "Item ID    Item Name        Qty    Price    Total\n" +
                "%s" +
                "--------------------------------\n" +
                "Total Amount: $%.2f\n" +
                "Amount Paid: $%.2f\n" +
                "Balance: $%.2f\n" +
                "--------------------------------\n" +
                "Thank you for your purchase!\n",
                currentDate, customerId, itemsContent.toString(), total1p, amount, balance);

            // Write to file
            String fileName = "receipt_" + customerId + "_" + System.currentTimeMillis() + ".txt";
            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write(receiptContent);
            }

            // Create new window to display receipt
            Stage receiptStage = new Stage();
            receiptStage.setTitle("Receipt - Customer " + customerId);
            receiptStage.initStyle(StageStyle.UTILITY);

            // Create TextArea to display receipt
            TextArea receiptTextArea = new TextArea(receiptContent);
            receiptTextArea.setEditable(false);
            receiptTextArea.setWrapText(true);
            receiptTextArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12;");

            // Create layout for the window
            VBox receiptLayout = new VBox(10);
            receiptLayout.getChildren().add(receiptTextArea);
            receiptLayout.setStyle("-fx-padding: 10;");

            // Create scene and set it to stage
            Scene receiptScene = new Scene(receiptLayout, 400, 500);
            receiptStage.setScene(receiptScene);

            // Make window draggable
            receiptLayout.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });
            receiptLayout.setOnMouseDragged((MouseEvent event) -> {
                receiptStage.setX(event.getScreenX() - x);
                receiptStage.setY(event.getScreenY() - y);
            });

            // Show the window
            receiptStage.show();

            // Show success message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Receipt generated and saved as: " + fileName);
            alert.showAndWait();

        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Failed to generate receipt: " + e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    // Existing methods (unchanged)
    public void dashboard_NC() {
        String sql = "SELECT COUNT(id) FROM product_info";
        int nc = 0;
        connect = database.connectDb();
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            
            if (result.next()) {
                nc = result.getInt("COUNT(id)");
            }
            dashboard_NC.setText(String.valueOf(nc));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboard_TI() {
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
            dashboard_TI.setText("$" + String.format("%.2f", ti));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboard_TIncome() {
        String sql = "SELECT SUM(total) FROM product_info";
        connect = (Connection) database.connectDb();
        double ti = 0;
        try {
            statement = connect.createStatement();
            result = statement.executeQuery(sql);
            if (result.next()) {
                ti = result.getDouble("SUM(total)");
            }
            dashboard_TIncome.setText("$" + String.format("%.2f", ti));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboard_NOChart() {
        try {
            dashboard_NOChart.getData().clear();
            String sql = "SELECT date, COUNT(id) FROM product_info GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 5";
            connect = (Connection) database.connectDb();
            XYChart.Series chart = new XYChart.Series();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            dashboard_NOChart.getData().add(chart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dashboard_ICChart() {
        dashboard_ICChart.getData().clear();
        String sql = "SELECT date, SUM(total) FROM product_info GROUP BY date ORDER BY TIMESTAMP(total) ASC LIMIT 7";
        connect = (Connection) database.connectDb();
        try {
            XYChart.Series chart = new XYChart.Series();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                chart.getData().add(new XYChart.Data(result.getString(1), result.getDouble(2)));
            }
            dashboard_ICChart.getData().add(chart);
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
                    availableFDShowData();
                    availableFDclear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void availableFDUpdate() {
        String sql = "UPDATE catagory SET product_name = '" + availableFD_productName.getText()
                + "', type = '" + availableFD_productType.getSelectionModel().getSelectedItem()
                + "', price = '" + availableFD_productPrice.getText()
                + "', status = '" + availableFD_productStatus.getSelectionModel().getSelectedItem()
                + "' WHERE product_id = '" + availableFD_productID.getText() + "'";
        
        try {
            Statement statement = connect.createStatement();
            Alert alert;
            if (availableFD_productID.getText().isEmpty()
                    || availableFD_productName.getText().isEmpty()
                    || availableFD_productType.getSelectionModel().getSelectedItem() == null
                    || availableFD_productPrice.getText().isEmpty()
                    || availableFD_productStatus.getSelectionModel().getSelectedItem() == null) {
                
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE product_ID:"
                        + availableFD_productID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if (option.get().equals(ButtonType.OK)) {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated:");
                    alert.showAndWait();
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    
                    availableFDShowData();
                    availableFDclear();
                    
                } else {
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
    public void availableFDDelete() {
        String sql = "DELETE FROM catagory WHERE product_id = '"
                + availableFD_productID.getText() + "'";
        connect = (Connection) database.connectDb();
        
        try {
            Alert alert;
            if (availableFD_productID.getText().isEmpty()
                    || availableFD_productName.getText().isEmpty()
                    || availableFD_productType.getSelectionModel().getSelectedItem() == null
                    || availableFD_productPrice.getText().isEmpty()
                    || availableFD_productStatus.getSelectionModel().getSelectedItem() == null) {
                
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE product_ID:"
                        + availableFD_productID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if (option.get().equals(ButtonType.OK)) {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted:");
                    alert.showAndWait();
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    
                    availableFDShowData();
                    availableFDclear();
                    
                } else {
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
    public void availableFDclear() {
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
                } else if (predicateCatagories.getPrice().toString().contains(searchKey)) {
                    return true;
                } else if (predicateCatagories.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<catagories> sortedList = new SortedList<>(filter);
        sortedList.comparatorProperty().bind(availableFD_tableView.comparatorProperty());
        availableFD_tableView.setItems(sortedList);
    }

    public void availableFDShowData() {
        availableFDList = availableFDListData();
        availableFD_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        availableFD_col_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        availableFD_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        availableFD_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableFD_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        availableFD_tableView.setItems(availableFDList);
    }

    @FXML
    public void availableFDSelect() {
        catagories catData = availableFD_tableView.getSelectionModel().getSelectedItem();
        
        int num = availableFD_tableView.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        }
        availableFD_productID.setText(catData.getProductId());
        availableFD_productName.setText(catData.getName());
        availableFD_productPrice.setText(String.valueOf(catData.getPrice()));
    }

    private String[] categories = {"Meals", "Drinks"};

    public void availableFDtype() {
        List<String> listCat = new ArrayList<>();
        for (String data : categories) {
            listCat.add(data);
        }
        ObservableList<String> listdata = FXCollections.observableArrayList(listCat);
        availableFD_productType.setItems(listdata);
    }

    private String[] status = {"Available", "Not available"};

    public void availableFDStatus() {
        List<String> listStatus = new ArrayList<>();
        
        for (String data : status) {
            listStatus.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listStatus);
        availableFD_productStatus.setItems(listData);
    }

    @FXML
    public void orderAdd() {
        orderCustomerId();
        
        String insertSql = "INSERT INTO product "
                + "(customer_id,product_id,product_name,type,price,quantity,date) "
                + "VALUES(?,?,?,?,?,?,?)";
        
        connect = (Connection) database.connectDb();
        
        try {
            String orderType = "";
            double orderPrice = 0;
            
            if (order_productID.getSelectionModel().getSelectedItem() == null
                    || order_productName.getSelectionModel().getSelectedItem() == null
                    || qty <= 0) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select product and quantity!");
                alert.showAndWait();
                return;
            }
            
            String checkData = "SELECT * FROM catagory WHERE product_id = ?";
            prepare = connect.prepareStatement(checkData);
            prepare.setString(1, (String) order_productID.getSelectionModel().getSelectedItem());
            result = prepare.executeQuery();
            
            if (result.next()) {
                orderType = result.getString("type");
                orderPrice = result.getDouble("price");
            }
            
            prepare = connect.prepareStatement(insertSql);
            prepare.setString(1, String.valueOf(customerId));
            prepare.setString(2, (String) order_productID.getSelectionModel().getSelectedItem());
            prepare.setString(3, (String) order_productName.getSelectionModel().getSelectedItem());
            prepare.setString(4, orderType);
            
            double totalPrice = orderPrice * qty;
            prepare.setString(5, String.valueOf(totalPrice));
            prepare.setString(6, String.valueOf(qty));
            
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            prepare.setString(7, String.valueOf(sqlDate));
            
            prepare.executeUpdate();
            
            orderTotal();
            orderDisplayData();
            
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Item added successfully!");
            alert.showAndWait();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void order_payBtn() {
        String sql = "INSERT INTO product_info (customer_id, total, date) VALUES(?,?,?)";
        connect = (Connection) database.connectDb();
        try {
            Alert alert;
            
            if (balance < 0 || total1p <= 0 || order_amount.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid payment amount or no items to pay for!");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("CONFIRMATION Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to process payment?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, String.valueOf(customerId));
                    prepare.setString(2, String.valueOf(total1p));
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(3, String.valueOf(sqlDate));
                    prepare.executeUpdate();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Payment Successful!");
                    alert.showAndWait();
                    
                    clearOrderForm();
                    orderDisplayData();
                    
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Payment Cancelled!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void orderDisplayTotal() {
        order_total.setText("$" + String.format("%.2f", total1p));
    }

    @FXML
    public void order_amount() {
        calculateBalance();
    }

    @FXML
    public void order_removeBtn() {
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
            } else {
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
                    orderTotal();
                    
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void orderSelectData() {
        product prod = order_tableview.getSelectionModel().getSelectedItem();
        int num = order_tableview.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        }
        item = prod.getId();
    }

    public void orderDisplayData() {
        orderData = orderListData();
        order_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        order_col_productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        order_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        order_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        order_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        order_tableview.setItems(orderData);
    }

    public void orderCustomerId() {
        String sql = "SELECT customer_id FROM product";
        connect = (Connection) database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            ObservableList listData = FXCollections.observableArrayList();
            while (result.next()) {
                customerId = result.getInt("customer_id");
            }
            String checkData = "SELECT customer_id FROM product_info";
            statement = connect.createStatement();
            result = statement.executeQuery(checkData);
            
            int customerInfoId = 0;
            
            while (result.next()) {
                customerInfoId = result.getInt("customer_id");
            }
            
            if (customerId == 0) {
                customerId += 1;
            } else if (customerId == customerInfoId) {
                customerId += 1;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void orderProductId() {
        String sql = "SELECT product_id FROM catagory WHERE STATUS = 'Available' ";
        connect = (Connection) database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            ObservableList listData = FXCollections.observableArrayList();
            while (result.next()) {
                listData.add(result.getString("product_id"));
            }
            
            order_productID.setItems(listData);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void oderProductName() {
        String sql = "SELECT product_name FROM catagory WHERE product_id = '"
                + order_productID.getSelectionModel().getSelectedItem() + "'";
        connect = (Connection) database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            ObservableList listData = FXCollections.observableArrayList();
            while (result.next()) {
                listData.add(result.getString("product_name"));
            }
            
            order_productName.setItems(listData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void orderSpinner() {
        spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 50, 0);
        order_quantity.setValueFactory(spinner);
    }

    @FXML
    public void orderQuantity() {
        qty = order_quantity.getValue();
        System.out.println(qty);
    }

    public void clearOrderForm() {
        order_amount.setText("");
        order_balance.setText("$0.00");
        order_total.setText("$0.00");
        if (order_productID.getSelectionModel() != null) {
            order_productID.getSelectionModel().clearSelection();
        }
        if (order_productName.getSelectionModel() != null) {
            order_productName.getSelectionModel().clearSelection();
        }
        if (order_quantity.getValueFactory() != null) {
            order_quantity.getValueFactory().setValue(0);
        }
        total1p = 0;
        amount = 0;
        balance = 0;
        qty = 0;
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
            dashboard_NOChart();
            dashboard_ICChart();
            
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
            orderSpinner();
            orderDisplayData();
            orderTotal();
            
            order_amount.setText("");
            order_balance.setText("$0.00");
        }
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
        } catch (Exception e) {
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
        
        dashboard_NOChart();
        dashboard_ICChart();
        displayUsername();
        availableFDStatus();
        availableFDtype();
        availableFDShowData();
        orderProductId();
        orderSpinner();
        orderDisplayData();
        orderDisplayTotal();
    }
}