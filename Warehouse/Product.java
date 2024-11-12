import java.io.*;
import java.util.*;

public class Product implements Serializable
{
    private String productName;
    private String productId;
    private int quantity;
    private double price;
    private Waitlist waitlist = new Waitlist();

    // Constructor
    public Product(String productName, String productId, int quantity, double price)
    {
        this.productName = productName;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public void setWaitlist(Iterator<WaitlistClient> list) {
        waitlist = new Waitlist(list);
    }

    public Iterator<WaitlistClient> getWaitlist() {
        return waitlist.getWaitlist();
    }

    public boolean addToWaitlist(String clientId, int quantity) {
        return waitlist.addClient(clientId, quantity);
    }

    // Getter for productName
    public String getProductName()
    {
        return productName;
    }

    // Setter for productName
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    // Getter for productId
    public String getProductId()
    {
        return productId;
    }

    // Setter for productId
    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    // Getter for quantity
    public int getQuantity()
    {
        return quantity;
    }

    // Setter for quantity
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    // Getter for price
    public double getPrice()
    {
        return price;
    }

    // Setter for price
    public void setPrice(double price)
    {
        this.price = price;
    }

    // Method to calculate total value
    public double calculateTotalValue()
    {
        return quantity * price;
    }

    @Override
    public String toString() 
    {
        return "Product Name: " + productName + 
        ", Product Id: " + productId + 
        ", Product Price: " + price + 
        ", Product Quantity: " + quantity;
    }

    
    public boolean equals(String id)
    {
        return productId == id;
    }
}