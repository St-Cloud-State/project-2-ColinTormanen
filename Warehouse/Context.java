import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.io.*;
public class Context {
    public static final int IsUser = 1;
    public static final int IsClerk = 2;
    public static final int IsManager = 3;
    private static int currentState;
    private static Warehouse warehouse;
    private static Context context;
    private int currentUser;
    private String userId;
    private static JFrame frame;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static WarehouseState[] states;
    private int[][] nextState;

    public void setLogin(int user) { currentUser = user; }

    public void setUserId(String id) { userId = id; }

    public int getLogin() { return currentUser; }
    
    public String getUserId() { return userId; }

    public JFrame getFrame() { return frame; }

    private Context() {
        warehouse = Warehouse.instance();

        states = new WarehouseState[4];
        states[0] = LoginState.instance();
        states[1] = UserState.instance();
        states[2] = ClerkState.instance();
        states[3] = ManagerState.instance();
        nextState = new int[4][4];
        nextState[0][0] = -1; nextState[0][1] = 1; nextState[0][2] = 2; nextState[0][3] = 3; 
        nextState[1][0] = 0; nextState[1][1] = -2; nextState[1][2] = 2; nextState[1][3] = -2; 
        nextState[2][0] = 0; nextState[2][1] = 1; nextState[2][2] = -2; nextState[2][3] = 3; 
        nextState[3][0] = 0; nextState[3][1] = -2; nextState[3][2] = 2; nextState[3][3] = -2; 

        currentState = 0;
        frame = new JFrame("Warehouse GUI");
	    frame.addWindowListener(new WindowAdapter()
            { public void windowClosing(WindowEvent e) { System.exit(0); } });
        frame.setSize(400,400);
        frame.setLocation(400, 400);
    }

    public void changeState(int next) {
        currentState = nextState[currentState][next];
        if(currentState == -2) {
            System.out.println("An error has occured");
            terminate();
        }
        else if(currentState == -1) terminate();
        states[currentState].run();
    }

    private void terminate() {
        System.out.println("Exiting");
        System.exit(0);
    }

    public static Context instance() {
        if(context == null) context = new Context();
        return context;
    }



    public static void main(String[] args) {
        Context.instance();
        states[currentState].run();
    }

}