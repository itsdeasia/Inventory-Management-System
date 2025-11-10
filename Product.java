import java.util.Date;

public class Product {
    public double cost;
    public String itemName;
    public Date addedDate;
    public ProductCategory category;  // Add this line

    public Product(double cost, String itemName, Date addedDate, ProductCategory category) {
        this.cost = cost;
        this.itemName = itemName;
        this.addedDate = addedDate;
        this.category = category;
    }
    public Date getAddedDate() {
        return addedDate;
    }
    public Product() {
        //default
    }
}
