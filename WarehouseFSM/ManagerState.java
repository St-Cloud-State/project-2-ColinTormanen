import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.io.*;
public class ManagerState extends WarehouseState implements ActionListener {
    private static ManagerState instance;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Warehouse warehouse;
    private Context context;
    private JFrame frame;
    private AbstractButton exitButton, addButton, displayButton, receiveButton, clerkButton;

    private ManagerState() {
        super();
        warehouse = Warehouse.instance();
    }

    public static ManagerState instance() {
        if(instance == null) instance = new ManagerState();
        return instance;
    }

    // Button press actions
    public void actionPerformed(ActionEvent event) {
        if(event.getSource().equals(this.exitButton)) this.exit();
        else if(event.getSource().equals(this.addButton)) this.addProduct();
        else if(event.getSource().equals(this.displayButton)) this.displayWaitlist();
        else if(event.getSource().equals(this.receiveButton)) this.receiveShipment();
        else if(event.getSource().equals(this.clerkButton)) this.clerk();
    }

    // Clear the JFrame
    public void clear() {
        frame.getContentPane().removeAll();
        frame.paint(frame.getGraphics());   
    }

    private void exit() {
        clear();
        (Context.instance()).changeState(0);
    }

    private void addProduct() {
        String name = JOptionPane.showInputDialog(frame,"Please input the product name: ");
        String id = JOptionPane.showInputDialog(frame,"Please input the product id: ");
        int quantity = Integer.parseInt(
            JOptionPane.showInputDialog(frame,"Please input the starting quantity: "));
        double price = Double.parseDouble(
            JOptionPane.showInputDialog(frame,"Please input the product price: "));
        if(Warehouse.instance().addProduct(new Product(name,id,quantity,price)))
            JOptionPane.showMessageDialog(frame, (name + " was successfully added"));
        else JOptionPane.showMessageDialog(frame, "An error occured");
    }

    private void displayWaitlist() {
        String id = JOptionPane.showInputDialog(frame,"Please input the product id: ");
        if(Warehouse.instance().searchForProduct(id) != null) {
            JButton close = new JButton("close");
            DefaultListModel<WaitlistClient> list = new DefaultListModel<WaitlistClient>();
            (Warehouse.instance().searchForProduct(id)).getWaitlist().forEachRemaining(list::addElement);
            JDialog dialog = new JDialog(frame, "Waitlist", true);
            dialog.setSize(500, 400);
            dialog.setLayout(new BorderLayout());
            dialog.add(new JScrollPane(new JList<WaitlistClient>(list)), BorderLayout.CENTER);
            close.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
            dialog.add(close, BorderLayout.SOUTH);
            dialog.setVisible(true);
        }
        else JOptionPane.showMessageDialog(frame, "An error occured");
    }

    private void receiveShipment() {
        String id = JOptionPane.showInputDialog(frame,"Please input the product id: ");
        if(Warehouse.instance().searchForProduct(id) != null) {
            int amount = Integer.parseInt(
                JOptionPane.showInputDialog(frame, "Please input the amount received: "));
            if(!(Warehouse.instance()).receiveShipment(id,amount)) 
                JOptionPane.showMessageDialog(frame, "An error occured");
            
        }
        else 
            JOptionPane.showMessageDialog(frame,"Invalid product id.");
    }

    private void clerk() {
        clear();
        (Context.instance()).changeState(2);
    }

    public void run() {
        frame = Context.instance().getFrame();
        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new FlowLayout());
        exitButton = new JButton("logout");
        addButton = new JButton("add product");
        displayButton = new JButton("display waitlist");
        receiveButton = new JButton("receive shipment");
        clerkButton = new JButton("become clerk");
        exitButton.addActionListener(this);
        addButton.addActionListener(this);
        displayButton.addActionListener(this);
        receiveButton.addActionListener(this);
        clerkButton.addActionListener(this);
        frame.getContentPane().add(this.exitButton);
        frame.getContentPane().add(this.addButton);
        frame.getContentPane().add(this.displayButton);
        frame.getContentPane().add(this.receiveButton);
        frame.getContentPane().add(this.clerkButton);
        frame.setVisible(true);
        frame.paint(frame.getGraphics()); 
        frame.toFront();
        frame.requestFocus();
    }
}