import java.io.*;
import java.util.*;

public class Warehouse implements Serializable {

    private static Warehouse warehouse;
    private ProductList pList;
    private ClientList cList;

    // Private constructor
    private Warehouse() {
        pList = ProductList.instance();
        cList = ClientList.instance();
    }

    // Create a static warehouse instance
    public static Warehouse instance() {
        if(warehouse == null) {
            warehouse = new Warehouse();
        }
        return warehouse;
    }


    /**** Client Operations ****/

    // Add a client to the client list
    public boolean addClient(Client client) {
        return cList.insertClient(client);
    }

    // Remove a client from the client list
    public boolean removeClient(String clientId) {
        return cList.removeClient(clientId);
    }

    // Search for a client
    public Client searchForClient(String clientId) {
        return cList.searchClient(clientId);
    }

    // Get client list
    public Iterator<Client> getClients() {
        return cList.getClients();
    }

    // Get client's wishlist
    public Iterator<Product> getClientWishlist(String clientId) {
        Client client = cList.searchClient(clientId);
        if(client == null) return null;
        return client.getWishlist();
    } 

    // Get client's invoices
    public Iterator<String> getClientInvoices(String clientId) {
        Client client = cList.searchClient(clientId);
        if(client == null) return null;
        return client.getInvoices();
    }


    /**** Product Operations ****/

    // Add a product to the product list
    public boolean addProduct(Product product) {
        return pList.insertProduct(product);
    }

    // Search for a product
    public Product searchForProduct(String productId) {
        return pList.searchProduct(productId);
    }

    // Get product list
    public Iterator<Product> getProducts() {
        return pList.getProducts();
    }

    // Get a Product's waitlist
    public Iterator<WaitlistClient> getProductWaitlist(String productId) {
        Product product = pList.searchProduct(productId);
        if(product == null) return null;
        return product.getWaitlist();
    } 


    /**** Warehouse Operations ****/

    // Add a product to a client's wishlist
    public boolean addToWishlist(String clientId, String productId, int quantity) {
        Client client = cList.searchClient(clientId);
        Product product = pList.searchProduct(productId);
        if(client == null || product == null) return false;
        Product productNew = new Product(product.getProductName(), product.getProductId(), quantity, product.getPrice());
        return client.addToWishlist(productNew);
    }

    // Remove a product from a client's wishlist
    public boolean removeFromWishlist(String clientId, String productId) {
        Client client = cList.searchClient(clientId);
        if(client == null) return false;
        return client.removeFromWishlist(productId);
    }

    // Add client to a waitlist
    public boolean addToWaitlist(String clientId, String productId, int quantity) {
        Product product = pList.searchProduct(productId);
        if(product == null) return false;
        return product.addToWaitlist(clientId, quantity);
    }

    // Place a client order
    public boolean placeOrder(String clientId) {
        String invoice = "";
        Client client = cList.searchClient(clientId);
        if(client == null) return false;
        Iterator<Product> wishlist = client.getWishlist();
        int totalCost = 0;
        while(wishlist.hasNext()) {
            Product product = wishlist.next();
            Product listProduct = pList.searchProduct(product.getProductId());
            if(listProduct == null) return false;
            int quantity = listProduct.getQuantity() - product.getQuantity();
            invoice += product.getProductName() + " " + product.getProductId() + " ";
            if(quantity < 0) {
                totalCost += product.getPrice() * listProduct.getQuantity();
                
                invoice += listProduct.getQuantity() + " " + product.getPrice() + "\n";
                listProduct.addToWaitlist(client.getClientId(), -1*quantity);
                listProduct.setQuantity(0);
            }
            else {
                listProduct.setQuantity(quantity);
                totalCost += product.getPrice() * product.getQuantity();
                invoice += product.getQuantity() + " " + product.getPrice() + "\n";
            }
        }
        if(invoice.equals("")) return false;
        invoice += "Total Cost: " + totalCost;
        client.addCredit(totalCost);
        client.addInvoice(invoice);
        client.clearWishlist();
        return true;
    }

    // Recieves a shipment of a product
    public boolean receiveShipment(String productId, int quantity) {
        Product product = pList.searchProduct(productId);
        if(product == null) return false;
        Waitlist copy = new Waitlist(product.getWaitlist());
        Iterator<WaitlistClient> waitlist = product.getWaitlist();
        while(waitlist.hasNext() && quantity > 0) {
            WaitlistClient client = waitlist.next();
            Client realClient = cList.searchClient(client.getClientId());
            String invoice = "";
            if(realClient == null) return false;
            if(quantity < client.getQuantity()) {
                quantity = client.getQuantity() - quantity; 
                copy.setQuantity(client.getClientId(), quantity);
                invoice = product.getProductName() + " " + product.getProductId() + 
                    " " + quantity + " " + product.getPrice() + "\n" + 
                    "Total Cost: " + (quantity * product.getPrice());
                realClient.addInvoice(invoice);
                realClient.addCredit(quantity * product.getPrice());
                quantity = 0;
            }
            else {
                quantity -= client.getQuantity();
                copy.removeClient(client.getClientId());
                invoice = product.getProductName() + " " + product.getProductId() + " " +
                    client.getQuantity() + " " + product.getPrice() + "\n" + "Total Cost: " +
                    client.getQuantity() * product.getPrice();
                realClient.addInvoice(invoice);
                realClient.addCredit(client.getQuantity() * product.getPrice());
            }
        }
        product.setWaitlist(copy.getWaitlist());
        if(quantity > 0) {
            product.setQuantity(product.getQuantity() + quantity);
        }
        return true;
    }

    // Recieve a client payment
    public boolean makePayment(String clientId, double amount) {
        Client client = cList.searchClient(clientId);
        if(client == null) return false;
        client.addDebit(amount);
        return true;
    }
}