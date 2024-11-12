public class LoginState extends WarehouseState implements ActionListener {

    private BufferedReader reader = new BufferedReader(InputStreamReader(System.in));
    private Context context;
    private JFrame frame;
    private static LoginState instance;
    private AbstractButton userButton, clerkButton, managerButton, logoutButton;

    private LoginState() {

    }

    public static LoginState instance() {
        if(instance == null) instance = new LoginState();
        return instance;
    }

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