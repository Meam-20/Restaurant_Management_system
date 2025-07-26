package restaurant_management_system;

/**
 * @author User
 */
public class product {
    private Integer id;
    private String productId;
    private String name;
    private String type;
    private Double price;
    private Integer quantity;
    
    // Fixed constructor
    public product(Integer id, String productId, String productName, String type, Double price, Integer quantity) {
        this.id = id;
        this.productId = productId;
        this.name = productName;  // ✅ FIXED: Now correctly assigning productName to this.name
        this.type = type;
        this.price = price;
        this.quantity = quantity;
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getProductId() {
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