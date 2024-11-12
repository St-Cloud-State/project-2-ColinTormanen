import java.io.Serializable;
import java.util.*;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientId;
    private String clientName;
    private static final String CLIENT_STRING = "C";
    private Wishlist wishlist;
    private List<String> invoices;
    private double creditBalance;
    private double debitBalance;

    // Constructor to create a Client object with a name
    public Client(String clientName) {
        this.clientName = clientName;
        this.clientId = CLIENT_STRING + ClientIdServer.instance().getId(); // Generating unique client ID
        this.wishlist = new Wishlist();
        this.invoices = new LinkedList<String>();
        this.creditBalance = 0;
        this.debitBalance = 0;
    }

    // Getter for client name
    public String getClientName() {
        return clientName;
    }

    // Getter for client ID
    public String getClientId() {
        return clientId;
    }

    // Setter for client name
    public void setClientName(String newClientName) {
        this.clientName = newClientName;
    }

    // Equals method to compare clients by ID
    public boolean equals(String id) {
        return this.clientId.equals(id);
    }

    @Override
    public String toString() {
        return "Client Name: " + clientName + ", Client ID: " + clientId + ", Cedit Balance: "
            + creditBalance + ", Debit Balance: " + debitBalance;
    }

    public boolean addToWishlist(Product product) {
        return wishlist.insertProduct(product);
    }

    public boolean removeFromWishlist(String productId) {
        return wishlist.removeProduct(productId);
    }

    public Iterator<Product> getWishlist() {
        return wishlist.getProducts();
    }

    public void clearWishlist() {
        this.wishlist = new Wishlist();
    }

    public boolean addInvoice(String invoice) {
        invoices.add(invoice);
        return true;
    }

    public Iterator<String> getInvoices() {
        return invoices.iterator();
    }

    public void addCredit(double credit) {
        if(debitBalance > credit) debitBalance = debitBalance - credit;
        else {
            debitBalance = 0;
            creditBalance = credit + creditBalance - debitBalance;
        }
    }

    public void addDebit(double debit) {
        if(creditBalance > debit) creditBalance = creditBalance - debit;
        else {
            creditBalance = 0;
            debitBalance = debit + debitBalance - creditBalance;
        }
    }
}