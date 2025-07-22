/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package restaurant_management_system;

/**
 *
 * @author User
 */
public class product {
    private String productId;
    private String name;
    private String type;
    private Double price;
    private Integer quantity;
    
    //public product(String productId,String name,String type,Double price,Integer quantity){
        public product(String productId, String productName, String type, double price, int quantity) {
    // assign values


        this.productId =productId;
        this.name = name;
        this. type = type;
        this.price = price;
        this.quantity=quantity;
    }
    
    public String getProductId(){
        return productId;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public Double getPrice() {
        return price;
    }
    public Integer getQuantity() {
        return quantity;
    }
}
