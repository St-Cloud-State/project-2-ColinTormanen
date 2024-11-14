import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.io.*;
public class UserState extends WarehouseState implements ActionListener {
    private static UserState instance;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private JFrame frame;
    private AbstractButton detailsButton, displayProductsButton, transactionsButton, addButton, 
        displayButton, orderButton, exitButton;

    private UserState() {
        super();

    }

    public static UserState instance() {
        if(instance == null) instance = new UserState();
        return instance;
    }

    // Button press actions
    public void actionPerformed(ActionEvent event) {
        if(event.getSource().equals(this.exitButton)) this.exit();
        else if(event.getSource().equals(this.detailsButton)) this.clientDetails();
        else if(event.getSource().equals(this.displayProductsButton)) this.displayProducts();
        else if(event.getSource().equals(this.transactionsButton)) this.transactions();
        else if(event.getSource().equals(this.addButton)) this.addToWishlist();
        else if(event.getSource().equals(this.displayButton)) this.displayWishlist();
        else if(event.getSource().equals(this.orderButton)) this.processOrder();
    }

    // Clear the JFrame
    public void clear() {
        frame.getContentPane().removeAll();
        frame.paint(frame.getGraphics());   
    }

    // Exit to login page or back to clerk 
    private void exit() {
        if(Context.instance().getLogin() == Context.IsUser) {
            clear();
            (Context.instance()).changeState(0);
        }
        else {
            clear();
            (Context.instance()).changeState(2);
        }
    }

    // Display the current client's details
    private void clientDetails() {
        JOptionPane.showMessageDialog(frame, 
            (Warehouse.instance().searchForClient(Context.instance().getUserId())));
    }

    // Display all products
    private void displayProducts() {
        JButton close = new JButton("close");
        DefaultListModel<Product> list = new DefaultListModel<Product>();
        (Warehouse.instance().getProducts()).forEachRemaining(list::addElement);
        JDialog dialog = new JDialog(frame, "Product list", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JScrollPane(new JList<Product>(list)), BorderLayout.CENTER);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(close, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // Display all client's invoices
    private void transactions() {
        JButton close = new JButton("close");
        DefaultListModel<String> list = new DefaultListModel<String>();
        (Warehouse.instance().getClientInvoices(Context.instance().getUserId()))
            .forEachRemaining(list::addElement);
        JDialog dialog = new JDialog(frame, "Invoices", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JScrollPane(new JList<String>(list)), BorderLayout.CENTER);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(close, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // Add a product to the wishlist
    private void addToWishlist() {
        String id = JOptionPane.showInputDialog(frame,"Please input the product id: ");
        if(Warehouse.instance().searchForProduct(id) != null) {
            int amount = Integer.parseInt(
                JOptionPane.showInputDialog(frame, "Amount to add: "));
            if(Warehouse.instance().addToWishlist((Context.instance().getUserId()),id,amount))
                JOptionPane.showMessageDialog(frame, "Product added");
            else JOptionPane.showMessageDialog(frame, "An error occured");
        } else JOptionPane.showMessageDialog(frame, "Invalid product id");
    }

    // Display the client's wishlist
    private void displayWishlist() {
        JButton close = new JButton("close");
        DefaultListModel<Product> list = new DefaultListModel<Product>();
        (Warehouse.instance().getClientWishlist(Context.instance().getUserId()))
            .forEachRemaining(list::addElement);
        JDialog dialog = new JDialog(frame, "Wishlist", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JScrollPane(new JList<Product>(list)), BorderLayout.CENTER);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(close, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // Process client's order
    private void processOrder() {
        if(Warehouse.instance().placeOrder(Context.instance().getUserId()))
            JOptionPane.showMessageDialog(frame, "Order has been placed");
        else JOptionPane.showMessageDialog(frame, "An error occured");
    }

    // Main run function
    public void run() {
        frame = Context.instance().getFrame();
        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new FlowLayout());
        exitButton = new JButton("logout");
        detailsButton = new JButton("details");
        displayProductsButton = new JButton("display products");
        transactionsButton = new JButton("display invoices");
        addButton = new JButton("add product to wishlist");
        displayButton = new JButton("display wishlist");
        orderButton = new JButton("place oder");
        exitButton.addActionListener(this);
        detailsButton.addActionListener(this);
        displayProductsButton.addActionListener(this);
        transactionsButton.addActionListener(this);
        addButton.addActionListener(this);
        displayButton.addActionListener(this);
        orderButton.addActionListener(this);
        frame.getContentPane().add(this.exitButton);
        frame.getContentPane().add(this.detailsButton);
        frame.getContentPane().add(this.displayProductsButton);
        frame.getContentPane().add(this.transactionsButton);
        frame.getContentPane().add(this.addButton);
        frame.getContentPane().add(this.displayButton);
        frame.getContentPane().add(this.orderButton);
        frame.setVisible(true);
        frame.paint(frame.getGraphics()); 
        frame.toFront();
        frame.requestFocus();
    }
}