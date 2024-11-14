import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.io.*;
public class ClerkState extends WarehouseState implements ActionListener {
    private static ClerkState instance;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private JFrame frame;
    private AbstractButton exitButton, addButton, displayProductsButton, displayClientsbutton,
        displayClientOutstandingButton, paymentButton, clientButton;

    private ClerkState() {
        super();
    }

    public static ClerkState instance() {
        if(instance == null) instance = new ClerkState();
        return instance;
    }

    // Button press actions
    public void actionPerformed(ActionEvent event) {
        if(event.getSource().equals(this.exitButton)) this.exit();
        else if(event.getSource().equals(this.addButton)) this.addClient();
        else if(event.getSource().equals(this.displayProductsButton)) this.displayProducts();
        else if(event.getSource().equals(this.displayClientsbutton)) this.displayClients();
        else if(event.getSource().equals(this.displayClientOutstandingButton)) this.displayClientOutstanding();
        else if(event.getSource().equals(this.paymentButton)) this.receivePayment();
        else if(event.getSource().equals(this.clientButton)) this.client();
    }

    // Clear the JFrame
    public void clear() {
        frame.getContentPane().removeAll();
        frame.paint(frame.getGraphics());   
    }

    // Exit to login or back to manager
    private void exit() {
        if(Context.instance().getLogin() == Context.IsClerk) {
            clear();
            (Context.instance()).changeState(0);
        }
        else {
            clear();
            (Context.instance()).changeState(3);
        }
    }

    // Add a new client
    private void addClient() {
        String name = JOptionPane.showInputDialog(frame,"Please input the client's name: ");
        if(Warehouse.instance().addClient(new Client(name))) 
            JOptionPane.showMessageDialog(frame, (name + " was successfully added"));
        else JOptionPane.showMessageDialog(frame, "An error occured");
    }

    // Display all the products
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

    // Display all the clients
    private void displayClients() {
        JButton close = new JButton("close");
        DefaultListModel<Client> list = new DefaultListModel<Client>();
        (Warehouse.instance().getClients()).forEachRemaining(list::addElement);
        JDialog dialog = new JDialog(frame, "Client list", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JScrollPane(new JList<Client>(list)), BorderLayout.CENTER);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(close, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // Display all clients with a credit balance > 0
    private void displayClientOutstanding() {
        JButton close = new JButton("close");
        DefaultListModel<Client> list = new DefaultListModel<Client>();
        Iterator<Client> clients = Warehouse.instance().getClients();
        while(clients.hasNext()) {
            Client c = clients.next();
            if(c.getCredit() > 0) list.addElement(c);
        }
        JDialog dialog = new JDialog(frame, "Client list", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new BorderLayout());
        dialog.add(new JScrollPane(new JList<Client>(list)), BorderLayout.CENTER);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(close, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // Receive a payment
    private void receivePayment() {
        String id = JOptionPane.showInputDialog(frame,"Please input the user id: ");
        if(Warehouse.instance().searchForClient(id) != null) {
            double amount = Double.parseDouble(
                JOptionPane.showInputDialog(frame, "Amount received: "));
            if(amount < 0)
                JOptionPane.showMessageDialog(frame, "Invalid amount");
            else {
                if(Warehouse.instance().makePayment(id,amount))
                    JOptionPane.showMessageDialog(frame, "Payment received");
                else JOptionPane.showMessageDialog(frame, "An error occured");
            }
        }
        else JOptionPane.showMessageDialog(frame, "An error occured");
    }

    // Go to client if valid id entered
    private void client() {
        String id = JOptionPane.showInputDialog(frame,"Please input the user id: ");
        if(Warehouse.instance().searchForClient(id) != null) {
            (Context.instance()).setUserId(id);
            clear();
            (Context.instance()).changeState(1);
        }
        else JOptionPane.showMessageDialog(frame,"Invalid user id.");
        
    }

    // Main run function
    public void run() {
        frame = Context.instance().getFrame();
        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new FlowLayout());
        exitButton = new JButton("logout");
        addButton = new JButton("add client");
        displayProductsButton = new JButton("display products");
        displayClientsbutton = new JButton("display clients");
        displayClientOutstandingButton = new JButton("display clients w/ outstanding balance");
        paymentButton = new JButton("receive payment");
        clientButton = new JButton("become client");
        exitButton.addActionListener(this);
        addButton.addActionListener(this);
        displayProductsButton.addActionListener(this);
        displayClientsbutton.addActionListener(this);
        displayClientOutstandingButton.addActionListener(this);
        paymentButton.addActionListener(this);
        clientButton.addActionListener(this);
        frame.getContentPane().add(this.exitButton);
        frame.getContentPane().add(this.addButton);
        frame.getContentPane().add(this.displayProductsButton);
        frame.getContentPane().add(this.displayClientsbutton);
        frame.getContentPane().add(this.displayClientOutstandingButton);
        frame.getContentPane().add(this.paymentButton);
        frame.getContentPane().add(this.clientButton);
        frame.setVisible(true);
        frame.paint(frame.getGraphics()); 
        frame.toFront();
        frame.requestFocus();
    }
}