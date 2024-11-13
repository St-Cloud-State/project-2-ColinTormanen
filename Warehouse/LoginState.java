public class LoginState extends WarehouseState implements ActionListener {

    private BufferedReader reader = new BufferedReader(InputStreamReader(System.in));
    private Context context;
    private JFrame frame;
    private static LoginState instance;
    private AbstractButton userButton, clerkButton, managerButton, logoutButton;

    // Private constructor
    private LoginState() {
        super();
    }

    // Get the instance of the state
    public static LoginState instance() {
        if(instance == null) instance = new LoginState();
        return instance;
    }

    // Button press actions
    public void actionPerformed(ActionEvent event) {
        if(event.getSource().equals(this.userButton)) this.user();
        else if(event.getSource().equals(this.clerkButton)) this.clerk();
        else if(event.getSource().equals(this.managerButton)) this.manager();
        else if(event.getSource().equals(this.logoutButton)) (Context.instance()).changeState(0);
    }

    // Clear the JFrame
    public void clear() {
        frame.getContentPane().removeAll();
        frame.paint(frame.getGraphics());   
    }

    // Called if the user button is pressed
    private void user() {
        String id = JOptionPane.showInputDialog(frame,"Please input the user id: ");
        if(Warehouse.instance().searchForClient(id) != null) {
            (Context.instance()).setLogin(Context.IsUser);
            (Context.instance()).setUser(id);
            clear();
            (Context.instance()).changeState(1);
        }
        else JOptionPane.showMessageDialog(frame,"Invalid user id.");
        
    }

    // Called if the clerk button is pressed
    private void clerk() {
        (Context.instance()).setLogin(Context.IsClerk);
        clear();
        (Context.instance()).changeState(2);
    }

    // Called if the manager button is pressed
    private void manager() {
        (Context.instance()).setLogin(Context.IsManager);
        clear();
        (Context.instance()).changeState(3);
    }

    // Run the state
    public void run(){
        frame = Context.instance().getFrame();
        frame.getContentPane().removeAll();
        frame.getContentPane().setLayout(new FlowLayout());
        userButton = new JButton("user");
        clerkButton = new JButton("clerk");
        managerButton = new JButton("manager");
        userButton.addActionListener(this);
        clerkButton.addActionListener(this);
        managerButton.addActionListener(this);
        frame.getContentPane().add(this.userButton);
        frame.getContentPane().add(this.clerkButton);
        frame.getContentPane().add(this.managerButton);
        framge.setVisible(true);
        frame.paint(frame.getGraphics()); 
        frame.toFront();
        frame.requestFocus();
    }
}