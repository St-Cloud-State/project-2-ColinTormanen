import java.io.*;
import java.util.*;

public class Wishlist {

    // Linked list to hold the wishlist
    private List<Product> list = new LinkedList<>();

    // Default constructor
    public Wishlist() {
    }

    // Insert a product into the wishlist
    public boolean insertProduct(Product product) {
        list.add(product);
        return true;
    }

    // Return an iterator to traverse the list
    public Iterator getProducts() {
        return list.iterator();
    }

    // Returns the list to string
    public String toString(){
        return list.toString();
    }

    public boolean removeProduct(String productId) {
        Iterator<Product> iterator = list.iterator();
        while(iterator.hasNext()) {
            Product product = iterator.next();
            if(product.getProductId() == productId) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private void writeObject(java.io.ObjectOutputStream out) {
        try {
            out.defaultWriteObject();
            out.writeObject(list);
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void readObject(java.io.ObjectInputStream in) {
        try {
            if(list != null) {
                return;
            }
            else {
                in.defaultReadObject();
                if(list == null) {
                    list = (List) in.readObject();
                }
                else {
                    in.readObject();
                }
            }
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }

    }
}